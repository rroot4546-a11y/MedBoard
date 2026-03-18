package com.roox.medboard.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentRepository(
    private val context: Context,
    private val topicDao: TopicDao
) {
    private val gson = Gson()

    suspend fun loadContentIfNeeded(): Boolean = withContext(Dispatchers.IO) {
        val count = topicDao.getCount()
        if (count > 0) return@withContext false

        loadFromAssets()
        true
    }

    private suspend fun loadFromAssets() = withContext(Dispatchers.IO) {
        val json = context.assets.open("medboard_content.json").bufferedReader().readText()
        val jsonObject = gson.fromJson(json, JsonObject::class.java)

        val topics = mutableListOf<TopicEntity>()

        for ((_, value) in jsonObject.entrySet()) {
            val obj = value.asJsonObject
            val id = obj.get("id").asString
            val systemId = obj.get("systemId").asString
            val title = obj.get("title").asString
            val sectionsJson = obj.get("sections").toString()

            topics.add(
                TopicEntity(
                    id = id,
                    systemId = systemId,
                    title = title,
                    sectionsJson = sectionsJson
                )
            )
        }

        topicDao.insertAll(topics)
    }

    fun getSectionsFromJson(sectionsJson: String): List<Section> {
        val type = object : TypeToken<List<Section>>() {}.type
        return try {
            gson.fromJson(sectionsJson, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}

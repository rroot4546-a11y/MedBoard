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

    // Cache the full JSON in memory to avoid repeated file reads
    private var cachedJsonObject: JsonObject? = null

    private fun getJsonObject(): JsonObject {
        if (cachedJsonObject == null) {
            val json = context.assets.open("medboard_content.json").bufferedReader().readText()
            cachedJsonObject = gson.fromJson(json, JsonObject::class.java)
        }
        return cachedJsonObject!!
    }

    suspend fun loadContentIfNeeded(): Boolean = withContext(Dispatchers.IO) {
        val count = topicDao.getCount()
        if (count > 0) return@withContext false

        loadFromAssets()
        true
    }

    private suspend fun loadFromAssets() = withContext(Dispatchers.IO) {
        val jsonObject = getJsonObject()
        val topics = mutableListOf<TopicEntity>()

        for ((_, value) in jsonObject.entrySet()) {
            val obj = value.asJsonObject
            val id = obj.get("id").asString
            val systemId = obj.get("systemId").asString
            val title = obj.get("title").asString
            // Store only topic id reference, not full sections JSON
            topics.add(
                TopicEntity(
                    id = id,
                    systemId = systemId,
                    title = title,
                    sectionsJson = "" // sections loaded directly from assets
                )
            )
        }

        topicDao.insertAll(topics)
    }

    // Load sections directly from assets JSON (bypasses SQLite size limits)
    suspend fun getSectionsForTopic(topicId: String): List<Section> = withContext(Dispatchers.IO) {
        try {
            val jsonObject = getJsonObject()
            val topicObj = jsonObject.get(topicId)?.asJsonObject ?: return@withContext emptyList()
            val sectionsJson = topicObj.get("sections")?.toString() ?: return@withContext emptyList()
            val type = object : TypeToken<List<Section>>() {}.type
            gson.fromJson(sectionsJson, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
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

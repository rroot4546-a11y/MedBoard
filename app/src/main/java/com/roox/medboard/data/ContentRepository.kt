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

    companion object {
        // Static cache shared across all instances - loaded only once
        @Volatile
        private var cachedJsonObject: JsonObject? = null
        private val cacheLock = Any()
    }

    private fun getJsonObject(): JsonObject {
        return cachedJsonObject ?: synchronized(cacheLock) {
            cachedJsonObject ?: run {
                val json = context.assets.open("medboard_content.json").bufferedReader().readText()
                val parsed = Gson().fromJson(json, JsonObject::class.java)
                cachedJsonObject = parsed
                parsed
            }
        }
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

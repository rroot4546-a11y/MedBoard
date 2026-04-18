package com.roox.medboard

import android.app.Application
import com.roox.medboard.data.ContentRepository
import com.roox.medboard.data.MedBoardDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedBoardApp : Application() {
    val database: MedBoardDatabase by lazy { MedBoardDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        // Pre-load JSON content into memory cache on app startup (background thread)
        // This ensures smooth performance when navigating topics
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repo = ContentRepository(applicationContext, database.topicDao())
                repo.loadContentIfNeeded()
            } catch (e: Exception) {
                // Ignore startup errors - content will load on demand
            }
        }
    }
}

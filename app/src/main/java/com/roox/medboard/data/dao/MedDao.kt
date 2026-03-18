package com.roox.medboard.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.roox.medboard.data.model.Bookmark
import com.roox.medboard.data.model.CachedTopic
import com.roox.medboard.data.model.ReadingProgress

@Dao
interface MedDao {
    // Cache
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheTopic(topic: CachedTopic)

    @Query("SELECT * FROM cached_topics WHERE topicId = :id")
    suspend fun getCachedTopic(id: String): CachedTopic?

    @Query("SELECT * FROM cached_topics WHERE systemId = :sysId")
    suspend fun getCachedBySystem(sysId: String): List<CachedTopic>

    @Query("DELETE FROM cached_topics")
    suspend fun clearCache()

    // Bookmarks
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bm: Bookmark)

    @Delete
    suspend fun removeBookmark(bm: Bookmark)

    @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
    fun allBookmarks(): LiveData<List<Bookmark>>

    @Query("SELECT COUNT(*) FROM bookmarks WHERE topicId = :id")
    suspend fun isBookmarked(id: String): Int

    // Progress
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgress(p: ReadingProgress)

    @Query("SELECT * FROM reading_progress WHERE topicId = :id")
    suspend fun getProgress(id: String): ReadingProgress?

    @Query("SELECT * FROM reading_progress WHERE completed = 1 ORDER BY lastRead DESC")
    fun completedTopics(): LiveData<List<ReadingProgress>>

    @Query("SELECT COUNT(*) FROM reading_progress WHERE systemId = :sysId AND completed = 1")
    suspend fun completedCount(sysId: String): Int
}

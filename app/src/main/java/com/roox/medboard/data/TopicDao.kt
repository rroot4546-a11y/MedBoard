package com.roox.medboard.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TopicDao {
    @Query("SELECT * FROM topics ORDER BY systemId, title")
    fun getAllTopics(): LiveData<List<TopicEntity>>

    @Query("SELECT * FROM topics WHERE systemId = :systemId ORDER BY title")
    fun getTopicsBySystem(systemId: String): LiveData<List<TopicEntity>>

    @Query("SELECT * FROM topics WHERE isBookmarked = 1 ORDER BY systemId, title")
    fun getBookmarkedTopics(): LiveData<List<TopicEntity>>

    @Query("SELECT * FROM topics WHERE title LIKE '%' || :query || '%' ORDER BY systemId, title")
    fun searchTopics(query: String): LiveData<List<TopicEntity>>

    @Query("SELECT * FROM topics WHERE id = :id")
    suspend fun getTopicById(id: String): TopicEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(topics: List<TopicEntity>)

    @Query("UPDATE topics SET isBookmarked = :bookmarked WHERE id = :id")
    suspend fun updateBookmark(id: String, bookmarked: Boolean)

    @Query("SELECT COUNT(*) FROM topics")
    suspend fun getCount(): Int

    @Query("SELECT DISTINCT systemId FROM topics ORDER BY systemId")
    fun getAllSystemIds(): LiveData<List<String>>

    @Query("SELECT COUNT(*) FROM topics WHERE systemId = :systemId")
    suspend fun getCountForSystem(systemId: String): Int
}

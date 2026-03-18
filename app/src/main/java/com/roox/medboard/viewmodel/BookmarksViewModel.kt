package com.roox.medboard.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.roox.medboard.MedBoardApp
import com.roox.medboard.data.TopicEntity
import kotlinx.coroutines.launch

class BookmarksViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = (application as MedBoardApp).database.topicDao()

    val bookmarks: LiveData<List<TopicEntity>> = dao.getBookmarkedTopics()

    fun removeBookmark(topic: TopicEntity) {
        viewModelScope.launch {
            dao.updateBookmark(topic.id, false)
        }
    }
}

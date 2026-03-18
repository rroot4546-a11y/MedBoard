package com.roox.medboard.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.roox.medboard.MedBoardApp
import com.roox.medboard.data.TopicEntity
import kotlinx.coroutines.launch

class TopicsViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = (application as MedBoardApp).database.topicDao()

    private val _systemId = MutableLiveData<String>()

    val topics: LiveData<List<TopicEntity>> = _systemId.switchMap { systemId ->
        dao.getTopicsBySystem(systemId)
    }

    fun setSystemId(systemId: String) {
        _systemId.value = systemId
    }

    fun toggleBookmark(topic: TopicEntity) {
        viewModelScope.launch {
            dao.updateBookmark(topic.id, !topic.isBookmarked)
        }
    }
}

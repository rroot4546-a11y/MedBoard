package com.roox.medboard.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.roox.medboard.MedBoardApp
import com.roox.medboard.data.ContentRepository
import com.roox.medboard.data.Section
import com.roox.medboard.data.TopicEntity
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val db = (application as MedBoardApp).database
    private val dao = db.topicDao()
    private val repository = ContentRepository(application, dao)

    private val _topic = MutableLiveData<TopicEntity?>()
    val topic: LiveData<TopicEntity?> = _topic

    private val _sections = MutableLiveData<List<Section>>()
    val sections: LiveData<List<Section>> = _sections

    fun loadTopic(topicId: String) {
        viewModelScope.launch {
            val topic = dao.getTopicById(topicId)
            _topic.value = topic
            topic?.let {
                _sections.value = repository.getSectionsFromJson(it.sectionsJson)
            }
        }
    }

    fun toggleBookmark() {
        val current = _topic.value ?: return
        viewModelScope.launch {
            dao.updateBookmark(current.id, !current.isBookmarked)
            _topic.value = dao.getTopicById(current.id)
        }
    }
}

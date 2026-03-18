package com.roox.medboard.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.roox.medboard.MedBoardApp
import com.roox.medboard.data.ContentRepository
import com.roox.medboard.data.TopicEntity
import kotlinx.coroutines.launch

data class SystemInfo(
    val systemId: String,
    val topicCount: Int
)

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db = (application as MedBoardApp).database
    private val dao = db.topicDao()
    private val repository = ContentRepository(application, dao)

    val allTopics: LiveData<List<TopicEntity>> = dao.getAllTopics()
    val systemIds: LiveData<List<String>> = dao.getAllSystemIds()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoaded = MutableLiveData(false)
    val isLoaded: LiveData<Boolean> = _isLoaded

    private val _searchQuery = MutableLiveData("")
    val searchResults: LiveData<List<TopicEntity>> = _searchQuery.switchMap { query ->
        if (query.isBlank()) MutableLiveData(emptyList())
        else dao.searchTopics(query)
    }

    private val _showHighYieldOnly = MutableLiveData(false)
    val showHighYieldOnly: LiveData<Boolean> = _showHighYieldOnly

    fun loadContent() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.loadContentIfNeeded()
            } finally {
                _isLoading.value = false
                _isLoaded.value = true
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleHighYield() {
        _showHighYieldOnly.value = !(_showHighYieldOnly.value ?: false)
    }
}

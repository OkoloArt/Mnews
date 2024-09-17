package com.example.m_news.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.m_news.data.repository.DatabaseRepo
import com.example.m_news.database.NewsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.m_news.utils.Result
import kotlinx.coroutines.flow.toList

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val databaseRepo: DatabaseRepo) : ViewModel() {

    private val _bookmarkedArticles = MutableStateFlow<Result<List<NewsEntity>>>(Result.Loading)
    val bookmarkedArticles: StateFlow<Result<List<NewsEntity>>> = _bookmarkedArticles.asStateFlow()

    init {
        fetchBookmarkedArticles()
    }

    private fun fetchBookmarkedArticles() {
        viewModelScope.launch {
            databaseRepo.getAllArticles().collect { result ->
                _bookmarkedArticles.value = result
            }
        }
    }

    fun addBookmarkArticle(news: NewsEntity) = viewModelScope.launch {
        try {
            databaseRepo.insertNews(news)
            fetchBookmarkedArticles()
        } catch (e: Exception) {
            Log.e("BookmarkViewModel", "Error adding bookmark", e)
        }
    }

    fun removeBookmark(news: NewsEntity) = viewModelScope.launch {
        try {
            databaseRepo.deleteNews(news)
            fetchBookmarkedArticles()
        } catch (e: Exception) {
            Log.e("BookmarkViewModel", "Error removing bookmark", e)
        }
    }
}


package com.example.m_news.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_news.data.model.News
import com.example.m_news.data.repository.DatabaseRepo
import com.example.m_news.data.repository.NewsRepo
import com.example.m_news.database.NewsEntity
import com.example.m_news.utils.Helper.toNewsEntity
import com.example.m_news.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepo: NewsRepo,
    private val databaseRepo: DatabaseRepo
) : ViewModel() {

    private val _news = MutableStateFlow<Result<List<News>>>(Result.Loading)
    val news: StateFlow<Result<List<News>>> = _news.asStateFlow()


    private val _bookmarkedArticles = MutableStateFlow<Result<List<NewsEntity>>>(Result.Loading)

    init {
        fetchBookmarkedArticles()
        getAllNews()

    }

    private fun getAllNews() {
        viewModelScope.launch {
            try {
                val news = newsRepo.getAllNews()
                if (news is Result.Success) {
                    val bookmarkUrl = when (val bookmarkedArticles = _bookmarkedArticles.value) {
                        is Result.Success -> bookmarkedArticles.data.map { it.id }.toSet()
                        else -> emptySet()
                    }

                    val updatedNews = news.data.map { article ->
                        article.copy(
                            isBookmarked = bookmarkUrl.contains(article.url.hashCode().toString())
                        )
                    }

                    _news.value = Result.Success(updatedNews)
                } else if (news is Result.Error) {
                    _news.value = news
                }
            } catch (e: Exception) {
                _news.value = Result.Error(e)
            }
        }
    }

    fun reloadNews() {
        viewModelScope.launch {
            _news.value = Result.Loading
            delay(3000)
            getAllNews()
        }
    }

    private fun fetchBookmarkedArticles() {
        viewModelScope.launch {
            databaseRepo.getAllArticles().collect { result ->
                _bookmarkedArticles.value = result
                val bookmarkUrl = when (result) {
                    is Result.Success -> result.data.map { it.id }.toSet()
                    else -> emptySet()
                }

                val updatedNews = (_news.value as? Result.Success)?.data?.map { article ->
                    article.copy(
                        isBookmarked = bookmarkUrl.contains(
                            article.url.hashCode().toString()
                        )
                    )
                }

                if (updatedNews != null) {
                    _news.value = Result.Success(updatedNews)
                }
            }
        }
    }

    fun toggleBookmark(news: News) {
        viewModelScope.launch {
            try {
                if (news.isBookmarked) {
                    databaseRepo.deleteNews(news.toNewsEntity())
                } else {
                    databaseRepo.insertNews(news.toNewsEntity())
                }

                val updatedList = (_news.value as? Result.Success)?.data?.map { article ->
                    if (article.url == news.url) {
                        article.copy(isBookmarked = !article.isBookmarked)
                    } else {
                        article
                    }
                }

                if (updatedList != null) {
                    _news.value = Result.Success(updatedList)
                }

            } catch (e: Exception) {
                _news.value = Result.Error(e)
            }
        }
    }

}






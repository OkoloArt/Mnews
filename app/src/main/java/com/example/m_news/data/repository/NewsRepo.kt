package com.example.m_news.data.repository

import com.example.m_news.data.model.News
import com.example.m_news.network.NewsService
import com.example.m_news.utils.Result
import javax.inject.Inject

interface NewsRepo {

    suspend fun getAllNews(): Result<List<News>>

}

class NewsRepoImpl @Inject constructor(private val newsService: NewsService): NewsRepo {
    override suspend fun getAllNews(): Result<List<News>> {
        return try {
            val articles = newsService.getAllNews()
            Result.Success(articles.articles)
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}

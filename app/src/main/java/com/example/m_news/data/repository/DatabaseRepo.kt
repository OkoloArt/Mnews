package com.example.m_news.data.repository

import com.example.m_news.database.NewsDao
import com.example.m_news.database.NewsEntity
import com.example.m_news.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface DatabaseRepo {
    suspend fun getAllArticles(): Flow<Result<List<NewsEntity>>>

    suspend fun insertNews(news: NewsEntity)

    suspend fun deleteNews(news: NewsEntity)

}

class DatabaseRepoImpl @Inject constructor(private val newsDao: NewsDao) : DatabaseRepo {
    override suspend fun getAllArticles(): Flow<Result<List<NewsEntity>>> = flow {
        try {
            newsDao.getAllArticles().collect { articleList ->
                emit(Result.Success(articleList))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override suspend fun insertNews(news: NewsEntity) {
        newsDao.insertNews(news)
    }

    override suspend fun deleteNews(news: NewsEntity) {
        newsDao.deleteNews(news)
    }
}

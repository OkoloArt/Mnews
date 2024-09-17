package com.example.m_news.di

import android.content.Context
import androidx.room.Room
import com.example.m_news.data.repository.DatabaseRepo
import com.example.m_news.data.repository.DatabaseRepoImpl
import com.example.m_news.data.repository.NewsRepo
import com.example.m_news.data.repository.NewsRepoImpl
import com.example.m_news.database.NewsDao
import com.example.m_news.database.NewsDatabase
import com.example.m_news.network.NewsService
import com.example.m_news.network.httpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideArticleRepo(newsService: NewsService): NewsRepo =
        NewsRepoImpl(newsService)

    @Provides
    @Singleton
    fun provideDatabaseRepo(newsDao: NewsDao): DatabaseRepo = DatabaseRepoImpl(newsDao)
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideClient(): HttpClient = httpClient

    @Provides
    @Singleton
    fun provideArticleApiInterface(httpClient: HttpClient): NewsService =
        NewsService(httpClient)
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context.applicationContext, NewsDatabase::class.java, "mnews_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideArticleDao(db: NewsDatabase): NewsDao {
        return db.newsDao()
    }
}



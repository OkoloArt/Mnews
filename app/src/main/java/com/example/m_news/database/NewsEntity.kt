package com.example.m_news.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.m_news.data.model.Source

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey val id: String,
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)

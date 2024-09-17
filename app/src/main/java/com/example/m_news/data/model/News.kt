package com.example.m_news.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val articles: List<News>,
    val status: String,
    val totalResults: Int
)

@Serializable
data class News(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    var isBookmarked: Boolean = false
)

@Serializable
data class Source(
    val id: String?,
    val name : String?
)
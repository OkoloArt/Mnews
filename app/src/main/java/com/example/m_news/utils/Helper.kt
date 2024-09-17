package com.example.m_news.utils

import com.example.m_news.data.model.News
import com.example.m_news.database.NewsEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Helper {

    fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date: Date? = inputFormat.parse(inputDate)
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

        return date?.let { outputFormat.format(it) } ?: "Invalid date"
    }

    fun News.toNewsEntity(): NewsEntity {
        val uniqueId = url.hashCode().toString()

        return NewsEntity(
            id = uniqueId,
            title = this.title,
            description = this.description,
            url = this.url,
            urlToImage = this.urlToImage,
            publishedAt = this.publishedAt,
            author = this.author,
            content = this.content,
            source = this.source
        )
    }

    fun NewsEntity.toNews(): News {
        return News(
            title = this.title,
            description = this.description,
            url = this.url,
            urlToImage = this.urlToImage,
            author = this.author,
            content = this.content,
            publishedAt = this.publishedAt,
            source = this.source,
            isBookmarked = true
        )
    }

}
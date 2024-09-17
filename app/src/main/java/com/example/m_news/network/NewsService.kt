package com.example.m_news.network

import com.example.m_news.data.model.NewsResponse
import com.example.m_news.utils.ApiException
import com.example.m_news.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import javax.inject.Inject

class NewsService @Inject constructor(private val httpClient: HttpClient) {

    suspend fun getAllNews(): NewsResponse {
        val response: HttpResponse = httpClient.get(Constants.BASE_URL) {
            url {
                parameters.append("country", "us")
                parameters.append("apiKey", Constants.API_KEY)
            }
            contentType(ContentType.Application.Json)
        }

        if (response.status.isSuccess()) {
            return response.body<NewsResponse>()
        } else {
            throw ApiException(statusCode = response.status.value, "Error fetching News")
        }
    }
}
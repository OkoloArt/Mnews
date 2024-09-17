package com.example.m_news.utils

sealed interface Result<out T : Any> {
    data class Success<out T : Any>(val data: T) :
        Result<T>
    data object Loading : Result<Nothing>
    data class Error<out T: Any>(val error: Throwable) :
        Result<T>
}

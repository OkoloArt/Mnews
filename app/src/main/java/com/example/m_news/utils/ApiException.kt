package com.example.m_news.utils

class ApiException(val statusCode: Int, message: String) : Exception(message)

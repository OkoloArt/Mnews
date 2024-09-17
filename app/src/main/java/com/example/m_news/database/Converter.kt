package com.example.m_news.database

import androidx.room.TypeConverter
import com.example.m_news.data.model.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromSource(source: Source?): String? {
        if (source == null) return null
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toSource(sourceString: String?): Source? {
        if (sourceString == null) return null
        val type = object : TypeToken<Source>() {}.type
        return Gson().fromJson(sourceString, type)
    }
}
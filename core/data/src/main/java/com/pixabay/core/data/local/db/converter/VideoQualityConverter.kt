package com.pixabay.core.data.local.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.pixabay.core.domain.model.pixabay.VideoQualityDomain

class VideoQualityConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromVideoQuality(value: VideoQualityDomain?): String? {
        return value?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toVideoQuality(value: String?): VideoQualityDomain? {
        return value?.let { gson.fromJson(it, VideoQualityDomain::class.java) }
    }
}
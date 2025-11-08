package com.tving.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_cache")
data class VideoCacheEntity(
    @PrimaryKey
    val query: String,
    val total: Int,
    val totalHits: Int,
    val data: String,
    val cachedAt: Long
)
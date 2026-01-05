package com.pixabay.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_cache")
data class ImageCacheEntity(
    @PrimaryKey
    val cacheKey: String,
    val query: String,
    val page: Int,
    val perPage: Int,
    val total: Int,
    val totalHits: Int,
    val data: String,
    val cachedAt: Long
)
package com.pixabay.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pixabay.core.data.local.dao.PixabayCacheDao
import com.pixabay.core.data.local.entity.ImageCacheEntity
import com.pixabay.core.data.local.entity.VideoCacheEntity

@Database(
    entities = [
        ImageCacheEntity::class,
        VideoCacheEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class PixabayDatabase : RoomDatabase() {
    abstract fun pixabayCacheDao(): PixabayCacheDao
}
package com.tving.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tving.core.data.local.db.converter.VideoQualityConverter
import com.tving.core.data.local.db.dao.FavoriteDao
import com.tving.core.data.local.db.entity.FavoriteImageEntity
import com.tving.core.data.local.db.entity.FavoriteVideoEntity

@Database(
    entities = [
        FavoriteVideoEntity::class,
        FavoriteImageEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(VideoQualityConverter::class)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
package com.tving.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tving.core.data.local.db.converter.VideoQualityConverter
import com.tving.core.data.local.db.dao.FavoriteDao
import com.tving.core.data.local.db.entity.FavoriteImageEntity
import com.tving.core.data.local.db.entity.FavoriteVideoEntity

@Database(
    entities = [
        FavoriteVideoEntity::class,
        FavoriteImageEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(VideoQualityConverter::class)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    
    companion object {
        /**
         * 버전 1 -> 2
         * Pixabay Hotlinking 정책 준수를 위해 로컬 이미지 경로 필드 추가
         */
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE favorite_videos ADD COLUMN localThumbnailPath TEXT DEFAULT NULL")
                db.execSQL("ALTER TABLE favorite_images ADD COLUMN localPreviewPath TEXT DEFAULT NULL")
            }
        }
    }
}
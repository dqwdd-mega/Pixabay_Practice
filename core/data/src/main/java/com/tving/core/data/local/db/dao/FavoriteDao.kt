package com.tving.core.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tving.core.data.local.db.entity.FavoriteImageEntity
import com.tving.core.data.local.db.entity.FavoriteVideoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    // Video
    @Query("SELECT * FROM favorite_videos ORDER BY createdAt DESC")
    fun getFavoriteVideos(): Flow<List<FavoriteVideoEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_videos WHERE id = :videoId)")
    suspend fun isFavoriteVideo(videoId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteVideo(video: FavoriteVideoEntity)

    @Query("DELETE FROM favorite_videos WHERE id = :videoId")
    suspend fun deleteFavoriteVideo(videoId: Int)

    // Image
    @Query("SELECT * FROM favorite_images ORDER BY createdAt DESC")
    fun getFavoriteImages(): Flow<List<FavoriteImageEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_images WHERE id = :imageId)")
    suspend fun isFavoriteImage(imageId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteImage(image: FavoriteImageEntity)

    @Query("DELETE FROM favorite_images WHERE id = :imageId")
    suspend fun deleteFavoriteImage(imageId: Int)
}
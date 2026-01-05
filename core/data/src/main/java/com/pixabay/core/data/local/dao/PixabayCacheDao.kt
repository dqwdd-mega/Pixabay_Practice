package com.pixabay.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pixabay.core.data.local.entity.ImageCacheEntity
import com.pixabay.core.data.local.entity.VideoCacheEntity

@Dao
interface PixabayCacheDao {
    
    @Query("SELECT * FROM video_cache WHERE query = :query AND cachedAt > :minTime")
    suspend fun getVideoCache(query: String, minTime: Long): VideoCacheEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideoCache(cache: VideoCacheEntity)
    
    @Query("SELECT * FROM image_cache WHERE cacheKey = :cacheKey AND cachedAt > :minTime")
    suspend fun getImageCache(cacheKey: String, minTime: Long): ImageCacheEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImageCache(cache: ImageCacheEntity)
    
    @Query("DELETE FROM image_cache WHERE cachedAt < :expiryTime")
    suspend fun deleteExpiredImageCaches(expiryTime: Long)
    
    @Query("DELETE FROM video_cache WHERE cachedAt < :expiryTime")
    suspend fun deleteExpiredVideoCaches(expiryTime: Long)
}
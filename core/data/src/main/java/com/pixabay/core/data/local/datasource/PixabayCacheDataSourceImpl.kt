package com.pixabay.core.data.local.datasource

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pixabay.core.data.local.dao.PixabayCacheDao
import com.pixabay.core.data.local.entity.ImageCacheEntity
import com.pixabay.core.data.local.entity.VideoCacheEntity
import com.pixabay.core.domain.model.BaseResult
import com.pixabay.core.domain.model.pixabay.ImageSearch
import com.pixabay.core.domain.model.pixabay.VideoSearch
import javax.inject.Inject

class PixabayCacheDataSourceImpl @Inject constructor(
    private val dao: PixabayCacheDao,
    private val gson: Gson
) : PixabayCacheDataSource {
    
    companion object {
        private const val CACHE_DURATION_MS = 24 * 60 * 60 * 1000L
    }
    
    override suspend fun cacheVideoSearch(query: String, result: BaseResult<List<VideoSearch>>) {
        val entity = VideoCacheEntity(
            query = query,
            total = result.total,
            totalHits = result.totalHits,
            data = gson.toJson(result.data),
            cachedAt = System.currentTimeMillis()
        )
        dao.insertVideoCache(entity)
    }
    
    override suspend fun getVideoSearchCache(query: String): BaseResult<List<VideoSearch>>? {
        val minTime = System.currentTimeMillis() - CACHE_DURATION_MS
        val entity = dao.getVideoCache(query, minTime) ?: return null
        
        return try {
            val type = object : TypeToken<List<VideoSearch>>() {}.type
            val data: List<VideoSearch> = gson.fromJson(entity.data, type)
            BaseResult(
                total = entity.total,
                totalHits = entity.totalHits,
                data = data
            )
        } catch (e: Exception) {
            null
        }
    }
    
    override suspend fun cacheImageSearch(
        query: String,
        page: Int,
        perPage: Int,
        result: BaseResult<List<ImageSearch>>
    ) {
        val entity = ImageCacheEntity(
            cacheKey = "${query}_${page}_${perPage}",
            query = query,
            page = page,
            perPage = perPage,
            total = result.total,
            totalHits = result.totalHits,
            data = gson.toJson(result.data),
            cachedAt = System.currentTimeMillis()
        )
        dao.insertImageCache(entity)
    }
    
    override suspend fun getImageSearchCache(
        query: String,
        page: Int,
        perPage: Int
    ): BaseResult<List<ImageSearch>>? {
        val cacheKey = "${query}_${page}_${perPage}"
        val minTime = System.currentTimeMillis() - CACHE_DURATION_MS
        val entity = dao.getImageCache(cacheKey, minTime) ?: return null
        
        return try {
            val type = object : TypeToken<List<ImageSearch>>() {}.type
            val data: List<ImageSearch> = gson.fromJson(entity.data, type)
            BaseResult(
                total = entity.total,
                totalHits = entity.totalHits,
                data = data
            )
        } catch (e: Exception) {
            null
        }
    }
    
    override suspend fun cleanupExpiredCaches() {
        val expiryTime = System.currentTimeMillis() - CACHE_DURATION_MS
        dao.deleteExpiredImageCaches(expiryTime)
        dao.deleteExpiredVideoCaches(expiryTime)
    }
}
package com.tving.core.data.local.datasource

import com.tving.core.domain.model.BaseResult
import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.model.pixabay.VideoSearch

interface PixabayCacheDataSource {
    suspend fun cacheVideoSearch(query: String, result: BaseResult<List<VideoSearch>>)
    suspend fun getVideoSearchCache(query: String): BaseResult<List<VideoSearch>>?
    suspend fun cacheImageSearch(query: String, page: Int, perPage: Int, result: BaseResult<List<ImageSearch>>)
    suspend fun getImageSearchCache(query: String, page: Int, perPage: Int): BaseResult<List<ImageSearch>>?
    suspend fun cleanupExpiredCaches()
}
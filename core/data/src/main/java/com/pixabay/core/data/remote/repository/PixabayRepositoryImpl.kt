package com.pixabay.core.data.remote.repository

import com.pixabay.core.data.local.datasource.PixabayCacheDataSource
import com.pixabay.core.data.mapper.toDomain
import com.pixabay.core.data.remote.datasource.PixabayDataSource
import com.pixabay.core.domain.model.BaseResult
import com.pixabay.core.domain.model.pixabay.ImageSearch
import com.pixabay.core.domain.model.pixabay.VideoSearch
import com.pixabay.core.domain.repository.PixabayRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PixabayRepositoryImpl @Inject constructor(
    private val remoteDataSource: PixabayDataSource,
    private val cacheDataSource: PixabayCacheDataSource
) : PixabayRepository {
    
    override suspend fun searchVideo(query: String): BaseResult<List<VideoSearch>> {
        val cached = cacheDataSource.getVideoSearchCache(query)
        if (cached != null) {
            return cached
        }
        
        val response = remoteDataSource.searchVideo(query)
        val domainResult = response.toDomain()
        cacheDataSource.cacheVideoSearch(query, domainResult)
        
        return domainResult
    }

    override suspend fun searchImage(
        query: String,
        page: Int,
        perPage: Int
    ): BaseResult<List<ImageSearch>> {
        val cached = cacheDataSource.getImageSearchCache(query, page, perPage)
        if (cached != null) {
            return cached
        }
        
        val response = remoteDataSource.searchImage(query, page, perPage)
        val domainResult = response.toDomain()
        cacheDataSource.cacheImageSearch(query, page, perPage, domainResult)
        
        return domainResult
    }
}
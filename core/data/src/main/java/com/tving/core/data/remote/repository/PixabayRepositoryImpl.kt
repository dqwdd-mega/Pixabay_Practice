package com.tving.core.data.remote.repository

import com.tving.core.data.mapper.toDomain
import com.tving.core.data.remote.datasource.PixabayDataSource
import com.tving.core.domain.model.BaseResult
import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.model.pixabay.VideoSearch
import com.tving.core.domain.repository.PixabayRepository
import javax.inject.Inject

class PixabayRepositoryImpl @Inject constructor(
    private val dataSource: PixabayDataSource
) : PixabayRepository {
    override suspend fun searchVideo(query: String): BaseResult<List<VideoSearch>> {
        return dataSource.searchVideo(query).toDomain()
    }

    override suspend fun searchImage(
        query: String,
        page: Int,
        perPage: Int
    ): BaseResult<List<ImageSearch>> {
        return dataSource.searchImage(query, page, perPage).toDomain()
    }
}
package com.tving.core.data.repository

import com.tving.core.data.mapper.toDomain
import com.tving.core.data.remote.PixabayDataSource
import com.tving.core.domain.model.BaseResult
import com.tving.core.domain.model.image.ImageSearch
import com.tving.core.domain.model.video.VideoSearch
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
        imageType: String
    ): BaseResult<List<ImageSearch>> {
        return dataSource.searchImage(query, imageType).toDomain()
    }
}
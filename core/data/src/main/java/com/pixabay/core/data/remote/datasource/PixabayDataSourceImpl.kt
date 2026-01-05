package com.pixabay.core.data.remote.datasource

import com.pixabay.core.data.model.BaseResponse
import com.pixabay.core.data.model.pixabay.ImageSearchResponse
import com.pixabay.core.data.model.pixabay.VideoSearchResponse
import com.pixabay.core.data.remote.service.PixabayService
import javax.inject.Inject

class PixabayDataSourceImpl @Inject constructor(
    private val service: PixabayService,
) : PixabayDataSource {
    override suspend fun searchVideo(
        query: String
    ): BaseResponse<List<VideoSearchResponse>> {
        return service.searchVideo(q = query)
    }

    override suspend fun searchImage(
        query: String,
        page: Int,
        perPage: Int
    ): BaseResponse<List<ImageSearchResponse>> {
        return service.searchImage(
            q = query,
            page = page,
            perPage = perPage
        )
    }
}
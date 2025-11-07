package com.tving.core.data.remote

import com.tving.core.data.model.BaseResponse
import com.tving.core.data.model.image.ImageSearchResponse
import com.tving.core.data.model.video.VideoSearchResponse
import com.tving.core.data.service.PixabayService
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
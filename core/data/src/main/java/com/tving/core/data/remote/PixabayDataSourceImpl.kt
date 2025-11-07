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
        key: String,
        query: String
    ): BaseResponse<List<VideoSearchResponse>> {
        return service.searchVideo(key = key, q = query)
    }

    override suspend fun searchImage(
        key: String,
        query: String,
        imageType: String
    ): BaseResponse<List<ImageSearchResponse>> {
        return service.searchImage(key = key, q = query, imageType = imageType)
    }
}
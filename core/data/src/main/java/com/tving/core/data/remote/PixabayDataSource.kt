package com.tving.core.data.remote

import com.tving.core.data.model.BaseResponse
import com.tving.core.data.model.image.ImageSearchResponse
import com.tving.core.data.model.video.VideoSearchResponse

interface PixabayDataSource {
    suspend fun searchVideo(query: String): BaseResponse<List<VideoSearchResponse>>
    suspend fun searchImage(
        query: String,
        page: Int = 1,
        perPage: Int = 20
    ): BaseResponse<List<ImageSearchResponse>>
}
package com.tving.core.data.remote

import com.tving.core.data.model.BaseResponse
import com.tving.core.data.model.image.ImageSearchResponse
import com.tving.core.data.model.video.VideoSearchResponse

interface PixabayDataSource {
    suspend fun searchVideo(key: String, query: String): BaseResponse<List<VideoSearchResponse>>
    suspend fun searchImage(key: String, query: String, imageType: String): BaseResponse<List<ImageSearchResponse>>
}
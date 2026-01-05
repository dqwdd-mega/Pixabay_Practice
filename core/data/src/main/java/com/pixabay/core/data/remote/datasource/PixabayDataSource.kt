package com.pixabay.core.data.remote.datasource

import com.pixabay.core.data.model.BaseResponse
import com.pixabay.core.data.model.pixabay.ImageSearchResponse
import com.pixabay.core.data.model.pixabay.VideoSearchResponse

interface PixabayDataSource {
    suspend fun searchVideo(query: String): BaseResponse<List<VideoSearchResponse>>
    suspend fun searchImage(
        query: String,
        page: Int = 1,
        perPage: Int = 20
    ): BaseResponse<List<ImageSearchResponse>>
}
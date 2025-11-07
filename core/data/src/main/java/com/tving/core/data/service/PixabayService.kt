package com.tving.core.data.service

import com.tving.core.data.model.BaseResponse
import com.tving.core.data.model.image.ImageSearchResponse
import com.tving.core.data.model.video.VideoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {

    @GET("api/videos/")
    suspend fun searchVideo(
        @Query("key") key: String,
        @Query("q") q: String
    ): BaseResponse<List<VideoSearchResponse>>

    @GET("api/")
    suspend fun searchImage(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("image_type") imageType: String,
    ): BaseResponse<List<ImageSearchResponse>>
}
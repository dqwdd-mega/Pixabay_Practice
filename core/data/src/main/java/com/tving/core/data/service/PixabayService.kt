package com.tving.core.data.service

import com.tving.core.data.model.BaseResponse
import com.tving.core.data.model.image.ImageSearchResponse
import com.tving.core.data.model.video.VideoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {

    @GET("api/videos/")
    suspend fun searchVideo(
        @Query("q") q: String,
        @Query("per_page") perPage: Int = 3
    ): BaseResponse<List<VideoSearchResponse>>

    @GET("api/")
    suspend fun searchImage(
        @Query("q") q: String,
        @Query("image_type") imageType: String = "photo",
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10
    ): BaseResponse<List<ImageSearchResponse>>
}
package com.pixabay.core.data.remote.service

import com.pixabay.core.data.model.BaseResponse
import com.pixabay.core.data.model.pixabay.ImageSearchResponse
import com.pixabay.core.data.model.pixabay.VideoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {

    @GET("api/videos/")
    suspend fun searchVideo(
        @Query("q") q: String,
        @Query("per_page") perPage: Int = 3 // 기본값 20, 사용 가능한 값 3 ~ 200
    ): BaseResponse<List<VideoSearchResponse>>

    @GET("api/")
    suspend fun searchImage(
        @Query("q") q: String,
        @Query("image_type") imageType: String = "photo",
        @Query("page") page: Int = 1, // 기본값 1
        @Query("per_page") perPage: Int = 20 // 기본값 20, 사용 가능한 값 3 ~ 200
    ): BaseResponse<List<ImageSearchResponse>>
}
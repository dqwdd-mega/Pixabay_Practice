package com.tving.core.data.model

import com.google.gson.annotations.SerializedName

/**
 * Pixabay API의 기본 응답 모델
 */
data class BaseResponse<T>(
    @SerializedName("total") val total: Int? = null,
    @SerializedName("totalHits") val totalHits: Int? = null,
    @SerializedName("hits") val hits: T? = null
)
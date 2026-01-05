package com.pixabay.core.data.model.pixabay

import com.google.gson.annotations.SerializedName

/**
 * Pixabay Video API 응답 모델
 */
data class VideoSearchResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("pageURL") val pageURL: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("tags") val tags: String?,
    @SerializedName("duration") val duration: Int?,
    @SerializedName("picture_id") val pictureId: String?,
    @SerializedName("videos") val videos: VideoQuality?,
    @SerializedName("views") val views: Int?,
    @SerializedName("downloads") val downloads: Int?,
    @SerializedName("likes") val likes: Int?,
    @SerializedName("comments") val comments: Int?,
    @SerializedName("user_id") val userId: Int?,
    @SerializedName("user") val user: String?,
    @SerializedName("userImageURL") val userImageURL: String?
)

data class VideoQuality(
    @SerializedName("large") val large: VideoInfo? = null,
    @SerializedName("medium") val medium: VideoInfo? = null,
    @SerializedName("small") val small: VideoInfo? = null,
    @SerializedName("tiny") val tiny: VideoInfo? = null
)

data class VideoInfo(
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("thumbnail") val thumbnail: String? = null
)
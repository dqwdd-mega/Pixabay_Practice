package com.pixabay.core.domain.model.pixabay

data class VideoSearch(
    val id: Int,
    val pageURL: String,
    val type: String,
    val tags: String,
    val duration: Int,
    val pictureId: String,
    val videos: VideoQualityDomain?,
    val views: Int,
    val downloads: Int,
    val likes: Int,
    val comments: Int,
    val userId: Int,
    val user: String,
    val userImageURL: String,
    val localThumbnailPath: String? = null
) {
    val videoUrl: String
        get() {
            return videos?.small?.url ?: "" // small 선택
        }

    val thumbnailUrl: String
        get() {
            return localThumbnailPath ?: (videos?.small?.thumbnail ?: "")
        }
}

data class VideoQualityDomain(
    val large: VideoInfoDomain? = null,
    val medium: VideoInfoDomain? = null,
    val small: VideoInfoDomain? = null,
    val tiny: VideoInfoDomain? = null
)

data class VideoInfoDomain(
    val url: String,
    val width: Int,
    val height: Int,
    val size: Int,
    val thumbnail: String? = null
)
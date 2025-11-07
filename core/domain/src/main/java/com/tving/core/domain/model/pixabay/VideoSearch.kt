package com.tving.core.domain.model.pixabay

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
    val userImageURL: String
) {
    val videoUrl: String
        get() {
            val bestVideo = videos?.small // small 선택
            return bestVideo?.url ?: ""
        }
    
    val thumbnailUrl: String
        get() {
            val bestVideo = videos?.medium ?: videos?.small ?: videos?.tiny ?: videos?.large
            return bestVideo?.thumbnail ?: ""
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
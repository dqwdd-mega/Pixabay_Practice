package com.pixabay.feat.contentdetail.model

import com.pixabay.core.common.util.formatNumber
import com.pixabay.core.domain.model.pixabay.ImageSearch
import com.pixabay.core.domain.model.pixabay.VideoSearch
import com.pixabay.feat.contentdetail.component.StatInfo

data class ContentInfo(
    val id: Int = 0,
    val type: ContentType = ContentType.VIDEO,
    val userName: String = "",
    val userImageUrl: String = "",
    val views: Int = 0,
    val likes: Int = 0,
    val downloads: Int = 0,
    val tags: String = "",
    val videoUrl: String = "",
    val thumbnailUrl: String = "",
    val imageUrl: String = ""
) {
    fun getStats(): List<StatInfo> = listOf(
        StatInfo(label = "Type", value = type.displayName),
        StatInfo(label = "Views", value = views.formatNumber()),
        StatInfo(label = "Likes", value = likes.formatNumber()),
        StatInfo(label = "Downloads", value = downloads.formatNumber())
    )
    companion object {
        fun fromVideo(video: VideoSearch?): ContentInfo {
            val contentInfo = video?.let {
                ContentInfo(
                    id = it.id,
                    type = ContentType.VIDEO,
                    userName = it.user,
                    userImageUrl = it.userImageURL,
                    views = it.views,
                    likes = it.likes,
                    downloads = it.downloads,
                    tags = it.tags,
                    videoUrl = it.videoUrl,
                    thumbnailUrl = it.thumbnailUrl
                )
            } ?: ContentInfo()

            return contentInfo
        }
        
        fun fromImage(image: ImageSearch?): ContentInfo {
            val contentInfo = image?.let {
                ContentInfo(
                    id = it.id,
                    type = ContentType.IMAGE,
                    userName = it.user,
                    userImageUrl = it.userImageURL,
                    views = it.views,
                    likes = it.likes,
                    downloads = it.downloads,
                    tags = it.tags,
                    imageUrl = it.largeImageURL
                )
            } ?: ContentInfo()

            return contentInfo
        }
    }
}

enum class ContentType(val displayName: String) {
    VIDEO("Video"),
    IMAGE("Photo")
}
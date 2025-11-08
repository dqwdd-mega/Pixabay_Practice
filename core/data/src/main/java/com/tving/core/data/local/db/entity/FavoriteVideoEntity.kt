package com.tving.core.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tving.core.domain.model.pixabay.VideoQualityDomain
import com.tving.core.domain.model.pixabay.VideoSearch

@Entity(tableName = "favorite_videos")
data class FavoriteVideoEntity(
    @PrimaryKey
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
    val createdAt: Long = System.currentTimeMillis(),
    val localThumbnailPath: String? = null
) {
    fun toDomain(): VideoSearch {
        return VideoSearch(
            id = id,
            pageURL = pageURL,
            type = type,
            tags = tags,
            duration = duration,
            pictureId = pictureId,
            videos = videos,
            views = views,
            downloads = downloads,
            likes = likes,
            comments = comments,
            userId = userId,
            user = user,
            userImageURL = userImageURL,
            localThumbnailPath = localThumbnailPath
        )
    }

    companion object {
        fun fromDomain(video: VideoSearch): FavoriteVideoEntity {
            return FavoriteVideoEntity(
                id = video.id,
                pageURL = video.pageURL,
                type = video.type,
                tags = video.tags,
                duration = video.duration,
                pictureId = video.pictureId,
                videos = video.videos,
                views = video.views,
                downloads = video.downloads,
                likes = video.likes,
                comments = video.comments,
                userId = video.userId,
                user = video.user,
                userImageURL = video.userImageURL,
                localThumbnailPath = video.localThumbnailPath
            )
        }
    }
}
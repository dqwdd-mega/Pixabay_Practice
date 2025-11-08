package com.tving.core.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tving.core.domain.model.pixabay.ImageSearch

@Entity(tableName = "favorite_images")
data class FavoriteImageEntity(
    @PrimaryKey
    val id: Int,
    val pageURL: String,
    val type: String,
    val tags: String,
    val previewURL: String,
    val webformatURL: String,
    val largeImageURL: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val views: Int,
    val downloads: Int,
    val likes: Int,
    val comments: Int,
    val userId: Int,
    val user: String,
    val userImageURL: String,
    val createdAt: Long = System.currentTimeMillis(),
    val localPreviewPath: String? = null
) {
    fun toDomain(): ImageSearch {
        return ImageSearch(
            id = id,
            pageURL = pageURL,
            type = type,
            tags = tags,
            previewURL = previewURL,
            webformatURL = webformatURL,
            largeImageURL = largeImageURL,
            imageWidth = imageWidth,
            imageHeight = imageHeight,
            views = views,
            downloads = downloads,
            likes = likes,
            comments = comments,
            userId = userId,
            user = user,
            userImageURL = userImageURL,
            localPreviewPath = localPreviewPath
        )
    }

    companion object {
        fun fromDomain(image: ImageSearch): FavoriteImageEntity {
            return FavoriteImageEntity(
                id = image.id,
                pageURL = image.pageURL,
                type = image.type,
                tags = image.tags,
                previewURL = image.previewURL,
                webformatURL = image.webformatURL,
                largeImageURL = image.largeImageURL,
                imageWidth = image.imageWidth,
                imageHeight = image.imageHeight,
                views = image.views,
                downloads = image.downloads,
                likes = image.likes,
                comments = image.comments,
                userId = image.userId,
                user = image.user,
                userImageURL = image.userImageURL,
                localPreviewPath = image.localPreviewPath
            )
        }
    }
}
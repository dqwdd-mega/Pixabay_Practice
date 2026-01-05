package com.pixabay.core.data.local.datasource

import com.pixabay.core.data.local.db.dao.FavoriteDao
import com.pixabay.core.data.local.db.entity.FavoriteImageEntity
import com.pixabay.core.data.local.db.entity.FavoriteVideoEntity
import com.pixabay.core.data.local.utils.ImageDownloadManager
import com.pixabay.core.domain.model.pixabay.ImageSearch
import com.pixabay.core.domain.model.pixabay.VideoSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteVideoDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
    private val imageDownloadManager: ImageDownloadManager
) : FavoriteVideoDataSource {

    override fun getFavoriteVideos(): Flow<List<VideoSearch>> {
        return favoriteDao.getFavoriteVideos().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addFavoriteVideo(video: VideoSearch) {
        // Pixabay Hotlinking 정책 준수: 썸네일 이미지 로컬에 저장
        val thumbnailUrl = video.videos?.small?.thumbnail
        val localThumbnailPath = if (!thumbnailUrl.isNullOrEmpty()) {
            imageDownloadManager.downloadVideoThumbnail(thumbnailUrl, video.id)
        } else {
            null
        }

        val videoWithLocalPath = video.copy(localThumbnailPath = localThumbnailPath)
        favoriteDao.insertFavoriteVideo(FavoriteVideoEntity.fromDomain(videoWithLocalPath))
    }

    override suspend fun removeFavoriteVideo(videoId: Int) {
        imageDownloadManager.deleteVideoThumbnail(videoId)
        favoriteDao.deleteFavoriteVideo(videoId)
    }

    override suspend fun isFavoriteVideo(videoId: Int): Boolean {
        return favoriteDao.isFavoriteVideo(videoId)
    }

    override fun getFavoriteImages(): Flow<List<ImageSearch>> {
        return favoriteDao.getFavoriteImages().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addFavoriteImage(image: ImageSearch) {
        // Pixabay Hotlinking 정책 준수: preview 이미지 로컬에 저장
        val localPreviewPath = if (image.previewURL.isNotEmpty()) {
            imageDownloadManager.downloadImagePreview(image.previewURL, image.id)
        } else {
            null
        }
        
        val imageWithLocalPath = image.copy(localPreviewPath = localPreviewPath)
        favoriteDao.insertFavoriteImage(FavoriteImageEntity.fromDomain(imageWithLocalPath))
    }

    override suspend fun removeFavoriteImage(imageId: Int) {
        imageDownloadManager.deleteImageFiles(imageId)
        favoriteDao.deleteFavoriteImage(imageId)
    }

    override suspend fun isFavoriteImage(imageId: Int): Boolean {
        return favoriteDao.isFavoriteImage(imageId)
    }
}
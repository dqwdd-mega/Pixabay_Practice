package com.tving.core.data.local.datasource

import com.tving.core.data.local.db.dao.FavoriteDao
import com.tving.core.data.local.db.entity.FavoriteImageEntity
import com.tving.core.data.local.db.entity.FavoriteVideoEntity
import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.model.pixabay.VideoSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteVideoDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteVideoDataSource {

    override fun getFavoriteVideos(): Flow<List<VideoSearch>> {
        return favoriteDao.getFavoriteVideos().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addFavoriteVideo(video: VideoSearch) {
        favoriteDao.insertFavoriteVideo(FavoriteVideoEntity.fromDomain(video))
    }

    override suspend fun removeFavoriteVideo(videoId: Int) {
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
        favoriteDao.insertFavoriteImage(FavoriteImageEntity.fromDomain(image))
    }

    override suspend fun removeFavoriteImage(imageId: Int) {
        favoriteDao.deleteFavoriteImage(imageId)
    }

    override suspend fun isFavoriteImage(imageId: Int): Boolean {
        return favoriteDao.isFavoriteImage(imageId)
    }
}
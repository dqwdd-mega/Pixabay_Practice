package com.pixabay.core.data.local.repository

import com.pixabay.core.data.local.datasource.FavoriteVideoDataSource
import com.pixabay.core.domain.model.pixabay.ImageSearch
import com.pixabay.core.domain.model.pixabay.VideoSearch
import com.pixabay.core.domain.repository.datastore.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dataSource: FavoriteVideoDataSource
) : FavoriteRepository {

    override fun getFavoriteVideos(): Flow<List<VideoSearch>> {
        return dataSource.getFavoriteVideos()
    }

    override suspend fun addFavoriteVideo(video: VideoSearch) {
        dataSource.addFavoriteVideo(video)
    }

    override suspend fun removeFavoriteVideo(videoId: Int) {
        dataSource.removeFavoriteVideo(videoId)
    }

    override suspend fun isFavoriteVideo(videoId: Int): Boolean {
        return dataSource.isFavoriteVideo(videoId)
    }

    override fun getFavoriteImages(): Flow<List<ImageSearch>> {
        return dataSource.getFavoriteImages()
    }

    override suspend fun addFavoriteImage(image: ImageSearch) {
        dataSource.addFavoriteImage(image)
    }

    override suspend fun removeFavoriteImage(imageId: Int) {
        dataSource.removeFavoriteImage(imageId)
    }

    override suspend fun isFavoriteImage(imageId: Int): Boolean {
        return dataSource.isFavoriteImage(imageId)
    }
}
package com.tving.core.data.local.repository

import com.tving.core.data.local.datasource.FavoriteVideoDataSource
import com.tving.core.domain.model.pixabay.VideoSearch
import com.tving.core.domain.repository.FavoriteRepository
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
}
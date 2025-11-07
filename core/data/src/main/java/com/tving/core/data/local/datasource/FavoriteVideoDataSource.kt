package com.tving.core.data.local.datasource

import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.model.pixabay.VideoSearch
import kotlinx.coroutines.flow.Flow

interface FavoriteVideoDataSource {
    fun getFavoriteVideos(): Flow<List<VideoSearch>>
    suspend fun addFavoriteVideo(video: VideoSearch)
    suspend fun removeFavoriteVideo(videoId: Int)
    suspend fun isFavoriteVideo(videoId: Int): Boolean
    
    fun getFavoriteImages(): Flow<List<ImageSearch>>
    suspend fun addFavoriteImage(image: ImageSearch)
    suspend fun removeFavoriteImage(imageId: Int)
    suspend fun isFavoriteImage(imageId: Int): Boolean
}
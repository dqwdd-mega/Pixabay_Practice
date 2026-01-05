package com.pixabay.core.domain.usecase

import com.pixabay.core.domain.model.pixabay.VideoSearch
import com.pixabay.core.domain.repository.datastore.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteVideosUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<VideoSearch>> {
        return repository.getFavoriteVideos()
    }
}
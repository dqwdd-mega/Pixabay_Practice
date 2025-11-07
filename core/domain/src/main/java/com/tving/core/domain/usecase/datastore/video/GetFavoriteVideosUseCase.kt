package com.tving.core.domain.usecase

import com.tving.core.domain.model.pixabay.VideoSearch
import com.tving.core.domain.repository.datastore.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteVideosUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<VideoSearch>> {
        return repository.getFavoriteVideos()
    }
}
package com.tving.core.domain.usecase

import com.tving.core.domain.repository.FavoriteRepository
import javax.inject.Inject

class IsFavoriteVideoUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(videoId: Int): Boolean {
        return repository.isFavoriteVideo(videoId)
    }
}
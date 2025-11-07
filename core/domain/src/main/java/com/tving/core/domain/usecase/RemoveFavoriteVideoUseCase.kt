package com.tving.core.domain.usecase

import com.tving.core.domain.repository.FavoriteRepository
import javax.inject.Inject

class RemoveFavoriteVideoUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(videoId: Int) {
        repository.removeFavoriteVideo(videoId)
    }
}
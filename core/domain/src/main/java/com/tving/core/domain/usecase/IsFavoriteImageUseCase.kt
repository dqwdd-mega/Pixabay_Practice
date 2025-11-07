package com.tving.core.domain.usecase

import com.tving.core.domain.repository.FavoriteRepository
import javax.inject.Inject

class IsFavoriteImageUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(imageId: Int): Boolean {
        return repository.isFavoriteImage(imageId)
    }
}
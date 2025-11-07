package com.tving.core.domain.usecase

import com.tving.core.domain.repository.datastore.FavoriteRepository
import javax.inject.Inject

class RemoveFavoriteImageUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(imageId: Int) {
        repository.removeFavoriteImage(imageId)
    }
}
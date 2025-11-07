package com.tving.core.domain.usecase

import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.repository.datastore.FavoriteRepository
import javax.inject.Inject

class AddFavoriteImageUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(image: ImageSearch) {
        repository.addFavoriteImage(image)
    }
}
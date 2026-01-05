package com.pixabay.core.domain.usecase

import com.pixabay.core.domain.model.pixabay.ImageSearch
import com.pixabay.core.domain.repository.datastore.FavoriteRepository
import javax.inject.Inject

class AddFavoriteImageUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(image: ImageSearch) {
        repository.addFavoriteImage(image)
    }
}
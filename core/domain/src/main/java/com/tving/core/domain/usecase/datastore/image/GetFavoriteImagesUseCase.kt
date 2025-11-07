package com.tving.core.domain.usecase

import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.repository.datastore.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteImagesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<ImageSearch>> {
        return repository.getFavoriteImages()
    }
}
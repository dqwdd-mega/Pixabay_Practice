package com.pixabay.core.domain.usecase

import com.pixabay.core.domain.model.pixabay.VideoSearch
import com.pixabay.core.domain.repository.datastore.FavoriteRepository
import javax.inject.Inject

class AddFavoriteVideoUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(video: VideoSearch) {
        repository.addFavoriteVideo(video)
    }
}
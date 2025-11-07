package com.tving.core.domain.usecase

import com.tving.core.domain.model.pixabay.VideoSearch
import com.tving.core.domain.repository.datastore.FavoriteRepository
import javax.inject.Inject

class AddFavoriteVideoUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(video: VideoSearch) {
        repository.addFavoriteVideo(video)
    }
}
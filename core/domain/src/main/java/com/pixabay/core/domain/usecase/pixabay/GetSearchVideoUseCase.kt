package com.pixabay.core.domain.usecase

import com.pixabay.core.domain.model.BaseResult
import com.pixabay.core.domain.model.pixabay.VideoSearch
import com.pixabay.core.domain.repository.PixabayRepository
import javax.inject.Inject

class GetSearchVideoUseCase @Inject constructor(
    private val repository: PixabayRepository
) {
    suspend operator fun invoke(query: String): BaseResult<List<VideoSearch>> {
        return repository.searchVideo(query)
    }
}
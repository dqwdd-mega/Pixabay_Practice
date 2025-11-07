package com.tving.core.domain.usecase

import com.tving.core.domain.model.BaseResult
import com.tving.core.domain.model.image.ImageSearch
import com.tving.core.domain.repository.PixabayRepository
import javax.inject.Inject

class GetSearchImageUseCase @Inject constructor(
    private val repository: PixabayRepository
) {
    suspend operator fun invoke(
        query: String,
        imageType: String = "all"
    ): BaseResult<List<ImageSearch>> {
        return repository.searchImage(query, imageType)
    }
}
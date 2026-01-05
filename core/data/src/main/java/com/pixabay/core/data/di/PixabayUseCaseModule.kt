package com.pixabay.core.data.di

import com.pixabay.core.domain.repository.PixabayRepository
import com.pixabay.core.domain.usecase.GetSearchImageUseCase
import com.pixabay.core.domain.usecase.GetSearchVideoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PixabayUseCaseModule {

    @Singleton
    @Provides
    fun provideGetSearchVideoUseCase(
        repository: PixabayRepository
    ): GetSearchVideoUseCase {
        return GetSearchVideoUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideGetSearchImageUseCase(
        repository: PixabayRepository
    ): GetSearchImageUseCase {
        return GetSearchImageUseCase(repository = repository)
    }
}
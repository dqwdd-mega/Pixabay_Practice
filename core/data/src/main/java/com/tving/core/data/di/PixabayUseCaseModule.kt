package com.tving.core.data.di

import com.tving.core.domain.repository.PixabayRepository
import com.tving.core.domain.usecase.GetSearchImageUseCase
import com.tving.core.domain.usecase.GetSearchVideoUseCase
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
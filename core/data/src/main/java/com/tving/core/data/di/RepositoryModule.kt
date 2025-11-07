package com.tving.core.data.di

import com.tving.core.data.repository.PixabayRepositoryImpl
import com.tving.core.domain.repository.PixabayRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPixabayRepository(
        pixabayRepositoryImpl: PixabayRepositoryImpl
    ): PixabayRepository
}
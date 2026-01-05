package com.pixabay.core.data.di

import com.pixabay.core.data.remote.repository.PixabayRepositoryImpl
import com.pixabay.core.domain.repository.PixabayRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPixabayRepository(
        pixabayRepositoryImpl: PixabayRepositoryImpl
    ): PixabayRepository
}
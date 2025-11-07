package com.tving.core.data.di

import com.tving.core.data.local.datasource.FavoriteVideoDataSource
import com.tving.core.data.local.datasource.FavoriteVideoDataSourceImpl
import com.tving.core.data.local.repository.FavoriteRepositoryImpl
import com.tving.core.domain.repository.datastore.FavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteModule {

    @Binds
    @Singleton
    abstract fun bindFavoriteVideoDataSource(
        impl: FavoriteVideoDataSourceImpl
    ): FavoriteVideoDataSource

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(
        impl: FavoriteRepositoryImpl
    ): FavoriteRepository
}
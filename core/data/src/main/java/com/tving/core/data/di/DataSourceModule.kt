package com.tving.core.data.di

import com.tving.core.data.remote.PixabayDataSource
import com.tving.core.data.remote.PixabayDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindPixabayDataSource(
        pixabayDataSourceImpl: PixabayDataSourceImpl
    ): PixabayDataSource
}
package com.pixabay.core.data.di

import com.pixabay.core.data.local.datasource.PixabayCacheDataSource
import com.pixabay.core.data.local.datasource.PixabayCacheDataSourceImpl
import com.pixabay.core.data.remote.datasource.PixabayDataSource
import com.pixabay.core.data.remote.datasource.PixabayDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindPixabayDataSource(
        pixabayDataSourceImpl: PixabayDataSourceImpl
    ): PixabayDataSource
    
    @Binds
    @Singleton
    abstract fun bindPixabayCacheDataSource(
        pixabayCacheDataSourceImpl: PixabayCacheDataSourceImpl
    ): PixabayCacheDataSource
}
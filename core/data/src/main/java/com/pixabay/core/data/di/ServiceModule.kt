package com.pixabay.core.data.di

import com.pixabay.core.data.remote.service.PixabayService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providePixabayService(
        retrofit: Retrofit
    ): PixabayService {
        return retrofit.create(PixabayService::class.java)
    }
}
package com.tving.core.data.di

import android.content.Context
import androidx.room.Room
import com.tving.core.data.local.dao.PixabayCacheDao
import com.tving.core.data.local.database.PixabayDatabase
import com.tving.core.data.local.db.FavoriteDatabase
import com.tving.core.data.local.db.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun providePixabayDatabase(
        @ApplicationContext context: Context
    ): PixabayDatabase {
        return Room.databaseBuilder(
            context,
            PixabayDatabase::class.java,
            "pixabay_cache.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    
    @Provides
    @Singleton
    fun providePixabayCacheDao(
        database: PixabayDatabase
    ): PixabayCacheDao {
        return database.pixabayCacheDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteDatabase(
        @ApplicationContext context: Context
    ): FavoriteDatabase {
        return Room.databaseBuilder(
            context,
            FavoriteDatabase::class.java,
            "favorite.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(
        database: FavoriteDatabase
    ): FavoriteDao {
        return database.favoriteDao()
    }
}
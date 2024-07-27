package com.dar_hav_projects.newsapp.di

import android.app.Application
import androidx.room.Room
import com.dar_hav_projects.newsapp.db.MainDb
import com.dar_hav_projects.newsapp.db.NewsItemsRepImpl
import com.dar_hav_projects.newsapp.db.NewsItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMainDb(app: Application): MainDb {
        return Room.databaseBuilder(
            app,
            MainDb::class.java,
            "main_data_base"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNewsItemRepo(db: MainDb): NewsItemsRepository {
        return NewsItemsRepImpl(db.listItemDao)
    }
}
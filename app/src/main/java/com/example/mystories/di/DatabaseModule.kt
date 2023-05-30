package com.example.mystories.di

import android.content.Context
import androidx.room.Room
import com.example.mystories.local.room.RemoteKeysDao
import com.example.mystories.local.room.StoryDao
import com.example.mystories.local.room.StoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideStoryDao(storyDatabase: StoryDatabase): StoryDao = storyDatabase.storyDao()

    @Provides
    fun provideRemoteKeysDao(storyDatabase: StoryDatabase): RemoteKeysDao = storyDatabase.remoteKeysDao()

    @Provides
    @Singleton
    fun provideStoryDatabase(@ApplicationContext context: Context) : StoryDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            StoryDatabase::class.java,
            "story_database"
        ).build()
    }
}
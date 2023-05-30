package com.example.mystories.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mystories.local.entity.RemoteKeys
import com.example.mystories.local.entity.Story

@Database(
    entities = [Story::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)

abstract class StoryDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
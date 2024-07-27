package com.dar_hav_projects.newsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NewsItem::class],
    version = 1
)
abstract class MainDb: RoomDatabase() {
    abstract val listItemDao: NewsItemDao
}
package com.dar_hav_projects.newsapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dar_hav_projects.newsapp.data.ArticleModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: NewsItem)
    @Delete
    suspend fun deleteItem(item: NewsItem)

    @Query("SELECT * FROM news_items")
    fun getAllItems(): Flow<List<NewsItem>>

}
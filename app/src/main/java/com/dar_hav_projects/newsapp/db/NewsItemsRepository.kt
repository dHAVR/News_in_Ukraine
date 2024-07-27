package com.dar_hav_projects.newsapp.db

import com.dar_hav_projects.newsapp.data.ArticleModel
import kotlinx.coroutines.flow.Flow

interface NewsItemsRepository {
    suspend fun insertItem(item: NewsItem)
    suspend fun deleteItem(item: NewsItem)
    fun getAllItems(): Flow<List<NewsItem>>

}
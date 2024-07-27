package com.dar_hav_projects.newsapp.db

import com.dar_hav_projects.newsapp.data.ArticleModel
import kotlinx.coroutines.flow.Flow

class NewsItemsRepImpl (
    private val dao: NewsItemDao
): NewsItemsRepository {
    override suspend fun insertItem(item: NewsItem) {
        dao.insertItem(item)
    }

    override suspend fun deleteItem(item: NewsItem) {
        dao.deleteItem(item)
    }

    override fun getAllItems(): Flow<List<NewsItem>> {
        return dao.getAllItems()
    }

    }
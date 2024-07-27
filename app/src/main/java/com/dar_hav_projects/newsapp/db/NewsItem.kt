package com.dar_hav_projects.newsapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_items")
data class NewsItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val source: String?,
    val author: String?,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    val isSaved: Boolean = false
)


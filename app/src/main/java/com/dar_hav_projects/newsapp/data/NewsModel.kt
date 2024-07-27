package com.dar_hav_projects.newsapp.data
data class NewsListModel(
    val articles: List<ArticleModel>
)

data class ArticleModel(
    val source: SourceModel,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)

data class SourceModel(
    val id: String?,
    val name: String
)
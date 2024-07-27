package com.dar_hav_projects.newsapp.retrofit

import com.dar_hav_projects.newsapp.data.NewsListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MainAPI {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
    ): NewsListModel
}
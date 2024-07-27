package com.dar_hav_projects.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.dar_hav_projects.newsapp.data.ArticleModel
import com.dar_hav_projects.newsapp.db.NewsItem
import com.dar_hav_projects.newsapp.retrofit.MainAPI
import com.dar_hav_projects.newsapp.screens.MainScreen
import com.dar_hav_projects.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val API_KEY = "71b7ac5d666348da92de0b91b59683c5"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                val newsList = remember {
                    mutableStateOf(listOf<NewsItem>())
                }

                var searchText by remember {
                    mutableStateOf("")
                }

                var active by remember {
                    mutableStateOf(false)
                }


                val filteredNewsList by remember {
                derivedStateOf {
                    newsList.value.filter { item ->
                        item.title.contains(searchText, ignoreCase = true)
                      }
                     }
                }

                val mutableFilteredNewsList = remember {
                    mutableStateOf<List<NewsItem>>(emptyList())
                }

                LaunchedEffect(filteredNewsList) {
                    mutableFilteredNewsList.value = filteredNewsList
                }

                val coroutineScope = rememberCoroutineScope()

                getData(newsList, coroutineScope)

                Column(modifier = Modifier.fillMaxSize()) {
                    SearchBar(
                        modifier = Modifier.fillMaxWidth(),
                        query = searchText,
                        onQueryChange = {
                            searchText = it
                        },
                        onSearch = {
                            active = false
                        },
                        active = active,
                        onActiveChange = {
                           active = it
                        },
                        placeholder = {
                            Text(text = "Search")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
                        },
                        trailingIcon = {
                            if(active) {
                                Icon(
                                    modifier = Modifier.clickable {
                                        if(searchText.isNotEmpty()){
                                            searchText = ""
                                        }else{
                                            active = false
                                        }
                                    },
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "clean"
                                )
                            }
                        }
                    ) {

                    }
                    MainScreen(mutableFilteredNewsList)
                }
               
            }
        }
    }
}

private fun getData(newsList: MutableState<List<NewsItem>>, coroutineScope: CoroutineScope) {
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(MainAPI::class.java)

    coroutineScope.launch {
        try {
            val response = api.getNews("ua", API_KEY)
            if (response.articles.isNotEmpty()) {
                val newsItems = response.articles.map { articleToNewsItem(it) }
                newsList.value = newsItems
                Log.d("MyLog", "Articles: ${newsList.value}")
            }
        } catch (e: Exception) {
            Log.e("MyLog", "Error fetching news: ${e.localizedMessage}", e)
        }
    }
}


fun articleToNewsItem(article: ArticleModel): NewsItem {
    return NewsItem(
        title = article.title,
        source = article.source.name,
        author = article.author ?: "Unknown",
        description = article.description,
        url = article.url,
        urlToImage = article.urlToImage,
        publishedAt = article.publishedAt,
        content = article.content
    )
}




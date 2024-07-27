package com.dar_hav_projects.newsapp.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dar_hav_projects.newsapp.db.NewsItem
import com.dar_hav_projects.newsapp.db.NewsItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: NewsItemsRepository,
) : ViewModel(){

    val list = repository.getAllItems()
  fun OnEvent(event: MainScreenEvent, item: NewsItem, context: Context){
        when(event){
            is MainScreenEvent.OnItemSave->{
                viewModelScope.launch {
                    if (!item.isSaved) {
                        repository.insertItem(
                            NewsItem(
                                null,
                                item.title ?: "No name",
                                item.source ?: "No source",
                                item.author ?: "Unknown",
                                item.description ?: "No description",
                                item.url,
                                item.urlToImage ?: "",
                                item.publishedAt ?: "",
                                item.content ?: "",
                                true
                            )
                        )
                    }else{
                        repository.deleteItem(item)
                    }
                }

            }
            is MainScreenEvent.OnShowArticle->{
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(item.url)
                }
                context.startActivity(intent)
            }

            is MainScreenEvent.getAllNews ->{
                viewModelScope.launch {
                    repository.getAllItems()
                  }
                }
            }
        }
    }

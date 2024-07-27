package com.dar_hav_projects.newsapp.screens

sealed class MainScreenEvent{
    object OnShowArticle: MainScreenEvent()
    object OnItemSave: MainScreenEvent()
    object getAllNews: MainScreenEvent()
}

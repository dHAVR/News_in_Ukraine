package com.dar_hav_projects.newsapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(publishedAt: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

     try {
        val date: Date = inputFormat.parse(publishedAt)
        return outputFormat.format(date)
    } catch (e: Exception) {
        return publishedAt
    }
}
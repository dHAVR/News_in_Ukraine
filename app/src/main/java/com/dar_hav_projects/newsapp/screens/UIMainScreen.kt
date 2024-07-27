package com.dar_hav_projects.newsapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dar_hav_projects.newsapp.db.NewsItem
import com.dar_hav_projects.newsapp.utils.formatDate


@Composable
fun MainList(list: State<List<NewsItem>>, viewModel: MainScreenViewModel){
    LazyColumn(modifier = Modifier.fillMaxSize() ){
        itemsIndexed(
            list.value
        ) {
                _, item->
            ListItem(item, viewModel)
        }
    }
}

@Composable
fun ListItem(item: NewsItem, viewModel: MainScreenViewModel) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 3.dp, end = 3.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 20.dp
        ),
        shape = RoundedCornerShape(20.dp),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            IconButton(
                onClick = {
                    viewModel.OnEvent(MainScreenEvent.OnItemSave, item, context)
                },
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(30.dp)
            ) {
                if(!item.isSaved) {
                    Icon(
                        imageVector = Icons.Filled.StarOutline,
                        contentDescription = "Add",
                    )
                }else{
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Add",
                    )
                }
            }
            if (!item.urlToImage.isNullOrEmpty()) {
                AsyncImage(
                    model = "${item.urlToImage}",
                    contentDescription = item.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(end = 10.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = {
                        viewModel.OnEvent(MainScreenEvent.OnShowArticle, item, context)
                    },
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForwardIos,
                        contentDescription = "Open",
                    )
                }
            }

        }
        Text(
            modifier = Modifier.padding(start = 20.dp, bottom = 10.dp),
            text = formatDate(item.publishedAt),
            fontSize = 12.sp
        )
    }
}


data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)

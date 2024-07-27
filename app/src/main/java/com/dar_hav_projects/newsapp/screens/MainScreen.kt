package com.dar_hav_projects.newsapp.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.dar_hav_projects.newsapp.db.NewsItem
import com.dar_hav_projects.newsapp.ui.theme.Blue
import com.dar_hav_projects.newsapp.utils.formatDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    newList: MutableState<List<NewsItem>>,
    viewModel: MainScreenViewModel = hiltViewModel()


    ) {

    val tabItems = listOf(
        TabItem(
            "Top News",
            Icons.Outlined.Newspaper,
            Icons.Filled.Newspaper
        ),
        TabItem(
            "Saved News",
            Icons.Outlined.StarOutline,
            Icons.Filled.Star
        )
    )

    var selectedTab by remember {
        mutableStateOf(0)
    }

    val pagerState = rememberPagerState {
        tabItems.size
    }
    LaunchedEffect(selectedTab) {
        pagerState.animateScrollToPage(selectedTab)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if(!pagerState.isScrollInProgress) {
            selectedTab = pagerState.currentPage
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(selectedTabIndex = selectedTab) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = index == selectedTab,
                    onClick = {
                        selectedTab = index
                    },
                    text = {
                        Text(text = item.title)
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedTab) {
                                item.selectedIcon
                            } else {
                                item.unselectedIcon
                            },
                            contentDescription = item.title
                        )
                    }
                )
            }
        }
        
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {index ->
            val list = when(index){
                0->{
                    newList
                }
                1->{
                     viewModel.list.collectAsState(initial = emptyList())
                }
                else->{
                    newList
                }
            }
            MainList(list,viewModel)
        }
    }
}

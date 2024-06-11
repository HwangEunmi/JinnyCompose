@file:OptIn(ExperimentalFoundationApi::class)

package com.study.jinnycompose.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.study.jinnycompose.model.HomeListItem
import com.study.jinnycompose.ui.component.ImageCard
import kotlinx.coroutines.launch

@Composable
fun TabPagerContent(
    item: HomeListItem.PagerItem
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState {
        item.imageList.size
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PagingContent(
            pagerState = pagerState,
            imageList = item.imageList
        )

        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth(),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = Color.Red
                )
            },
            edgePadding = 0.dp
        ) {
            item.imageList.forEachIndexed { index, imageUrl ->
                Tab(
                    modifier = Modifier
                        .padding(horizontal = 0.dp),
                    selected = (pagerState.currentPage == index),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }) {
                    ImageCard(
                        imageUrl = imageUrl,
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(50.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 10.dp,
                                    topEnd = 10.dp
                                )
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun PagingContent(
    pagerState: PagerState,
    imageList: List<String>
) {
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState
    ) { page ->
        Column {
            val imageUrl = imageList[page]
            ImageCard(
                imageUrl = imageUrl,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
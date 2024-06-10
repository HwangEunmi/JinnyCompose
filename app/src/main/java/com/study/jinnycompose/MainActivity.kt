package com.study.jinnycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.study.jinnycompose.model.HomeListItem
import com.study.jinnycompose.ui.component.AppBar
import com.study.jinnycompose.ui.theme.JinnyComposeTheme
import kotlin.math.min

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JinnyComposeTheme {
                MainHome()
            }
        }
    }
}

@ExperimentalMaterial3Api
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainHome(
    viewModel: MainViewModel = viewModel()
) {
    // Column + Modifier.verticalScroll은 적은 수의 아이템을
    // 간단하게 스크롤할 때 사용한다.
    // 모든 아이템을 한번에 렌더링하므로 많은 수의 아이템에 적합하지 않다.
    // val scrollState = rememberScrollState()
    val scrollState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
            // .verticalScroll(scrollState)
        ) {
            itemsIndexed(viewModel.homeList) { index, item ->
                when (item) {
                    is HomeListItem.TopPagingContent -> {
                        TabPagerContent(
                            item = item,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    is HomeListItem.ImageContent -> {
                        ListContent(
                            imageList = item.imageList,
                            onClickEvent = {},
                            onSelectedEvent = { true },
                            modifier = Modifier
                                .wrapContentWidth()
                        )
                    }


                    is HomeListItem.Info -> {

                    }
                }
            }
        }

        MainAppBar(
            scrollState = scrollState
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun MainAppBar(
    scrollState: LazyListState
) {
    // 현재 LazyColumn의 MultiViewType으로 구성했기 때문에
    // 각 아이템의 높이를 구해서 '현재까지의 아이템 높이 / 전체 스크롤의 높이'로 구하려면
    // 각 항목의 아이템 높이를 구할때부터 코드가 꽤 복잡해짐 (visibleItemsInfo에서 first, last 항목만 가지고 올 수 있어서 Iterator 돌려야될 듯)
    // 그래서 Index의 비율로 계산
    val currentScrollPositionPercentage by remember {
        derivedStateOf {
            val layoutInfo = scrollState.layoutInfo // 현재 레이아웃의 상태 정보
            val totalItemsCount = layoutInfo.totalItemsCount // 전체 항목 수
            val visibleItemsInfo = layoutInfo.visibleItemsInfo // 현재 화면에 보이는 항목들의 정보

            if (visibleItemsInfo.isNotEmpty()) {
                val countOfItemsUpToIndex = visibleItemsInfo.last().index + 1
                min(1f, countOfItemsUpToIndex.toFloat() / totalItemsCount)
            } else {
                0f
            }
        }
    }

    val iconColor = Color(
        red = lerp(1f, 0f, currentScrollPositionPercentage),
        green = lerp(1f, 0f, currentScrollPositionPercentage),
        blue = lerp(1f, 0f, currentScrollPositionPercentage)
    )

    AppBar(
        title = null,
        appBarColor = {
            TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White.copy(
                    alpha = currentScrollPositionPercentage
                ),
                navigationIconContentColor = iconColor,
                actionIconContentColor = iconColor,
            )
        },
        onNavigationIconClick = { /*TODO*/ },
        onLikeIconClick = { /*TODO*/ },
        onShareIconClick = { /*TODO*/ })
@ExperimentalCoroutinesApi
@Composable
fun MainBottomLayout(
    scrollState: LazyListState,
    modifier: Modifier = Modifier
) {
    val isScrollUp = scrollState.getScrollDirection() == ScrollDirection.Up
    val isScrollStop = scrollState.getScrollStateAfterStop() == ScrollState.Stop
    AnimatedVisibility(
        visible = isScrollUp || isScrollStop,
        enter = slideInVertically { height ->
            height
        },
        exit = slideOutVertically { height ->
            height
        },
        modifier = modifier
    ) {
        BottomButtonLayout(
            layoutHeight = 100.dp,
            onButtonClick = {},
            modifier
        )
    }
}

@ExperimentalFoundationApi
@Composable
private fun TabPagerContent(
    item: HomeListItem.TopPagingContent,
    modifier: Modifier = Modifier
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
            imageList = item.imageList,
            modifier = Modifier.fillMaxSize()
        )

        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth()
        ) {
            item.imageList.forEachIndexed { index, imageUrl ->
                Tab(
                    selected = (pagerState.currentPage == index),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }) {
                    ImageCard(
                        imageUrl = imageUrl,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(text = "")
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagingContent(
    pagerState: PagerState,
    imageList: List<String>,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        modifier = Modifier.size(Dp(500.0f)),
        // modifier = modifier,
        state = pagerState
    ) {
        Column(
            modifier = modifier
        ) {
            val imageUrl = imageList[pagerState.currentPage]
            ImageCard(
                imageUrl = imageUrl,
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListContent(
    imageList: List<String>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        // modifier = Modifier.wrapContentWidth()
        modifier = Modifier.size(Dp(150.0f))
    ) {
        items(imageList) { imageUrl ->
            ImageCard(
                imageUrl = imageUrl,
                modifier = modifier
            )
        }
    }
}

@Composable
fun ImageCard(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = Modifier,
        model = imageUrl,
        contentDescription = "",
        placeholder = painterResource(R.drawable.ic_launcher_foreground)
    )
}

@Preview(showBackground = true)
@Composable
fun PagingImageCardPreview() {
    JinnyComposeTheme {
        ImageCard(imageUrl = "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/B.jpg?83000000")
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun MainHomePreview() {
    JinnyComposeTheme {
        MainHome()
    }
}
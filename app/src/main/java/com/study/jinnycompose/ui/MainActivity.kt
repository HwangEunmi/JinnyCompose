@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)

package com.study.jinnycompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.study.jinnycompose.model.HomeListItem
import com.study.jinnycompose.ui.component.BottomButtonLayout
import com.study.jinnycompose.ui.component.CustomAppBar
import com.study.jinnycompose.ui.data.ScrollDirection
import com.study.jinnycompose.ui.data.ScrollState
import com.study.jinnycompose.ui.theme.JinnyComposeTheme
import com.study.jinnycompose.utils.getScrollDirection
import com.study.jinnycompose.utils.getScrollStateAfterStop
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.math.min

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
            .background(Color.White)
            .padding(
                bottom = WindowInsets.navigationBars
                    .asPaddingValues()
                    .calculateBottomPadding()
            ) // 네비게이션 위로 그려지기
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
            // .verticalScroll(scrollState)
        ) {
            items(viewModel.homeList) { item ->
                when (item) {
                    is HomeListItem.PagerItem ->
                        TabPagerContent(
                            item = item
                        )

                    is HomeListItem.InfoItem ->
                        InfoContent()

                    is HomeListItem.RequestReservationDateItem ->
                        RequestReservationDateContent()

                    is HomeListItem.BlankItem ->
                        BlankContent()
                }
            }
        }

        MainBottomLayout(
            scrollState = scrollState
        )

        MainAppBar(
            scrollState = scrollState
        )
    }
}

@Composable
fun MainAppBar(
    scrollState: LazyListState
) {
    // 현재 LazyColumn의 MultiViewType으로 구성했기 때문에
    // 각 아이템의 높이를 구해서 '현재까지의 아이템 높이 / 전체 스크롤의 높이'로 구하려면
    // 각 항목의 아이템 높이를 구할때부터 코드가 꽤 복잡해짐 (visibleItemsInfo에서 first, last 항목만 가지고 올 수 있어서 Iterator 돌려야될 듯)
    // 그래서 Index의 비율로 계산
    // -> 단 아이템의 height가 길고 Index가 몇개 안되는 경우, 제대로 동작하기 힘듬
    // -> 다시 수정 필요
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

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomAppBar(
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
            onShareIconClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
fun MainBottomLayout(
    scrollState: LazyListState
) {
    val isScrollUp = scrollState.getScrollDirection() == ScrollDirection.Up
    val isScrollStop = scrollState.getScrollStateAfterStop() == ScrollState.Stop

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = isScrollUp || isScrollStop,
            enter = slideInVertically { height ->
                height
            },
            exit = slideOutVertically { height ->
                height
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            BottomButtonLayout(
                layoutHeight = 100.dp,
                onButtonClick = {},
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainHomePreview() {
    JinnyComposeTheme {
        MainHome()
    }
}
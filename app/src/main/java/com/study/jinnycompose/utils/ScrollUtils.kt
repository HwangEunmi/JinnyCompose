package com.study.jinnycompose.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.study.jinnycompose.ui.data.ScrollDirection
import com.study.jinnycompose.ui.data.ScrollState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

// TODO(jinny): by remember vs by remember(key)의 차이점
// TODO(jinny): derivedStateOf vs snapshotFlow
// TODO(jinny): offset에 대해 정확히 알아보기 (firstVisibleItemOffset이 계속 쓰이는 이유)
// TODO(jinny): LaunchedEffect vs rememberCoroutineScope의 사용 차이점
// TODO(jinny): Spacer vs Divider
@ExperimentalCoroutinesApi
@Composable
fun LazyListState.getScrollDirection(): ScrollDirection {
    var scrollDirection by remember {
        mutableStateOf(ScrollDirection.None)
    }
    var previousOffset by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(this) {
        snapshotFlow { firstVisibleItemScrollOffset }
            .distinctUntilChanged()
            .flatMapLatest { offset ->
                when {
                    offset > previousOffset -> flowOf(ScrollDirection.Down)
                    offset < previousOffset -> flowOf(ScrollDirection.Up)
                    else -> flowOf(ScrollDirection.None)
                }.also {
                    previousOffset = offset
                }
            }
            .collectLatest { direction ->
                scrollDirection = direction
            }
    }
    return scrollDirection
}

@ExperimentalCoroutinesApi
@Composable
fun LazyListState.getScrollStateAfterStop(): ScrollState {
    var scrollState by remember {
        mutableStateOf(ScrollState.Stop)
    }

    LaunchedEffect(this) {
        snapshotFlow { isScrollInProgress }
            .distinctUntilChanged()
            .flatMapLatest { isScrolling ->
                when (isScrolling) {
                    true -> {
                        flowOf(ScrollState.Scrolling)
                    }
                    false -> {
                        delay(500)
                        flowOf(ScrollState.Stop)
                    }
                }
            }.collectLatest { state ->
                scrollState = state
            }
    }
    return scrollState
}
package com.study.jinnycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.study.jinnycompose.model.ImageModel
import com.study.jinnycompose.ui.theme.JinnyComposeTheme

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainHome(
    viewModel: MainViewModel = viewModel()
) {
    val pagerState = rememberPagerState {
        viewModel.imageList.size
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PagingContent(
            pagerState = pagerState,
            imageList = viewModel.imageList,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
        )
        ListContent(
            imageList = viewModel.imageList,
            modifier = Modifier
                .wrapContentWidth()
                .background(Color.Blue)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagingContent(
    pagerState: PagerState,
    imageList: List<ImageModel>,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState
    ) {
        Column(
            modifier = modifier
        ) {
            val imageUrl = imageList[pagerState.currentPage].imageUrl
            ImageCard(
                imageUrl = imageUrl,
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListContent(
    imageList: List<ImageModel>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(imageList) { image ->
            val imageUrl = image.imageUrl
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
        modifier = modifier,
        model = imageUrl,
        contentDescription = "",
        placeholder = painterResource(R.drawable.ic_launcher_foreground)
    )
//    var uiState by remember { mutableStateOf(UiState.Loading) }
//    val painter = rememberAsyncImagePainter(
//        model = imageUrl,
//        onState = { state ->
//            Log.d("THEEND", "uiState: $state")
//            // uiState = fromAsyncImagePainterState(state)
//        }
//    )
//    Image(
//            painter = when (uiState) {
//                UiState.Loading -> painterResource(R.drawable.ic_launcher_foreground)
//                UiState.Success -> painter
//                UiState.Error -> painterResource(R.drawable.ic_launcher_background)
//            },
//        contentDescription = null,
//        modifier = modifier
//    )
}

@Preview(showBackground = true)
@Composable
fun PagingImageCardPreview() {
    JinnyComposeTheme {
        ImageCard(imageUrl = "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/B.jpg?83000000")
    }
}

// TODO : 왜 안나오는지 확인 필요
@Preview(showBackground = true)
@Composable
fun MainHomePreview() {
    JinnyComposeTheme {
        MainHome()
    }
}
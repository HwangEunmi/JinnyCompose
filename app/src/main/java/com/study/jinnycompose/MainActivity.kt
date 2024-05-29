package com.study.jinnycompose

import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.study.jinnycompose.ui.theme.JinnyComposeTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.study.jinnycompose.model.ImageModel

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
        state = pagerState,
        flingBehavior = PagerDefaults.flingBehavior(
            state = pagerState,
            pagerSnapDistance = PagerSnapDistance.atMost(0)
        ) // TODO : 개선 필요
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
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val context = LocalContext.current

    LaunchedEffect(imageUrl) {
        Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    imageBitmap = resource.asImageBitmap()
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    imageBitmap = null
                }
            })
    }

    imageBitmap?.let {
        Column(
            modifier = modifier
        ) {
            Image(bitmap = it, contentDescription = null, modifier = modifier)
        }
    }
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
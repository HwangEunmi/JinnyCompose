package com.study.jinnycompose.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.study.jinnycompose.R
import com.study.jinnycompose.ui.theme.JinnyComposeTheme

@Composable
fun ImageCard(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier,
        model = imageUrl,
        contentScale = ContentScale.Crop,
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
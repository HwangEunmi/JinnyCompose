package com.study.jinnycompose

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.study.jinnycompose.model.HomeListItem

class MainViewModel : ViewModel() {
    private val _homeList = makeHomeList().toMutableStateList()
    val homeList: List<HomeListItem>
        get() = _homeList

    private fun makeHomeList() =
        listOf(
            HomeListItem.TopPagingContent(
                imageList = makeImageList()
            ),
            HomeListItem.TopPagingContent(
                imageList = makeImageList()
            ),
            HomeListItem.TopPagingContent(
                imageList = makeImageList()
            ),
            HomeListItem.TopPagingSmallContent(
                imageList = makeImageList()
            ),
            HomeListItem.TopPagingSmallContent(
                imageList = makeImageList()
            ),
            HomeListItem.TopPagingContent(
                imageList = makeImageList()
            ),
            HomeListItem.TopPagingContent(
                imageList = makeImageList()
            ),
            HomeListItem.TopPagingContent(
                imageList = makeImageList()
            )
            // HomeListItem.Info
        )

    private fun makeImageList() =
        listOf(
            "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/B.jpg?83000000",
            "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/A3.jpg?337000000",
            "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/A1.jpg?196000000",
            "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/A2.jpg?269000000"
        )
}
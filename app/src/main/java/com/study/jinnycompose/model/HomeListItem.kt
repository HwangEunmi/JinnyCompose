package com.study.jinnycompose.model

sealed class HomeListItem {
    data class TopPagingContent(
        val imageList: List<String>
    ) : HomeListItem()

    data class TopPagingSmallContent(
        val imageList: List<String>
    ) : HomeListItem()

    data object Info : HomeListItem()
}
package com.study.jinnycompose.model

sealed class HomeListItem {
    data class PagerItem(
        val imageList: List<String>
    ) : HomeListItem()

    data object InfoItem : HomeListItem()

    data object RequestReservationDateItem : HomeListItem()

    data object BlankItem : HomeListItem()
}
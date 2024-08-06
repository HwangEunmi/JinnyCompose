package com.study.jinnycompose.ui

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.study.jinnycompose.model.BottomSheetListItem
import com.study.jinnycompose.model.HomeListItem

class MainViewModel : ViewModel() {
    private val _homeList = makeHomeList().toMutableStateList()
    val homeList: List<HomeListItem>
        get() = _homeList

    private val _bottomSheetList = makeBottomSheetList().toMutableStateList()
    val bottomSheetList: List<BottomSheetListItem>
        get() = _bottomSheetList

    private fun makeHomeList() =
        listOf(
            HomeListItem.PagerItem(
                imageList = makeImageList()
            ),
            HomeListItem.InfoItem,
            HomeListItem.RequestReservationDateItem,
            HomeListItem.BlankItem,
            HomeListItem.BlankItem,
            HomeListItem.BlankItem,
            HomeListItem.BlankItem,
            HomeListItem.BlankItem,
            HomeListItem.BlankItem
        )

    private fun makeBottomSheetList() =
        listOf(
            BottomSheetListItem(
                bankingName = "KB국민은행",
                amount = "100,000,000원"
            ),
            BottomSheetListItem(
                bankingName = "광주은행",
                amount = "200,000,000원"
            ),
            BottomSheetListItem(
                bankingName = "신한은행",
                amount = "300,000,000원"
            ),
            BottomSheetListItem(
                bankingName = "우리은행",
                amount = "100,000,000원"
            ),
            BottomSheetListItem(
                bankingName = "KB국민은행",
                amount = "100,000,000원"
            ),
            BottomSheetListItem(
                bankingName = "광주은행",
                amount = "200,000,000원"
            ),
            BottomSheetListItem(
                bankingName = "신한은행",
                amount = "300,000,000원"
            ),
            BottomSheetListItem(
                bankingName = "우리은행",
                amount = "100,000,000원"
            ),
            BottomSheetListItem(
                bankingName = "신한은행",
                amount = "300,000,000원"
            ),
            BottomSheetListItem(
                bankingName = "우리은행",
                amount = "100,000,000원"
            ),
            BottomSheetListItem(
                bankingName = "KB국민은행",
                amount = "100,000,000원"
            ),
            BottomSheetListItem(
                bankingName = "광주은행",
                amount = "200,000,000원"
            ),
            BottomSheetListItem(
                bankingName = "신한은행",
                amount = "300,000,000원"
            ),
            BottomSheetListItem(
                bankingName = "우리은행",
                amount = "100,000,000원"
            )
        )

    private fun makeImageList() =
        listOf(
            "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/B.jpg?83000000",
            "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/A3.jpg?337000000",
            "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/A1.jpg?196000000",
            "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/A2.jpg?269000000",
            "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/B.jpg?83000000",
            "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/A3.jpg?337000000",
            "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/A1.jpg?196000000",
            "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/A2.jpg?269000000"
        )
}
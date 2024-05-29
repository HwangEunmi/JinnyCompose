package com.study.jinnycompose

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.study.jinnycompose.model.ImageModel

class MainViewModel : ViewModel() {

    private val _imageList = makeImageList().toMutableStateList()
    val imageList: List<ImageModel>
        get() = _imageList

    private fun makeImageList() =
        listOf(
            ImageModel("https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/B.jpg?83000000"),
            ImageModel("https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/A3.jpg?337000000"),
            ImageModel("https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/A1.jpg?196000000"),
            ImageModel("https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6856853836/A2.jpg?269000000")
        )
}
package com.study.jinnycompose.ui

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.study.jinnycompose.ui.component.ImageCard

// TODO(jinny): selectable vs clickable
@Composable
fun ListContent(
    imageList: List<String>,
    onClickEvent: (Int) -> Unit,
    onSelectedEvent: () -> Boolean,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = Modifier.wrapContentWidth()
    ) {
        itemsIndexed(imageList) { index, imageUrl ->
            // val isSelected = (selectedIndex == index)
            ImageCard(
                imageUrl = imageUrl,
                modifier = modifier
                    .selectable(
                        selected = onSelectedEvent(),
                        onClick = {
                            onClickEvent(index)
                        }
                    )
            )
        }
    }
}
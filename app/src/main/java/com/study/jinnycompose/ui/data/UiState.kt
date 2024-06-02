package com.study.jinnycompose.ui.data

import coil.compose.AsyncImagePainter

enum class UiState {
    Loading,
    Success,
    Error;

    companion object {
        fun fromAsyncImagePainterState(state: AsyncImagePainter.State): UiState {
            return when (state) {
                is AsyncImagePainter.State.Success -> Success
                is AsyncImagePainter.State.Error -> Error
                is AsyncImagePainter.State.Loading -> Loading
                else -> Loading
            }
        }
    }
}
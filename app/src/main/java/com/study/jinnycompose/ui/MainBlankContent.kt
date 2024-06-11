package com.study.jinnycompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BlankContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "스크롤 영역",
            modifier = Modifier
                .background(
                    Color(0xFFFFFFCC)
                )
                .fillMaxWidth()
                .height(200.dp)
        )
    }
}
package com.study.jinnycompose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.study.jinnycompose.ui.theme.JinnyComposeTheme

@Composable
fun RequestReservationDateContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                vertical = 10.dp,
                horizontal = 10.dp
            )
    ) {
        Text(
            text = "예약 날짜 문의하기",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
        Text(
            text = "희망하는 날짜와 시간을 요청해보세요.",
            modifier = Modifier
                .padding(vertical = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RequestReservationDateContentPreview() {
    JinnyComposeTheme {
        RequestReservationDateContent()
    }
}
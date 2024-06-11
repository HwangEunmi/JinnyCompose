package com.study.jinnycompose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.study.jinnycompose.ui.theme.JinnyComposeTheme

@Composable
fun InfoContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                vertical = 10.dp,
                horizontal = 10.dp
            )
    ) {
        Row {
            Text(
                text = "TOP 30",
                style = TextStyle(
                    color = Color.Red,
                    fontSize = 24.sp
                )
            )
            Text(
                text = "원데이",
                modifier = Modifier
                    .padding(start = 5.dp)
            )
        }

        Text(
            text = "[망원동]다양한 베이킹 원데이 클래스 모음전",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(
                top = 5.dp
            )
        )

        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .padding(
                    vertical = 15.dp
                )
        )

        Text(
            text = "마포구 망원동"
        )
        Text(
            text = "주차 불가",
            modifier = Modifier.padding(
                top = 10.dp
            )
        )
        Text(
            text = "총 1시간 30분",
            modifier = Modifier.padding(
                top = 10.dp
            )
        )
        Text(
            text = "최대 수용 가능 인원 4명",
            modifier = Modifier.padding(
                top = 10.dp
            )
        )

        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .padding(
                    top = 15.dp
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InfoContentPreview() {
    JinnyComposeTheme {
        InfoContent()
    }
}
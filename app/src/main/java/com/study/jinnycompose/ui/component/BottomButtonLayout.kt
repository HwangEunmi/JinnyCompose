package com.study.jinnycompose.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.study.jinnycompose.ui.theme.JinnyComposeTheme

@Composable
fun BottomButtonLayout(
    layoutHeight: Dp,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(layoutHeight)
            .background(Color(0xFFFAEBD7))
    ) {
        Text(
            text = "33,000원",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 20.dp)
        )

        Surface(
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .padding(5.dp)
                .wrapContentWidth()
        ) {
            Text(
                text = "신청하기",
                modifier = Modifier
                    .padding(horizontal = 0.dp, vertical = 8.dp) // 원하는 패딩을 설정하세요
            )
        }

        Button(
            modifier = Modifier
                .wrapContentWidth()
                .padding(top = 50.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF8E7F)
            ),
            onClick = { onButtonClick() }
        ) {
            Text(
                text = "신청하기"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomButtonLayoutPreview() {
    JinnyComposeTheme {
        BottomButtonLayout(
            layoutHeight = 100.dp,
            onButtonClick = { })
    }
}
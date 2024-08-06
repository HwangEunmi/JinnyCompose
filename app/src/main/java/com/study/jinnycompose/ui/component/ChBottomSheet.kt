@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class
)

package com.study.jinnycompose.ui.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.study.jinnycompose.model.BottomSheetListItem
import com.study.jinnycompose.ui.theme.JinnyComposeTheme


@Composable
fun ModalBottomSheetContent(
    bottomSheetList: List<BottomSheetListItem>,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    var bottomSheetHeight by remember {
        mutableIntStateOf(0)
    }

    var dragHandleHeight by remember {
        mutableIntStateOf(0)
    }

    ModalBottomSheet(
        modifier = Modifier
            .onGloballyPositioned {
                bottomSheetHeight = it.size.height
            },
        windowInsets = WindowInsets(0, 0, 0, 0),
        sheetState = bottomSheetState,
        dragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(Color.White)
                    .onGloballyPositioned {
                        dragHandleHeight = it.size.height
                    }
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    painter = painterResource(id = android.R.drawable.bottom_bar),
                    contentDescription = ""
                )
            }
        },
        onDismissRequest = { onCancelClick() }
    ) {
        BottomSheetContent(
            bottomSheetState = bottomSheetState,
            bottomSheetList = bottomSheetList,
            onConfirmClick = onConfirmClick,
            dragHandleHeight = dragHandleHeight,
            bottomSheetHeight = bottomSheetHeight
        )
    }
}

@Composable
fun ChBottomSheet(
    title: String,
    bottomSheetList: List<BottomSheetListItem>,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    ModalBottomSheetContent(
        bottomSheetList = bottomSheetList,
        onConfirmClick = onConfirmClick,
        onCancelClick = onCancelClick
    )
}

@Composable
fun BottomSheetContent(
    bottomSheetState: SheetState,
    bottomSheetList: List<BottomSheetListItem>,
    onConfirmClick: () -> Unit,
    dragHandleHeight: Int,
    bottomSheetHeight: Int
) {
    // *는 스프레드 연산자로써 배열을 리스트로 변환할때 필요
    val checkedState = remember {
        mutableStateListOf(*Array(bottomSheetList.size) { false })
    }
    val checkedCount = checkedState.count { it == true }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(bottom = 48.dp) // 하단에 있는 Button의 높이만큼
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White)
        ) {
            itemsIndexed(bottomSheetList) { index, item ->
                ContentListItem(
                    bankingName = item.bankingName,
                    amount = item.amount,
                    checkedState = checkedState[index],
                    onCheckedChange = { isChecked ->
                        checkedState[index] = isChecked
                    }
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height) {
                        Log.d("THEEND", "height: ${placeable.height.toDp()}")
                        val bottomSheetHeightOffset = bottomSheetState
                            .requireOffset()
                        // 현재 BottomSheet의 상단에 offset을 준 상태 (BottomSheetHeight * 0.9f)
                        // 하지만 bottomSheetState의 requireOffset에는 BottomSheetHeight의 높이가 줄어도 반영이 안되기 때문에
                        // 상단에 offset을 준 만큼 보정해주기
                        val bottomSheetTopOffSet = bottomSheetHeight * 0.1f

                        // BottomSheet가 Expanded상태일때는 offset이 0, Hidden상태일때는 offset이 BottomSheet의 Height와 동일
                        // BottomSheet가 Expanded상태일때 버튼이 바닥에 붙어야하니까 BottomSheet의 Height - offset을 하면 되지 않을까
                        // BottomSheet의 시작점은 하단 네비게이션의 bottom
                        // 즉 버튼의 높이만큼 빼주면 버튼이 보이게 됨
                        val buttonLocation =
                            bottomSheetHeight - bottomSheetHeightOffset + bottomSheetTopOffSet - placeable.height
                        placeable.placeRelative(
                            0,
                            (buttonLocation - dragHandleHeight).toInt()
                        )
                    }
                },
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF8E7F)
            ),
            onClick = { onConfirmClick() }
        ) {
            Text(
                text = "선택 ($checkedCount)"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    JinnyComposeTheme {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF8E7F)
            ),
            onClick = { }
        ) {
            Text(
                text = "선택"
            )
        }
    }
}

@Composable
fun ContentListItem(
    bankingName: String,
    amount: String,
    checkedState: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
    ) {
        Text(
            text = bankingName,
            style = TextStyle(
                color = Color.Red,
                fontSize = 50.sp
            )
        )
        Text(
            text = amount,
            modifier = Modifier
                .padding(start = 5.dp)
        )
        Checkbox(
            checked = checkedState,
            onCheckedChange = { onCheckedChange(it) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChBottomSheetPreview() {
    JinnyComposeTheme {
//        ChBottomSheet(
//            title = "상환가능 대출",
//            bottomSheetList = arrayListOf(
//                BottomSheetListItem(
//                    "KB국민은행",
//                    "100,000,000원"
//                )
//            ),
//            onCancelClick = { },
//            onConfirmClick = { })
    }
}

@Preview(showBackground = true)
@Composable
fun ContentListItemPreview() {
    JinnyComposeTheme {
//        ContentListItem(
//            bankingName = "KB국민은행",
//            amount = "100,000,000원"
//        )
    }
}
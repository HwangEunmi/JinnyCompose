@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.study.jinnycompose.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomAppBar(
    title: String?,
    appBarColor: @Composable () -> TopAppBarColors,
    onNavigationIconClick: () -> Unit,
    onLikeIconClick: () -> Unit,
    onShareIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            title?.let { Text(title) }
        },
        colors = appBarColor(),
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "keyboardArrowLeft Button"
                )
            }
        },
        actions = {
            IconButton(onClick = onLikeIconClick) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "like Button"
                )
            }
            IconButton(onClick = onShareIconClick) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "share button"
                )
            }
        },
        modifier = modifier
    )
}
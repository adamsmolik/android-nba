package com.adamsmolik.nba.core.base.composable

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun Spacer(size: Dp) {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(size))
}

@Composable
fun HorizontalSpacer(size: Dp) {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(size))
}

@Composable
fun VerticalSpacer(size: Dp) {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(size))
}

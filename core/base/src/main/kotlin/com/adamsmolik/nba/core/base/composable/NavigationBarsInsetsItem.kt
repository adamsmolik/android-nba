package com.adamsmolik.nba.core.base.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NavigationBarsInsetsItem() {
    Spacer(modifier = Modifier.navigationBarsPadding())
}

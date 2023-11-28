package com.adamsmolik.nba.core.base.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.adamsmolik.nba.core.base.theme.NBATheme

@Composable
inline fun DefaultPreview(
    crossinline content: @Composable () -> Unit,
) =
    NBATheme {
        CompositionLocalProvider(
            LocalContentColor provides NBATheme.colors.onSurface.primary,
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .background(Color.LightGray),
            ) {
                content()
            }
        }
    }

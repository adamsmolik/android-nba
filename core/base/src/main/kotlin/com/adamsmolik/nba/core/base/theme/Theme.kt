package com.adamsmolik.nba.core.base.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun NBATheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: NBATypography = Typography,
    content: @Composable () -> Unit,
) {
    val colors = remember(darkTheme) {
        if (darkTheme) {
            DarkThemeColors
        } else {
            LightThemeColors
        }
    }

    val systemUiController = rememberSystemUiController()
    LaunchedEffect(systemUiController, darkTheme) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !darkTheme,
        )
    }

    MaterialTheme(
        colorScheme = colors.toMaterialColors(),
        typography = typography.toMaterialTypography(),
    ) {
        CompositionLocalProvider(
            LocalColorScheme provides colors,
            LocalRippleTheme provides BaseRippleTheme,
            LocalTypography provides typography,
        ) {
            content()
        }
    }
}

object NBATheme {

    val colors: NBAColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

    val typography: NBATypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}

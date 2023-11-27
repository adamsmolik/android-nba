package com.adamsmolik.nba.base.theme

import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun NBATheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: NBATypography = Typography,
    content: @Composable () -> Unit,
) {
//    val colors = if (darkTheme) {
//        DarkThemeColors
//    } else {
//        LightThemeColors
//    }

    val colors = LightThemeColors

    MaterialTheme(
        colorScheme = colors.toMaterialColors(),
        typography = typography.toMaterialTypography(),
    ) {
        CompositionLocalProvider(
            LocalColorScheme provides colors,
            LocalRippleTheme provides DarkRippleTheme,
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

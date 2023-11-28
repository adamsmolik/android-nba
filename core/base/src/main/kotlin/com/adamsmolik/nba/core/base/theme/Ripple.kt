package com.adamsmolik.nba.core.base.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
object LightRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = NBATheme.colors.surface.primary

    @Composable
    override fun rippleAlpha() = alpha()
}

@Immutable
object DarkRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = NBATheme.colors.surface.inverse

    @Composable
    override fun rippleAlpha() = alpha()
}

private fun alpha() = RippleAlpha(
    pressedAlpha = 0.2f,
    focusedAlpha = 0.2f,
    draggedAlpha = 0.24f,
    hoveredAlpha = 0.1f,
)

package com.adamsmolik.nba.core.ui.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
object InverseRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = NBATheme.colors.surface.primary

    @Composable
    override fun rippleAlpha() = alpha()
}

@Immutable
object BaseRippleTheme : RippleTheme {
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

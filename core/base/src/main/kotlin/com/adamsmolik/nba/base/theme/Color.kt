package com.adamsmolik.nba.base.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Base
val Unspecified = Color.Unspecified
val Transparent = Color(0x00000000)

// val Black = Color(0xFF000000) TODO
val White = Color(0xFFFFFFFF)

// Grey
val Grey7 = Color(0xFF1A1C1E)
val Grey6 = Color(0xFF25262B)
val Grey5 = Color(0xFF495057)
val Grey4 = Color(0xFF909296)
val Grey3 = Color(0xFFC1C2C5)
val Grey2 = Color(0xFFD1D1D1)
val Grey1 = Color(0xFFF1F3F5)

// Brand
val Blue3 = Color(0xFF385BA9)
val Blue2 = Color(0xFFB1C6FF)
val Blue1 = Color(0xFFD9E2FF)
val Orange2 = Color(0xFFEB612A)
val Orange1 = Color(0xFFF5B094)
val Red1 = Color(0xFFC8102E)

@Immutable
data class NBAColors(
    val base: BaseColors = BaseColors(),
    val surface: SurfaceColors = SurfaceColors(),
    val onSurface: OnSurfaceColors = OnSurfaceColors(),
    val brand: BrandColors = BrandColors(),
    val light: Boolean,
)

@Immutable
data class BaseColors(
    val unspecified: Color = Unspecified,
    val transparent: Color = Transparent,
    val error: Color = Red1,
)

@Immutable
data class SurfaceColors(
    val primary: Color = Grey1,
    val secondary: Color = White,
    val tertiary: Color = Grey2,
    val inverse: Color = Grey7,
    val inverseSecondary: Color = Grey6,
    val disabled: Color = Grey3,
    val primarySemiTransparent: Color = Grey1.copy(alpha = 0.7f),
    val inverseSemiTransparent: Color = Grey7.copy(alpha = 0.7f),
)

@Immutable
data class OnSurfaceColors(
    val primary: Color = Grey7,
    val secondary: Color = Grey4,
    val tertiary: Color = Grey5,
    val quaternary: Color = Grey2,
    val inverse: Color = White,
    val disabled: Color = Grey3,
)

@Immutable
data class BrandColors(
    val blue1: Color = Blue1,
    val blue2: Color = Blue2,
    val blue3: Color = Blue3,
    val orange1: Color = Orange1,
    val orange2: Color = Orange2,
)

internal val LightThemeColors = NBAColors(light = true)

internal fun NBAColors.toMaterialColors(): ColorScheme = ColorScheme(
    primary = brand.blue3,
    onPrimary = onSurface.inverse,
    primaryContainer = brand.blue1,
    onPrimaryContainer = onSurface.primary,
    inversePrimary = brand.blue2,
    secondary = brand.blue2,
    onSecondary = onSurface.inverse,
    secondaryContainer = brand.blue1,
    onSecondaryContainer = onSurface.primary,
    tertiary = brand.orange2,
    onTertiary = onSurface.inverse,
    tertiaryContainer = brand.orange1,
    onTertiaryContainer = onSurface.primary,
    background = surface.primary,
    onBackground = onSurface.primary,
    surface = surface.primary,
    onSurface = onSurface.primary,
    surfaceVariant = surface.secondary,
    onSurfaceVariant = onSurface.primary,
    surfaceTint = surface.primary,
    inverseSurface = surface.inverse,
    inverseOnSurface = onSurface.inverse,
    error = base.error,
    onError = onSurface.inverse,
    errorContainer = base.error,
    onErrorContainer = onSurface.inverse,
    outline = onSurface.secondary,
    outlineVariant = onSurface.quaternary,
    scrim = onSurface.primary,
)

internal val LocalColorScheme = staticCompositionLocalOf { LightThemeColors }

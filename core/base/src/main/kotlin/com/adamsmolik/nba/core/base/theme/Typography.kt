package com.adamsmolik.nba.core.base.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val headlineLarge = TextStyle(
    fontWeight = FontWeight.Black,
    fontSize = 24.sp,
    lineHeight = 36.sp,
)

val headlineMedium = TextStyle(
    fontWeight = FontWeight.Black,
    fontSize = 20.sp,
    lineHeight = 30.sp,
)

val headlineSmall = TextStyle(
    fontWeight = FontWeight.Black,
    fontSize = 16.sp,
    lineHeight = 26.sp,
)

val titleLarge = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 22.sp,
)

val titleMedium = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 20.sp,
)

val titleSmall = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    lineHeight = 18.sp,
)

val labelLarge = TextStyle(
    fontWeight = FontWeight.Black,
    fontSize = 16.sp,
    lineHeight = 24.sp,
)

val labelMedium = TextStyle(
    fontWeight = FontWeight.Black,
    fontSize = 14.sp,
    lineHeight = 22.sp,
)

val labelSmall = TextStyle(
    fontWeight = FontWeight.Black,
    fontSize = 12.sp,
    lineHeight = 20.sp,
)

val bodyLarge = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 20.sp,
)

val bodyMedium = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 18.sp,
)

val bodySmall = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp,
    lineHeight = 16.sp,
)

@Immutable
data class NBATypography(
    val headline: Headline = Headline(),
    val title: Title = Title(),
    val body: Body = Body(),
    val label: Label = Label(),
)

@Immutable
data class Headline(
    val large: TextStyle = headlineLarge,
    val medium: TextStyle = headlineMedium,
    val small: TextStyle = headlineSmall,
)

@Immutable
data class Title(
    val large: TextStyle = titleLarge,
    val medium: TextStyle = titleMedium,
    val small: TextStyle = titleSmall,
)

@Immutable
data class Body(
    val large: TextStyle = bodyLarge,
    val medium: TextStyle = bodyMedium,
    val small: TextStyle = bodySmall,
)

@Immutable
data class Label(
    val large: TextStyle = labelLarge,
    val medium: TextStyle = labelMedium,
    val small: TextStyle = labelSmall,
)

internal val Typography = NBATypography()

internal fun NBATypography.toMaterialTypography() = Typography(
    headlineLarge = headline.large,
    headlineMedium = headline.medium,
    headlineSmall = headline.small,
    titleLarge = title.large,
    titleMedium = title.medium,
    titleSmall = title.small,
    labelLarge = label.large,
    labelMedium = label.medium,
    labelSmall = label.small,
    bodyLarge = body.large,
    bodyMedium = body.medium,
    bodySmall = body.small,
)

internal val LocalTypography = staticCompositionLocalOf { Typography }

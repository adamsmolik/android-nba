package com.adamsmolik.nba.core.base.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.adamsmolik.nba.core.base.theme.NBATheme

@Composable
fun DotDivider(
    color: Color,
    style: TextStyle,
    modifier: Modifier = Modifier,
) {
    Text(
        text = " · ",
        style = style,
        color = color,
        modifier = modifier,
    )
}

@Preview(name = "DotDivider", group = "Core Base")
@Composable
fun PreviewDotDivider() {
    DefaultPreview {
        DotDivider(
            style = NBATheme.typography.body.small,
            color = NBATheme.colors.onSurface.secondary,
        )
    }
}

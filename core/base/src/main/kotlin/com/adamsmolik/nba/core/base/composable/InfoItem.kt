package com.adamsmolik.nba.core.base.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.adamsmolik.nba.core.base.theme.NBATheme

@Composable
fun InfoItem(
    value: String?,
    label: String,
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(NBATheme.colors.surface.secondary)
            .padding(8.dp),
    ) {
        Text(
            text = value ?: "-",
            style = NBATheme.typography.headline.medium,
            color = NBATheme.colors.onSurface.primary,
        )

        VerticalSpacer(size = 2.dp)

        Text(
            text = label,
            style = NBATheme.typography.body.medium,
            color = NBATheme.colors.onSurface.secondary,
        )
    }
}

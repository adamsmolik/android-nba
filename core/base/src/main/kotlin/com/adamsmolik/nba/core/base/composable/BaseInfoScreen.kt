package com.adamsmolik.nba.core.base.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adamsmolik.nba.core.base.R
import com.adamsmolik.nba.core.base.composable.button.PrimaryButton
import com.adamsmolik.nba.core.base.extension.navigationInsets
import com.adamsmolik.nba.core.base.extension.verticalScroll
import com.adamsmolik.nba.core.base.theme.NBATheme
import com.adamsmolik.nba.core.base.util.TextRes
import com.adamsmolik.nba.core.base.util.asString

@Composable
fun BaseInfoScreen(
    @DrawableRes
    icon: Int,
    iconColor: Color,
    title: TextRes?,
    addNavInsets: Boolean,
    description: String? = null,
    contentColor: Color = NBATheme.colors.onSurface.primary,
    scrollState: ScrollState? = rememberScrollState(),
    @StringRes
    action: Int?,
    onAction: (() -> Unit)? = null,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
            .navigationInsets(addNavInsets),
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier
                .size(64.dp)
                .background(NBATheme.colors.onSurface.inverse, CircleShape)
                .padding(20.dp),
        )

        if (title != null) {
            Spacer(32.dp)

            Text(
                text = title.asString(),
                style = NBATheme.typography.headline.large,
                color = contentColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 32.dp),
            )
        }

        if (description != null) {
            Spacer(16.dp)

            Text(
                text = description,
                style = NBATheme.typography.body.large,
                color = contentColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 32.dp),
            )
        }

        if (action != null && onAction != null) {
            Spacer(32.dp)

            PrimaryButton(
                text = stringResource(action),
                onClick = onAction,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}

@Preview(name = "BaseInfoScreen", group = "Feature Authentication")
@Composable
fun PreviewBaseInfoScreen() {
    DefaultPreview {
        BaseInfoScreen(
            icon = R.drawable.ic_emoji,
            iconColor = NBATheme.colors.brand.blue2,
            title = TextRes.Regular(R.string.preview_title),
            description = stringResource(id = R.string.preview_message),
            action = R.string.preview_action,
            onAction = {},
            addNavInsets = false,
        )
    }
}

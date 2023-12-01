@file:OptIn(ExperimentalMaterial3Api::class)

package com.adamsmolik.nba.core.ui.composable

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.adamsmolik.nba.core.ui.R
import com.adamsmolik.nba.core.ui.theme.NBATheme

sealed class NavigationType {

    object None : NavigationType()

    sealed class Clickable : NavigationType() {
        abstract val icon: Int
        abstract val callback: () -> Unit
        abstract val contentDescriptionResId: Int

        data class Up(override val callback: () -> Unit) : Clickable() {
            override val icon = R.drawable.ic_back
            override val contentDescriptionResId = R.string.content_description_back
        }

        data class Close(override val callback: () -> Unit) : Clickable() {
            override val icon = R.drawable.ic_close
            override val contentDescriptionResId = R.string.content_description_close
        }
    }
}

@Composable
fun SmallAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationType: NavigationType = NavigationType.None,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
) {
    SmallAppBar(
        title = {
            AppBarTitle(
                text = title,
                style = NBATheme.typography.headline.large,
            )
        },
        modifier = modifier,
        navigationType = navigationType,
        windowInsets = windowInsets,
    )
}

@Composable
fun SmallAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationType: NavigationType = NavigationType.None,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
) {
    TopAppBar(
        navigationIcon = {
            NavigationIcon(navigationType = navigationType)
        },
        title = {
            title()
        },
        windowInsets = windowInsets,
        modifier = modifier,
    )
}

@Composable
private fun NavigationIcon(navigationType: NavigationType) {
    if (navigationType is NavigationType.Clickable) {
        IconButton(onClick = navigationType.callback) {
            Icon(
                painter = painterResource(navigationType.icon),
                contentDescription = stringResource(navigationType.contentDescriptionResId),
            )
        }
    }
}

@Composable
fun AppBarTitle(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = style,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
    )
}

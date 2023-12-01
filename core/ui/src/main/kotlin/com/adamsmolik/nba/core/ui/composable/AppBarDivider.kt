package com.adamsmolik.nba.core.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.adamsmolik.nba.core.ui.theme.NBATheme

@Composable
fun AppBarDivider(
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
) {
    val showDivider by remember {
        derivedStateOf {
            scrollState.value != 0
        }
    }

    AppBarDivider(
        visible = showDivider,
        modifier = modifier,
    )
}

@Composable
fun AppBarDivider(
    listState: LazyListState,
    modifier: Modifier = Modifier,
) {
    val showDivider by remember {
        derivedStateOf {
            (listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0).not()
        }
    }

    AppBarDivider(
        visible = showDivider,
        modifier = modifier,
    )
}

@Composable
fun AppBarDivider(
    gridState: LazyGridState,
    modifier: Modifier = Modifier,
) {
    val showDivider by remember {
        derivedStateOf {
            (gridState.firstVisibleItemIndex == 0 && gridState.firstVisibleItemScrollOffset == 0).not()
        }
    }

    AppBarDivider(
        visible = showDivider,
        modifier = modifier,
    )
}

@Composable
fun AppBarDivider(
    modifier: Modifier = Modifier,
    visible: Boolean = true,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            NBATheme.colors.onSurface.primary.copy(alpha = 0.12f),
                            NBATheme.colors.base.transparent,
                        ),
                    ),
                ),
        )
    }
}

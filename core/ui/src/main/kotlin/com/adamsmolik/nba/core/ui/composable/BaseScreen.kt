package com.adamsmolik.nba.core.ui.composable

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adamsmolik.nba.core.ui.arch.ScreenState
import com.adamsmolik.nba.core.ui.theme.NBATheme

sealed class AppBarDividerType {
    data class Scroll(val scrollState: ScrollState) : AppBarDividerType()
    data class Lazy(val listState: LazyListState) : AppBarDividerType()
    data class Grid(val gridState: LazyGridState) : AppBarDividerType()
}

@Composable
fun BaseScreen(
    appBar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    appBarDivider: AppBarDividerType? = null,
    snackbarHandler: SnackbarHandler? = null,
    containerColor: Color = NBATheme.colors.surface.primary,
    content: @Composable BoxScope.() -> Unit,
) {
    BaseScreenInternal(
        appBar = appBar,
        modifier = modifier,
        snackbarHandler = snackbarHandler,
        containerColor = containerColor,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            content()

            if (appBarDivider != null) {
                when (appBarDivider) {
                    is AppBarDividerType.Scroll -> AppBarDivider(scrollState = appBarDivider.scrollState)
                    is AppBarDividerType.Lazy -> AppBarDivider(listState = appBarDivider.listState)
                    is AppBarDividerType.Grid -> AppBarDivider(gridState = appBarDivider.gridState)
                }
            }
        }
    }
}

@Composable
fun BaseScreen(
    title: String,
    modifier: Modifier = Modifier,
    navigationType: NavigationType = NavigationType.None,
    appBarDivider: AppBarDividerType? = null,
    snackbarHandler: SnackbarHandler? = null,
    containerColor: Color = NBATheme.colors.surface.primary,
    content: @Composable BoxScope.() -> Unit,
) {
    BaseScreen(
        appBar = {
            BaseAppBar(
                title = title,
                navigationType = navigationType,
            )
        },
        modifier = modifier,
        appBarDivider = appBarDivider,
        snackbarHandler = snackbarHandler,
        containerColor = containerColor,
        content = content,
    )
}

@Composable
fun <T : Any> BaseStatefulScreen(
    appBar: @Composable () -> Unit,
    state: ScreenState<T>,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarPadding: PaddingValues? = null,
    appBarDivider: AppBarDividerType? = null,
    snackbarHandler: SnackbarHandler? = null,
    containerColor: Color = NBATheme.colors.surface.primary,
    progress: @Composable (BoxScope.() -> Unit)? = null,
    empty: @Composable (BoxScope.() -> Unit)? = null,
    content: @Composable BoxScope.(T) -> Unit,
) {
    BaseScreenInternal(
        appBar = appBar,
        modifier = modifier,
        snackbarHandler = snackbarHandler,
        containerColor = containerColor,
        snackbarPadding = snackbarPadding,
    ) { innerPadding ->
        StatefulScreen(
            innerPadding = innerPadding,
            state = state,
            onRetry = onRetry,
            appBarDivider = appBarDivider,
            progress = progress,
            empty = empty,
            content = content,
        )
    }
}

@Composable
fun <T : Any> BaseStatefulScreen(
    title: String,
    state: ScreenState<T>,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    navigationType: NavigationType = NavigationType.None,
    appBarDivider: AppBarDividerType? = null,
    snackbarHandler: SnackbarHandler? = null,
    containerColor: Color = NBATheme.colors.surface.primary,
    progress: @Composable (BoxScope.() -> Unit)? = null,
    empty: @Composable (BoxScope.() -> Unit)? = null,
    snackbarPadding: PaddingValues? = null,
    content: @Composable BoxScope.(T) -> Unit,
) {
    BaseStatefulScreen(
        appBar = {
            BaseAppBar(
                title = title,
                navigationType = navigationType,
            )
        },
        state = state,
        onRetry = onRetry,
        modifier = modifier,
        appBarDivider = appBarDivider,
        snackbarHandler = snackbarHandler,
        containerColor = containerColor,
        progress = progress,
        empty = empty,
        snackbarPadding = snackbarPadding,
        content = content,
    )
}

@Composable
private fun BaseAppBar(
    title: String,
    navigationType: NavigationType,
) {
    SmallAppBar(
        title = title,
        navigationType = navigationType,
    )
}

@Composable
private fun BaseScreenInternal(
    appBar: @Composable () -> Unit,
    modifier: Modifier,
    snackbarHandler: SnackbarHandler?,
    containerColor: Color = NBATheme.colors.surface.primary,
    snackbarPadding: PaddingValues? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            appBar()
        },
        snackbarHost = {
            if (snackbarHandler != null) {
                SnackbarHost(
                    snackbarHandler = snackbarHandler,
                    modifier = Modifier
                        .then(
                            if (snackbarPadding != null) {
                                Modifier.padding(snackbarPadding)
                            } else {
                                Modifier
                            },
                        )
                        .navigationBarsPadding()
                        .imePadding(),
                )
            }
        },
        contentWindowInsets = WindowInsets(0.dp),
        containerColor = containerColor,
        modifier = modifier,
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Preview(name = "BaseScreen", group = "Core UI")
@Composable
fun PreviewBaseScreen() {
    DefaultPreview {
        BaseScreen(
            title = "Title",
            navigationType = NavigationType.Clickable.Up {},
            content = {},
        )
    }
}

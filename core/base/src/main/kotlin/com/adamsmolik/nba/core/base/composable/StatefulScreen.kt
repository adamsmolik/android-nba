package com.adamsmolik.nba.core.base.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adamsmolik.nba.core.base.R
import com.adamsmolik.nba.core.base.arch.ScreenState
import com.adamsmolik.nba.core.base.error.AppError
import com.adamsmolik.nba.core.base.error.ErrorUiModel
import com.adamsmolik.nba.core.base.error.LocalErrorHandler
import com.adamsmolik.nba.core.base.extension.navigationInsets
import com.adamsmolik.nba.core.base.theme.NBATheme
import com.adamsmolik.nba.core.base.util.TextRes
import com.adamsmolik.nba.core.base.util.asString

@Composable
fun <T : Any> StatefulScreen(
    state: ScreenState<T>,
    modifier: Modifier = Modifier,
    defaultStatesNavigationInsets: Boolean = false,
    onRetry: (() -> Unit)? = null,
    progress: @Composable (BoxScope.() -> Unit)? = null,
    empty: @Composable (BoxScope.() -> Unit)? = null,
    offline: @Composable (BoxScope.() -> Unit)? = null,
    error: @Composable (BoxScope.(error: AppError, customMessage: TextRes?) -> Unit)? = null,
    content: @Composable BoxScope.(T) -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        when (state) {
            is ScreenState.Progress -> {
                if (progress != null) {
                    progress.invoke(this)
                } else {
                    ProgressState(
                        addNavInset = defaultStatesNavigationInsets,
                    )
                }
            }
            is ScreenState.Content -> {
                content(state.data)
            }
            is ScreenState.Empty -> {
                if (empty != null) {
                    empty.invoke(this)
                } else {
                    EmptyState(
                        emptyMessage = state.message?.asString(),
                        addNavInsets = defaultStatesNavigationInsets,
                    )
                }
            }
            is ScreenState.Error -> {
                if (error != null) {
                    error.invoke(this, state.error, state.message)
                } else {
                    val errorHandler = LocalErrorHandler.current
                    val error = errorHandler.getError(state.error)

                    ErrorState(
                        error = error,
                        customMessage = state.message,
                        addNavInsets = defaultStatesNavigationInsets,
                        onRetry = onRetry ?: {},
                    )
                }
            }
            is ScreenState.Offline -> {
                if (offline != null) {
                    offline.invoke(this)
                } else {
                    OfflineState(
                        addNavInsets = defaultStatesNavigationInsets,
                        onRetry = onRetry ?: {},
                    )
                }
            }
        }
    }
}

@Composable
fun <T : Any> StatefulScreen(
    state: ScreenState<T>,
    onRetry: () -> Unit,
    innerPadding: PaddingValues = PaddingValues(),
    appBarDivider: AppBarDividerType? = null,
    progress: @Composable (BoxScope.() -> Unit)? = null,
    empty: @Composable (BoxScope.() -> Unit)? = null,
    content: @Composable BoxScope.(T) -> Unit,
) {
    StatefulScreen(
        state = state,
        defaultStatesNavigationInsets = true,
        onRetry = onRetry,
        progress = progress,
        empty = empty,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) { data ->
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            content(data)

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
fun ProgressState(
    addNavInset: Boolean,
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .navigationInsets(addNavInset),
    ) {
        CircularProgressIndicator(
            color = NBATheme.colors.brand.blue3,
            modifier = Modifier
                .align(Alignment.Center),
        )
    }
}

@Composable
fun EmptyState(
    emptyMessage: String?,
    addNavInsets: Boolean,
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .navigationInsets(addNavInsets),
    ) {
        Text(
            text = emptyMessage ?: stringResource(R.string.screen_empty_title),
            style = NBATheme.typography.headline.large,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(48.dp),
        )
    }
}

@Composable
fun OfflineState(
    addNavInsets: Boolean,
    onRetry: () -> Unit,
) {
    RetryState(
        title = R.string.screen_offline_title,
        addNavInsets = addNavInsets,
        description = stringResource(R.string.screen_offline_description),
        onRetry = onRetry,
    )
}

@Composable
fun ErrorState(
    error: ErrorUiModel?,
    customMessage: TextRes?,
    addNavInsets: Boolean,
    onRetry: () -> Unit,
) {
    RetryState(
        title = R.string.screen_error_title,
        addNavInsets = addNavInsets,
        description = customMessage?.asString() ?: error?.message?.asString() ?: stringResource(R.string.screen_error_description),
        onRetry = onRetry,
    )
}

@Composable
private fun RetryState(
    @StringRes
    title: Int,
    description: String,
    onRetry: () -> Unit,
    addNavInsets: Boolean,
) {
    BaseInfoScreen(
        icon = R.drawable.ic_warning,
        iconColor = NBATheme.colors.brand.orange2,
        title = TextRes.Regular(title),
        description = description,
        action = R.string.screen_state_retry,
        onAction = onRetry,
        addNavInsets = addNavInsets,
    )
}

@Preview(name = "ProgressState", group = "Core Base")
@Composable
fun PreviewProgressState() {
    DefaultPreview {
        Box {
            ProgressState(addNavInset = false)
        }
    }
}

@Preview(name = "EmptyState", group = "Core Base")
@Composable
fun PreviewEmptyState() {
    DefaultPreview {
        Box {
            EmptyState(
                addNavInsets = false,
                emptyMessage = "Empty data",
            )
        }
    }
}

@Preview(name = "OfflineState", group = "Core Base")
@Composable
fun PreviewOfflineState() {
    DefaultPreview {
        OfflineState(
            addNavInsets = false,
            onRetry = {},
        )
    }
}

@Preview(name = "ErrorState", group = "Core Base")
@Composable
fun PreviewErrorState() {
    DefaultPreview {
        ErrorState(
            error = ErrorUiModel(
                message = TextRes.Formatted(R.string.string, "Error message"),
            ),
            customMessage = TextRes.Formatted(R.string.string, "Error message"),
            addNavInsets = false,
            onRetry = {},
        )
    }
}

@Preview(name = "RetryState", group = "Core Base")
@Composable
fun PreviewRetryState() {
    DefaultPreview {
        RetryState(
            title = R.string.screen_state_retry,
            addNavInsets = false,
            description = "Retry description",
            onRetry = {},
        )
    }
}

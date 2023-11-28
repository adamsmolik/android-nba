package com.adamsmolik.nba.core.base.paging

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adamsmolik.nba.core.base.composable.HorizontalItemProgress
import com.adamsmolik.nba.core.base.composable.NavigationBarsInsetsItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LazyColumnPaged(
    paging: PagingUiModel,
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    refreshEnabled: Boolean = true,
    onRefreshRequested: (() -> Unit)? = null,
    onRefreshStarted: (() -> Unit)? = null,
    navigationInsets: Boolean = false,
    content: LazyListScope.() -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = paging.refreshing,
        onRefresh = {
            onRefreshRequested?.invoke()
            paging.onRefresh()
            onRefreshStarted?.invoke()
        },
    )

    Box(
        modifier = containerModifier,
    ) {
        LazyColumn(
            state = state,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
            modifier = modifier
                .pullRefresh(
                    state = pullRefreshState,
                    enabled = refreshEnabled,
                ),
        ) {
            content()

            if (paging.loadingNextPage) {
                item { HorizontalItemProgress() }
            }

            if (navigationInsets) {
                item {
                    NavigationBarsInsetsItem()
                }
            }
        }

        if (refreshEnabled) {
            PullRefreshIndicator(
                refreshing = paging.refreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }

    PagingHandler(
        listState = state,
        onDistanceToEndChanged = paging.onDistanceToEndChanged,
    )
}

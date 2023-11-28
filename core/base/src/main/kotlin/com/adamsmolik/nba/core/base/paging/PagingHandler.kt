package com.adamsmolik.nba.core.base.paging

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun PagingHandler(
    listState: LazyListState,
    onDistanceToEndChanged: (distance: Int) -> Unit,
) {
    val pagingState = remember {
        derivedStateOf {
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf PagingState()

            PagingState(
                distanceToEnd = totalItemsCount - 1 - lastVisibleItem.index,
                totalItemsCount = totalItemsCount,
            )
        }
    }

    LaunchedEffect(pagingState) {
        snapshotFlow { pagingState.value }
            .distinctUntilChanged()
            .collect {
                onDistanceToEndChanged(it.distanceToEnd)
            }
    }
}

@Composable
fun PagingHandler(
    gridState: LazyGridState,
    onDistanceToEndChanged: (distance: Int) -> Unit,
) {
    val pagingState = remember {
        derivedStateOf {
            val totalItemsCount = gridState.layoutInfo.totalItemsCount
            val lastVisibleItem = gridState.layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf PagingState()

            PagingState(
                distanceToEnd = totalItemsCount - 1 - lastVisibleItem.index,
                totalItemsCount = totalItemsCount,
            )
        }
    }

    LaunchedEffect(pagingState) {
        snapshotFlow { pagingState.value }
            .distinctUntilChanged()
            .collect {
                onDistanceToEndChanged(it.distanceToEnd)
            }
    }
}

private data class PagingState(
    val distanceToEnd: Int = -1,
    val totalItemsCount: Int = -1,
)

package com.adamsmolik.nba.core.base.paging

import androidx.compose.runtime.Immutable

@Immutable
data class PagingUiModel(
    val loadingNextPage: Boolean,
    val refreshing: Boolean,
    val onDistanceToEndChanged: (distance: Int) -> Unit,
    val onRefresh: () -> Unit,
) {
    companion object
}

fun PagedState<*>.toUiModel() = PagingUiModel(
    loadingNextPage = isLoadingNextPage(),
    refreshing = isRefreshing(),
    onDistanceToEndChanged = onDistanceToEndChanged,
    onRefresh = onRefresh,
)

fun PagingUiModel.Companion.preview() = PagingUiModel(
    loadingNextPage = false,
    refreshing = false,
    onDistanceToEndChanged = {},
    onRefresh = {},
)

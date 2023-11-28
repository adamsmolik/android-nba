package com.adamsmolik.nba.core.base.paging

import com.adamsmolik.nba.core.base.arch.ScreenState
import com.adamsmolik.nba.core.base.error.AppError
import com.adamsmolik.nba.core.base.error.toScreenState

sealed class PagedState<T> {
    abstract val data: List<T>
    abstract val firstPage: Boolean
    abstract val onDistanceToEndChanged: (distance: Int) -> Unit
    abstract val onRefresh: () -> Unit

    data class Progress<T>(
        val refreshing: Boolean,
        override val data: List<T>,
        override val firstPage: Boolean,
        override val onDistanceToEndChanged: (distance: Int) -> Unit,
        override val onRefresh: () -> Unit,
    ) : PagedState<T>()

    data class Success<T>(
        override val data: List<T>,
        override val firstPage: Boolean,
        override val onDistanceToEndChanged: (distance: Int) -> Unit,
        override val onRefresh: () -> Unit,
    ) : PagedState<T>()

    data class Error<T>(
        val error: AppError,
        override val data: List<T>,
        override val firstPage: Boolean,
        override val onDistanceToEndChanged: (distance: Int) -> Unit,
        override val onRefresh: () -> Unit,
    ) : PagedState<T>()

    fun isRefreshing() = this is Progress && refreshing

    fun isLoadingNextPage() = firstPage.not() && this is Progress

    fun copyWithData(data: List<T>): PagedState<T> = when (this) {
        is Progress<T> -> copy(data = data)
        is Success<T> -> copy(data = data)
        is Error<T> -> copy(data = data)
    }
}

fun <S, T : Any> PagedState<S>.toScreenState(
    emitEmptyAsContent: Boolean = false,
    content: (items: List<S>) -> T,
): ScreenState<T> = when (this) {
    is PagedState.Progress ->
        if (firstPage.not() || refreshing) ScreenState.Content(content(data)) else ScreenState.Progress
    is PagedState.Success ->
        if (data.isEmpty() && firstPage && emitEmptyAsContent.not()) {
            ScreenState.Empty()
        } else {
            ScreenState.Content(
                content(data),
            )
        }
    is PagedState.Error ->
        if (firstPage) {
            error.toScreenState()
        } else {
            ScreenState.Content(content(data))
        }
}

fun <S : Any> PagedState<S>.toScreenState(emitEmptyAsContent: Boolean = false): ScreenState<List<S>> = toScreenState(
    emitEmptyAsContent,
) { it }

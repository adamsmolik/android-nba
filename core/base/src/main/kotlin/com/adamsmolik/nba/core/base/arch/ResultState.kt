package com.adamsmolik.nba.core.base.arch

import com.adamsmolik.nba.core.base.error.AppError
import com.adamsmolik.nba.core.base.error.toScreenState

sealed interface ResultState<out V> {

    class Progress<V> : ResultState<V>

    data class Content<V>(
        val value: V,
        val refreshing: Boolean = false,
    ) : ResultState<V>

    data class Error<V>(
        val error: AppError,
    ) : ResultState<V>

    fun isRefreshing() = this is Content && refreshing
}

@SuppressWarnings("TooGenericExceptionCaught")
inline fun <V> ResultState<V>.update(block: (V) -> V): ResultState<V> =
    when (this) {
        is ResultState.Progress -> this
        is ResultState.Content -> this.copy(value = block(value))
        is ResultState.Error -> this
    }

fun <V> ResultState<V>.toLoadingState(clearData: Boolean): ResultState<V> = if (clearData.not() && this is ResultState.Content) {
    ResultState.Content(value = value, refreshing = true)
} else {
    ResultState.Progress()
}

fun <V1, V2> ResultState<V1>.merge(other: ResultState<V2>): ResultState<Pair<V1, V2>> =
    when {
        this is ResultState.Content && other is ResultState.Content -> ResultState.Content(
            value = this.value to other.value,
            refreshing = this.refreshing || other.refreshing,
        )
        this is ResultState.Error -> ResultState.Error(this.error)
        other is ResultState.Error -> ResultState.Error(other.error)
        this is ResultState.Progress || other is ResultState.Progress -> ResultState.Progress()
        else -> ResultState.Error(AppError.UnknownError) // This should never happen
    }

fun <V> Result<V>.toState(): ResultState<V> = when (this) {
    is Result.Data -> ResultState.Content(value = this.value, refreshing = false)
    is Result.Error -> ResultState.Error(this.error)
}

fun <S, T : Any> ResultState<S>.toScreenState(content: (data: S) -> T): ScreenState<T> = when (this) {
    is ResultState.Progress -> ScreenState.Progress
    is ResultState.Content -> ScreenState.Content(content(value))
    is ResultState.Error -> error.toScreenState()
}

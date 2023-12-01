package com.adamsmolik.nba.core.ui.arch

import com.adamsmolik.nba.core.base.error.AppError
import com.adamsmolik.nba.core.ui.util.TextRes

sealed class ScreenState<out T : Any> {
    data object Progress : ScreenState<Nothing>()
    data object Offline : ScreenState<Nothing>()
    data class Error(val error: AppError, val message: TextRes? = null) : ScreenState<Nothing>()
    data class Empty(val message: TextRes? = null) : ScreenState<Nothing>()
    data class Content<T : Any>(val data: T) : ScreenState<T>()
}

inline fun <T : Any, T1 : Any> ScreenState<T>.map(block: (T) -> T1): ScreenState<T1> =
    when (this) {
        is ScreenState.Content -> ScreenState.Content(block(data))
        is ScreenState.Progress -> this
        is ScreenState.Offline -> this
        is ScreenState.Error -> this
        is ScreenState.Empty -> this
    }

inline fun <T1 : Any, T2 : Any, T : Any> ScreenState<T1>.merge(
    other: ScreenState<T2>,
    block: (T1, T2) -> T,
): ScreenState<T> =
    when {
        this is ScreenState.Content && other is ScreenState.Content -> {
            ScreenState.Content(block(data, other.data))
        }
        this is ScreenState.Error -> ScreenState.Error(this.error)
        other is ScreenState.Error -> ScreenState.Error(other.error)
        this is ScreenState.Empty -> ScreenState.Empty(this.message)
        other is ScreenState.Empty -> ScreenState.Empty(other.message)
        this is ScreenState.Offline -> ScreenState.Offline
        other is ScreenState.Offline -> ScreenState.Offline
        this is ScreenState.Progress -> ScreenState.Progress
        other is ScreenState.Progress -> ScreenState.Progress
        else -> ScreenState.Error(
            error = AppError.UnknownError,
        )
    }

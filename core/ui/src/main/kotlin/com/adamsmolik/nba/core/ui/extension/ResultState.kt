package com.adamsmolik.nba.core.ui.extension

import com.adamsmolik.nba.core.base.arch.ResultState
import com.adamsmolik.nba.core.ui.arch.ScreenState

fun <S, T : Any> ResultState<S>.toScreenState(content: (data: S) -> T): ScreenState<T> = when (this) {
    is ResultState.Progress -> ScreenState.Progress
    is ResultState.Content -> ScreenState.Content(content(value))
    is ResultState.Error -> error.toScreenState()
}

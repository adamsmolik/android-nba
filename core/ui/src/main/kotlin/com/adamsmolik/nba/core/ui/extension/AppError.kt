package com.adamsmolik.nba.core.ui.extension

import com.adamsmolik.nba.core.base.error.AppError
import com.adamsmolik.nba.core.ui.arch.ScreenState
import com.adamsmolik.nba.core.ui.util.TextRes

fun AppError.toScreenState(customMessage: TextRes? = null) = when (this) {
    is AppError.NetworkError.NoConnectivityError -> ScreenState.Offline
    else -> ScreenState.Error(
        error = this,
        message = customMessage,
    )
}

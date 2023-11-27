package com.adamsmolik.nba.base.error

import com.adamsmolik.nba.base.arch.ScreenState
import com.adamsmolik.nba.base.util.TextRes

sealed class AppError {
    abstract val cause: Throwable

    data class MappingError(override val cause: Throwable, val mappingFrom: Any?) : AppError()

    sealed class NetworkError : AppError() {
        data class ResponseError(
            override val cause: Throwable,
            val responseError: ResponseErrorModel,
        ) : NetworkError()
        data class InvalidResponse(override val cause: Throwable, val errorBody: String?) : NetworkError()
        data class NoConnectivityError(override val cause: Throwable) : NetworkError()
        data object FirewallError : NetworkError() {
            override val cause: Throwable = ApiFirewallException()
        }

        data object Unauthenticated : NetworkError() {
            override val cause: Throwable = Throwable("Unauthenticated")
        }
    }

    data class Error(override val cause: Throwable) : AppError()

    data class Canceled(override val cause: Throwable) : AppError()

    data object UnknownError : AppError() {
        override val cause: Throwable = Throwable("Unknown")
    }
}

data class ResponseErrorModel(
    val type: String?,
    val message: String,
)

fun AppError.toScreenState(customMessage: TextRes? = null) = when (this) {
    is AppError.NetworkError.NoConnectivityError -> ScreenState.Offline
    else -> ScreenState.Error(
        error = this,
        message = customMessage,
    )
}

private data class ApiFirewallException(override val message: String? = "") : Throwable()

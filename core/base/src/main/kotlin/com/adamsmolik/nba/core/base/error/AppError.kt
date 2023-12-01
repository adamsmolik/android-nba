package com.adamsmolik.nba.core.base.error

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

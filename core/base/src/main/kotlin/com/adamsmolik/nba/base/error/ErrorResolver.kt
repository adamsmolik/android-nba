package com.adamsmolik.nba.base.error

import com.adamsmolik.nba.base.R
import com.adamsmolik.nba.base.util.TextRes
import javax.inject.Inject

open class ErrorResolver @Inject constructor() {

    fun getErrorMessageRes(error: AppError): Int? =
        when (error) {
            // Mapping
            is AppError.MappingError -> R.string.error_general

            // Network
            is AppError.NetworkError.ResponseError -> R.string.error_general
            is AppError.NetworkError.InvalidResponse -> R.string.error_general
            is AppError.NetworkError.NoConnectivityError -> R.string.error_no_internet
            is AppError.NetworkError.FirewallError -> R.string.error_general
            is AppError.NetworkError.Unauthenticated -> R.string.error_general

            is AppError.Error -> R.string.error_general

            is AppError.Canceled -> null

            // Unknown
            is AppError.UnknownError -> R.string.error_general
        }

    fun getErrorMessage(error: AppError): TextRes? = getErrorMessageRes(error)?.let {
        TextRes.Regular(
            it,
        )
    }
}

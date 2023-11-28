package com.adamsmolik.nba.core.base.arch

import com.adamsmolik.nba.core.base.error.AppError
import timber.log.Timber

sealed class Result<out V> {

    data class Data<out V>(val value: V) : Result<V>()
    data class Error(val error: AppError) : Result<Nothing>()

    inline fun <A> fold(onData: (V) -> A, onError: (AppError) -> A): A =
        when (this) {
            is Data -> onData(value)
            is Error -> onError(error)
        }

    inline fun onData(block: (V) -> Unit): Result<V> {
        (this as? Data)?.let { block(it.value) }
        return this
    }

    inline fun onError(block: (AppError) -> Unit): Result<V> {
        (this as? Error)?.let { block(it.error) }
        return this
    }
}

@SuppressWarnings("TooGenericExceptionCaught")
inline fun <V, V1> Result<V>.map(block: (V) -> V1): Result<V1> =
    when (this) {
        is Result.Data ->
            try {
                Result.Data(block(value))
            } catch (e: Exception) {
                Timber.d(e)
                Result.Error(AppError.MappingError(e, value))
            }
        is Result.Error -> this
    }

@SuppressWarnings("TooGenericExceptionCaught")
inline fun <V, V1> Result<V>.flatMap(block: (V) -> Result<V1>): Result<V1> =
    when (this) {
        is Result.Data ->
            try {
                block(value)
            } catch (e: Exception) {
                Timber.d(e)
                Result.Error(AppError.MappingError(e, value))
            }
        is Result.Error -> this
    }

@SuppressWarnings("TooGenericExceptionCaught")
inline fun <V1, V2, V> Result<V1>.merge(other: Result<V2>, block: (V1, V2) -> V): Result<V> =
    when {
        this is Result.Data && other is Result.Data -> {
            try {
                Result.Data(block(value, other.value))
            } catch (e: Exception) {
                Timber.d(e)
                Result.Error(AppError.MappingError(e, value))
            }
        }
        this is Result.Error -> this
        other is Result.Error -> other
        else -> resultError(AppError.UnknownError) // This should never happen
    }

@SuppressWarnings("TooGenericExceptionCaught")
inline fun <V1, V2, V3, V> Result<V1>.merge(
    other: Result<V2>,
    third: Result<V3>,
    block: (V1, V2, V3) -> V,
): Result<V> =
    when {
        this is Result.Data && other is Result.Data && third is Result.Data -> {
            try {
                Result.Data(block(value, other.value, third.value))
            } catch (e: Exception) {
                Timber.d(e)
                Result.Error(AppError.MappingError(e, value))
            }
        }
        this is Result.Error -> this
        other is Result.Error -> other
        third is Result.Error -> third
        else -> resultError(AppError.UnknownError) // This should never happen
    }

fun <V> resultData(value: V): Result<V> = Result.Data(value)
fun resultError(value: AppError): Result<Nothing> = Result.Error(value)

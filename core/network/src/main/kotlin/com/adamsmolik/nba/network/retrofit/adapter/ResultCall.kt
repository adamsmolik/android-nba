package com.adamsmolik.nba.network.retrofit.adapter

import com.adamsmolik.nba.base.arch.Result
import com.adamsmolik.nba.base.arch.resultData
import com.adamsmolik.nba.base.arch.resultError
import com.adamsmolik.nba.base.error.AppError
import com.adamsmolik.nba.network.retrofit.toAppError
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import java.lang.reflect.Type
import java.net.UnknownHostException
import kotlinx.coroutines.CancellationException
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ResultCall<R>(
    private val delegate: Call<R>,
    private val resultType: Type,
    private val moshi: Moshi,
) : Call<Result<R>> {

    override fun enqueue(callback: Callback<Result<R>>) = delegate.enqueue(
        object : Callback<R> {

            override fun onResponse(call: Call<R>, response: Response<R>) {
                val result = response.toEither(resultType, moshi)

                if (result is Result.Error) {
                    Timber.d(result.error.cause, "ResultCall.onResponse: error=${result.error}")
                }

                callback.onResponse(this@ResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<R>, throwable: Throwable) {
                val error = if (isCanceled) {
                    AppError.Canceled(throwable)
                } else {
                    when (throwable) {
                        // Moshi parsing error
                        is JsonDataException -> {
                            val path = with(call.request()) { "${url.encodedPath}?${url.encodedQuery}" }
                            val method = call.request().method

                            Timber.e(
                                throwable,
                                "ResultCall.onFailure: Parse error request=$method:$path",
                            )
                            AppError.NetworkError.InvalidResponse(throwable, null)
                        }
                        is CancellationException -> AppError.Canceled(throwable)
                        // No connectivity error
                        is UnknownHostException -> AppError.NetworkError.NoConnectivityError(
                            throwable,
                        )
                        // Unknown error
                        else -> AppError.NetworkError.InvalidResponse(throwable, null)
                    }
                }

                Timber.d("ResultCall.onFailure: error=$error")

                callback.onResponse(this@ResultCall, Response.success(Result.Error(error)))
            }
        },
    )

    override fun clone(): Call<Result<R>> = ResultCall(delegate.clone(), resultType, moshi)

    override fun execute(): Response<Result<R>> {
        throw UnsupportedOperationException("ResultCall doesn't support execute")
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}

@Suppress("UNCHECKED_CAST")
private fun <R> Response<R>.toEither(
    resultType: Type,
    moshi: Moshi,
): Result<R> {
    // Http error response (4xx - 5xx)
    if (!isSuccessful) {
        return resultError(toAppError(moshi))
    }

    // Http success response with body
    val body = body()
    if (body != null) {
        return resultData(body)
    }

    // if we defined Unit as success type it means we expected no response body e.g. in case of 204 No Content
    return if (resultType == Unit::class.java) {
        resultData(Unit) as Result<R>
    } else {
        resultError(
            AppError.NetworkError.InvalidResponse(Throwable("Response body was null"), null),
        )
    }
}

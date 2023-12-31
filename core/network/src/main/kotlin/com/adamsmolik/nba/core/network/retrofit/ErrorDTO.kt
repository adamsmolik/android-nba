package com.adamsmolik.nba.core.network.retrofit

import com.adamsmolik.nba.core.base.error.AppError
import com.adamsmolik.nba.core.base.error.ResponseErrorModel
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import retrofit2.Response

// For api specific error
@JsonClass(generateAdapter = true)
data class ErrorDTO(
    val error: String?,
    val message: String,
)

@SuppressWarnings("TooGenericExceptionCaught", "SwallowedException")
fun Response<*>.toAppError(moshi: Moshi): AppError {
    val errorBody = try {
        errorBody()?.string()
    } catch (e: Exception) {
        null
    }

    return try {
        val error = if (errorBody != null) {
            val parser = moshi.adapter(ErrorDTO::class.java)
            parser?.fromJson(errorBody)
        } else {
            null
        }

        if (error != null) {
            AppError.NetworkError.ResponseError(
                ApiResponseException("statusCode=${code()}"),
                error.toModel(),
            )
        } else {
            AppError.NetworkError.InvalidResponse(
                ApiException("Unable to parse error: body=$errorBody"),
                errorBody,
            )
        }
    } catch (e: Exception) {
        AppError.NetworkError.InvalidResponse(e, errorBody)
    }
}

fun ErrorDTO.toModel() = ResponseErrorModel(
    type = error,
    message = message,
)

private data class ApiResponseException(override val message: String?) : Throwable()
private data class ApiException(override val message: String?) : Throwable()

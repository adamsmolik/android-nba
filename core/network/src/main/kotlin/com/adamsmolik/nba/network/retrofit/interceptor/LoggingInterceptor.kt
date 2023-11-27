package com.adamsmolik.nba.network.retrofit.interceptor

import com.adamsmolik.nba.base.CoreConfig
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

object LoggingInterceptor {
    @SuppressWarnings("SwallowedException")
    fun provide(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message ->
            val result = when {
                message.startsWith("{") -> {
                    try {
                        JSONObject(message).toString(4)
                    } catch (e: JSONException) {
                        message
                    }
                }
                message.startsWith("[") -> {
                    try {
                        JSONArray(message).toString(4)
                    } catch (e: JSONException) {
                        message
                    }
                }
                else -> {
                    message
                }
            }

            Timber.d(result)
        }.apply {
            level = when (CoreConfig.RELEASE_BUILD_TYPE) {
                true -> HttpLoggingInterceptor.Level.BASIC
                false -> HttpLoggingInterceptor.Level.BODY
            }
        }
}

package com.adamsmolik.nba.core.network.retrofit.interceptor

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

// const val AUTHORIZATION_HEADER = "Authorization"
private const val ACCEPT_CHARSET_HEADER = "Accept-Charset"
private const val CONTENT_TYPE_HEADER = "Content-Type"
// private const val USER_AGENT = "User-Agent"

// TODO
fun getAuthorizationHeader(token: String) = "Bearer $token"

abstract class HeaderInterceptor : Interceptor {

    abstract suspend fun customHeaders(originalRequest: Request): Map<String, String>

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        buildNewRequest(chain),
    )

    private fun buildNewRequest(chain: Interceptor.Chain): Request = runBlocking {
        val originalRequest = chain.request()

        originalRequest
            .newBuilder()
            .apply {
                addHeader(ACCEPT_CHARSET_HEADER, "utf-8")
                addHeader(CONTENT_TYPE_HEADER, "application/json")

                customHeaders(originalRequest).forEach { (name, value) ->
                    addHeader(name, value)
                }
            }
            .build()
    }
}

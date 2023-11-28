package com.adamsmolik.nba.core.network.retrofit.adapter

import com.adamsmolik.nba.core.base.arch.Result
import com.squareup.moshi.Moshi
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit

class ResultCallAdapterFactory @Inject constructor(
    private val moshi: Moshi,
) : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        check(returnType is ParameterizedType) { "Return type must be a parameterized type." }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != Result::class.java) return null
        check(responseType is ParameterizedType) { "Result type must be a parameterized type." }

        val resultType = getParameterUpperBound(0, responseType)
        return ResultCallAdapter<Any>(resultType, moshi)
    }
}

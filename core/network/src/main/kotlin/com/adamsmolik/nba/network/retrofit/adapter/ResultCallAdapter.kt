package com.adamsmolik.nba.network.retrofit.adapter

import com.adamsmolik.nba.base.arch.Result
import com.squareup.moshi.Moshi
import java.lang.reflect.Type
import retrofit2.Call
import retrofit2.CallAdapter

class ResultCallAdapter<R>(
    private val resultType: Type,
    private val moshi: Moshi,
) : CallAdapter<R, Call<Result<R>>> {

    override fun responseType(): Type = resultType

    override fun adapt(call: Call<R>): Call<Result<R>> = ResultCall(call, resultType, moshi)
}

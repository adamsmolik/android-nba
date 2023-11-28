package com.adamsmolik.nba.core.network.retrofit.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataDTO<out T : Any>(
    val data: T,
    val meta: MetaDTO,
)

@JsonClass(generateAdapter = true)
data class MetaDTO(
    @Json(name = "total_pages")
    val total: Int,
)

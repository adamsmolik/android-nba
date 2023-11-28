package com.adamsmolik.nba.data.player.api

import com.adamsmolik.nba.core.base.arch.Result
import com.adamsmolik.nba.core.network.retrofit.data.model.DataDTO
import com.adamsmolik.nba.data.player.model.PlayerDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface PlayerApi {

    @GET("players")
    suspend fun listPlayers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Result<DataDTO<List<PlayerDTO>>>
}

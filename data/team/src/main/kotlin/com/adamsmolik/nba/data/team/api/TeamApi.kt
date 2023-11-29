package com.adamsmolik.nba.data.team.api

import com.adamsmolik.nba.core.base.arch.Result
import com.adamsmolik.nba.data.team.model.TeamDetailDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamApi {

    @GET("teams/{id}")
    suspend fun getTeamDetail(
        @Path("id") id: Int,
    ): Result<TeamDetailDTO>
}

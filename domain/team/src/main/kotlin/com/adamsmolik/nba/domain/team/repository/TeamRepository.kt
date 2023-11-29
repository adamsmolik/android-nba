package com.adamsmolik.nba.domain.team.repository

import com.adamsmolik.nba.core.base.arch.Result
import com.adamsmolik.nba.domain.team.model.TeamDetailModel

interface TeamRepository {

    suspend fun getTeamDetail(
        id: Int,
    ): Result<TeamDetailModel>
}

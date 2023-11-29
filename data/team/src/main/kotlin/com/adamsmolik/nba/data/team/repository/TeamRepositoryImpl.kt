package com.adamsmolik.nba.data.team.repository

import com.adamsmolik.nba.core.base.arch.Result
import com.adamsmolik.nba.core.base.arch.map
import com.adamsmolik.nba.data.team.api.TeamApi
import com.adamsmolik.nba.data.team.model.toModel
import com.adamsmolik.nba.domain.team.model.TeamDetailModel
import com.adamsmolik.nba.domain.team.repository.TeamRepository
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val teamApi: TeamApi,
) : TeamRepository {

    override suspend fun getTeamDetail(id: Int): Result<TeamDetailModel> = teamApi
        .getTeamDetail(id)
        .map { it.toModel() }
}

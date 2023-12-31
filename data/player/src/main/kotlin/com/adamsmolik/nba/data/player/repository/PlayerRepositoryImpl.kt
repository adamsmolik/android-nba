package com.adamsmolik.nba.data.player.repository

import com.adamsmolik.nba.core.base.arch.Result
import com.adamsmolik.nba.core.base.arch.map
import com.adamsmolik.nba.data.player.api.PlayerApi
import com.adamsmolik.nba.data.player.model.toModel
import com.adamsmolik.nba.domain.player.model.PlayerDetailModel
import com.adamsmolik.nba.domain.player.model.PlayerModel
import com.adamsmolik.nba.domain.player.repository.PlayerRepository
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playerApi: PlayerApi,
) : PlayerRepository {

    override suspend fun listPlayers(limit: Int, offset: Int): Result<List<PlayerModel>> = playerApi
        .listPlayers(
            // This is not ideal, my paging is designed for offset with limit. I decided to use this workaround instead of rewriting my paging.
            page = offset / limit + 1,
            perPage = limit,
        ).map { it.data.map { item -> item.toModel() } }

    override suspend fun getPlayerDetail(id: Int): Result<PlayerDetailModel> = playerApi
        .getPlayerDetail(id)
        .map { it.toModel() }
}

package com.adamsmolik.nba.domain.player.repository

import com.adamsmolik.nba.core.base.arch.Result
import com.adamsmolik.nba.domain.player.model.PlayerDetailModel
import com.adamsmolik.nba.domain.player.model.PlayerModel

interface PlayerRepository {
    suspend fun listPlayers(
        limit: Int,
        offset: Int,
    ): Result<List<PlayerModel>>

    suspend fun getPlayerDetail(
        id: Int,
    ): Result<PlayerDetailModel>
}

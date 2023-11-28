package com.adamsmolik.nba.domain.player.usecase

import com.adamsmolik.nba.core.base.arch.Result
import com.adamsmolik.nba.core.base.arch.UseCase
import com.adamsmolik.nba.core.base.arch.UseCaseInput
import com.adamsmolik.nba.domain.player.model.PlayerModel
import com.adamsmolik.nba.domain.player.repository.PlayerRepository
import javax.inject.Inject

data class ListPlayersUseCaseInput(
    val offset: Int,
    val limit: Int,
) : UseCaseInput

class ListPlayersUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
) : UseCase<ListPlayersUseCaseInput, List<PlayerModel>> {
    override suspend fun execute(input: ListPlayersUseCaseInput): Result<List<PlayerModel>> = with(input) {
        playerRepository.listPlayers(limit, offset)
    }
}

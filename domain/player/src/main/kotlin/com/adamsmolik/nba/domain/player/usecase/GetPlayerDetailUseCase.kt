package com.adamsmolik.nba.domain.player.usecase

import com.adamsmolik.nba.core.base.arch.Result
import com.adamsmolik.nba.core.base.arch.UseCase
import com.adamsmolik.nba.core.base.arch.UseCaseInput
import com.adamsmolik.nba.domain.player.model.PlayerDetailModel
import com.adamsmolik.nba.domain.player.repository.PlayerRepository
import javax.inject.Inject

data class GetPlayerDetailUseCaseInput(
    val id: Int,
) : UseCaseInput

class GetPlayerDetailUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
) : UseCase<GetPlayerDetailUseCaseInput, PlayerDetailModel> {
    override suspend fun execute(input: GetPlayerDetailUseCaseInput): Result<PlayerDetailModel> = with(input) {
        playerRepository.getPlayerDetail(id)
    }
}

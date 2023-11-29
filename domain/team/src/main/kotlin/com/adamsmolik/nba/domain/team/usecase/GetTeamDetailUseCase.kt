package com.adamsmolik.nba.domain.team.usecase

import com.adamsmolik.nba.core.base.arch.Result
import com.adamsmolik.nba.core.base.arch.UseCase
import com.adamsmolik.nba.core.base.arch.UseCaseInput
import com.adamsmolik.nba.domain.team.model.TeamDetailModel
import com.adamsmolik.nba.domain.team.repository.TeamRepository
import javax.inject.Inject

data class GetTeamDetailUseCaseInput(
    val id: Int,
) : UseCaseInput

class GetTeamDetailUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) : UseCase<GetTeamDetailUseCaseInput, TeamDetailModel> {
    override suspend fun execute(input: GetTeamDetailUseCaseInput): Result<TeamDetailModel> = with(input) {
        teamRepository.getTeamDetail(id)
    }
}

package com.adamsmolik.nba.team.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import com.adamsmolik.nba.core.base.arch.BaseStatefulViewModel
import com.adamsmolik.nba.core.base.navigation.ARG_INITIALIZER
import com.adamsmolik.nba.domain.team.usecase.GetTeamDetailUseCase
import com.adamsmolik.nba.domain.team.usecase.GetTeamDetailUseCaseInput
import com.adamsmolik.nba.team.ui.model.TeamDetailUiModel
import com.adamsmolik.nba.team.ui.model.toUiModel
import com.adamsmolik.nba.team.ui.nav.TeamDetailInitializer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    private val getTeamDetailUseCase: GetTeamDetailUseCase,
    savedState: SavedStateHandle,
) : BaseStatefulViewModel<TeamDetailUiModel>() {

    private val initializer = requireNotNull(savedState.get<TeamDetailInitializer>(ARG_INITIALIZER))

    init {
        loadData()
    }

    fun loadData() {
        launch {
            emitProgress()

            val input = GetTeamDetailUseCaseInput(
                id = initializer.id,
            )

            getTeamDetailUseCase.execute(input)
                .onData {
                    emitContent(it.toUiModel())
                }
                .onError {
                    emitError(it)
                }
        }
    }
}

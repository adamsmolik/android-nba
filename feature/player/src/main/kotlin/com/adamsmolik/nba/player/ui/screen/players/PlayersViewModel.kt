package com.adamsmolik.nba.player.ui.screen.players

import com.adamsmolik.nba.core.base.arch.BaseStatefulViewModel
import com.adamsmolik.nba.core.base.paging.PagedDataProvider
import com.adamsmolik.nba.core.base.paging.toScreenState
import com.adamsmolik.nba.core.base.paging.toUiModel
import com.adamsmolik.nba.domain.player.usecase.ListPlayersUseCase
import com.adamsmolik.nba.domain.player.usecase.ListPlayersUseCaseInput
import com.adamsmolik.nba.player.ui.model.PlayersUiModel
import com.adamsmolik.nba.player.ui.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val listPlayers: ListPlayersUseCase,
) : BaseStatefulViewModel<PlayersUiModel>() {

    private val playersDataProvider = PagedDataProvider(
        coroutineScope = this,
    ) { offset, pageSize ->
        val input = ListPlayersUseCaseInput(
            offset = offset,
            limit = pageSize,
        )

        listPlayers.execute(input)
    }

    init {
        observe()
        loadData()
    }

    fun loadData() {
        playersDataProvider.reloadData(clearData = true)
    }

    private fun observe() {
        playersDataProvider.state
            .onEach { pagedState ->
                emitState(
                    pagedState.toScreenState { players ->
                        PlayersUiModel(
                            players = players.map { it.toUiModel() },
                            paging = pagedState.toUiModel(),
                        )
                    },
                )
            }
            .launchIn(this)
    }
}

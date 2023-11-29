package com.adamsmolik.nba.player.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import com.adamsmolik.nba.core.base.arch.BaseStatefulViewModel
import com.adamsmolik.nba.core.base.navigation.ARG_INITIALIZER
import com.adamsmolik.nba.domain.player.usecase.GetPlayerDetailUseCase
import com.adamsmolik.nba.domain.player.usecase.GetPlayerDetailUseCaseInput
import com.adamsmolik.nba.player.ui.model.PlayerDetailUiModel
import com.adamsmolik.nba.player.ui.model.toUiModel
import com.adamsmolik.nba.player.ui.nav.PlayerDetailInitializer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PlayerDetailViewModel @Inject constructor(
    private val getPlayerDetail: GetPlayerDetailUseCase,
    savedState: SavedStateHandle,
) : BaseStatefulViewModel<PlayerDetailUiModel>() {

    private val initializer = requireNotNull(savedState.get<PlayerDetailInitializer>(ARG_INITIALIZER))

    init {
        loadData()
    }

    fun loadData() {
        launch {
            emitProgress()

            val input = GetPlayerDetailUseCaseInput(
                id = initializer.id,
            )

            getPlayerDetail.execute(input)
                .onData {
                    emitContent(it.toUiModel())
                }
                .onError {
                    emitError(it)
                }
        }
    }
}

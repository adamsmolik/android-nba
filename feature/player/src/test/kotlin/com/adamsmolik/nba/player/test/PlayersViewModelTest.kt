package com.adamsmolik.nba.player.test

import app.cash.turbine.test
import com.adamsmolik.nba.core.base.arch.resultData
import com.adamsmolik.nba.core.ui.arch.ScreenState
import com.adamsmolik.nba.domain.player.model.PlayerModel
import com.adamsmolik.nba.domain.player.model.mock
import com.adamsmolik.nba.domain.player.usecase.ListPlayersUseCase
import com.adamsmolik.nba.player.ui.model.toUiModel
import com.adamsmolik.nba.player.ui.screen.players.PlayersViewModel
import com.adamsmolik.nba.test.base.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PlayersViewModelTest : BaseViewModelTest<PlayersViewModel>() {
    private lateinit var _listPlayers: ListPlayersUseCase

    private val players = List(10) { PlayerModel.mock(it) }

    @Before
    override fun mock() {
        super.mock()

        _listPlayers = mockk()
        coEvery { _listPlayers.execute(any()) } returns resultData(players)
    }

    @Test
    fun `init`() = runTest {
        getViewModel().run {
            screenState.test {
                val state = expectMostRecentItem()

                assert(state is ScreenState.Content && state.data.players == players.map { it.toUiModel() }) {
                    "Content state: $contentState"
                }

                coVerify(exactly = 1) { _listPlayers.execute(any()) }
            }
        }
    }

    @Test
    fun `load data`() = runTest {
        getViewModel().run {
            screenState.test {
                loadData()

                val state = expectMostRecentItem()

                assert(state is ScreenState.Content && state.data.players == players.map { it.toUiModel() }) {
                    "Content state: $contentState"
                }

                coVerify(exactly = 2) { _listPlayers.execute(any()) }
            }
        }
    }

    private fun getViewModel(
        listPlayers: ListPlayersUseCase = _listPlayers,
    ) = PlayersViewModel(
        listPlayers = listPlayers,
    )
}

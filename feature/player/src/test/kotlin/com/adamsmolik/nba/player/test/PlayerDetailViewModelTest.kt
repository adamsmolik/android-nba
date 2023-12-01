package com.adamsmolik.nba.player.test

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.adamsmolik.nba.core.base.arch.resultData
import com.adamsmolik.nba.core.base.navigation.ARG_INITIALIZER
import com.adamsmolik.nba.core.ui.arch.ScreenState
import com.adamsmolik.nba.domain.player.model.PlayerDetailModel
import com.adamsmolik.nba.domain.player.model.mock
import com.adamsmolik.nba.domain.player.usecase.GetPlayerDetailUseCase
import com.adamsmolik.nba.player.ui.model.toUiModel
import com.adamsmolik.nba.player.ui.nav.PlayerDetailInitializer
import com.adamsmolik.nba.player.ui.screen.detail.PlayerDetailViewModel
import com.adamsmolik.nba.test.base.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PlayerDetailViewModelTest : BaseViewModelTest<PlayerDetailViewModel>() {
    private lateinit var _getPlayerDetail: GetPlayerDetailUseCase

    private val _savedState: SavedStateHandle = SavedStateHandle().apply {
        set(
            ARG_INITIALIZER,
            PlayerDetailInitializer(
                id = 0,
            ),
        )
    }

    private val playerDetail = PlayerDetailModel.mock()

    @Before
    override fun mock() {
        super.mock()

        _getPlayerDetail = mockk()
        coEvery { _getPlayerDetail.execute(any()) } returns resultData(playerDetail)
    }

    @Test
    fun `init`() = runTest {
        getViewModel().run {
            screenState.test {
                val state = expectMostRecentItem()

                assert(state is ScreenState.Content && state.data == playerDetail.toUiModel()) {
                    "Content state: $contentState"
                }

                coVerify(exactly = 1) { _getPlayerDetail.execute(any()) }
            }
        }
    }

    @Test
    fun `load data`() = runTest {
        getViewModel().run {
            screenState.test {
                loadData()

                val state = expectMostRecentItem()

                assert(state is ScreenState.Content && state.data == playerDetail.toUiModel()) {
                    "Content state: $contentState"
                }

                coVerify(exactly = 2) { _getPlayerDetail.execute(any()) }
            }
        }
    }

    private fun getViewModel(
        getPlayerDetail: GetPlayerDetailUseCase = _getPlayerDetail,
        savedState: SavedStateHandle = _savedState,
    ) = PlayerDetailViewModel(
        getPlayerDetail = getPlayerDetail,
        savedState = savedState,
    )
}

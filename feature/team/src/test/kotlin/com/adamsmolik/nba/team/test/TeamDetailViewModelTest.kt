package com.adamsmolik.nba.team.test

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.adamsmolik.nba.core.base.arch.resultData
import com.adamsmolik.nba.core.base.navigation.ARG_INITIALIZER
import com.adamsmolik.nba.core.ui.arch.ScreenState
import com.adamsmolik.nba.domain.team.model.TeamDetailModel
import com.adamsmolik.nba.domain.team.model.mock
import com.adamsmolik.nba.domain.team.usecase.GetTeamDetailUseCase
import com.adamsmolik.nba.team.ui.model.toUiModel
import com.adamsmolik.nba.team.ui.nav.TeamDetailInitializer
import com.adamsmolik.nba.team.ui.screen.detail.TeamDetailViewModel
import com.adamsmolik.nba.test.base.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TeamDetailViewModelTest : BaseViewModelTest<TeamDetailViewModel>() {
    private lateinit var _getTeamDetailUseCase: GetTeamDetailUseCase

    private val _savedState: SavedStateHandle = SavedStateHandle().apply {
        set(
            ARG_INITIALIZER,
            TeamDetailInitializer(
                id = 0,
            ),
        )
    }

    private val teamDetail = TeamDetailModel.mock()

    @Before
    override fun mock() {
        super.mock()

        _getTeamDetailUseCase = mockk()
        coEvery { _getTeamDetailUseCase.execute(any()) } returns resultData(teamDetail)
    }

    @Test
    fun `init`() = runTest {
        getViewModel().run {
            screenState.test {
                val state = expectMostRecentItem()

                assert(state is ScreenState.Content && state.data == teamDetail.toUiModel()) {
                    "Content state: $contentState"
                }

                coVerify(exactly = 1) { _getTeamDetailUseCase.execute(any()) }
            }
        }
    }

    @Test
    fun `load data`() = runTest {
        getViewModel().run {
            screenState.test {
                loadData()

                val state = expectMostRecentItem()

                assert(state is ScreenState.Content && state.data == teamDetail.toUiModel()) {
                    "Content state: $contentState"
                }

                coVerify(exactly = 2) { _getTeamDetailUseCase.execute(any()) }
            }
        }
    }

    private fun getViewModel(
        getTeamDetailUseCase: GetTeamDetailUseCase = _getTeamDetailUseCase,
        savedState: SavedStateHandle = _savedState,
    ) = TeamDetailViewModel(
        getTeamDetailUseCase = getTeamDetailUseCase,
        savedState = savedState,
    )
}

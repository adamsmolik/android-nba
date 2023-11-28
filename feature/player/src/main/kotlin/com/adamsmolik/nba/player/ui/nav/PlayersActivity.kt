package com.adamsmolik.nba.player.ui.nav

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.adamsmolik.nba.core.base.arch.BaseActivity
import com.adamsmolik.nba.player.ui.screen.players.PlayersScreen
import com.adamsmolik.nba.player.ui.screen.players.PlayersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayersActivity : BaseActivity() {

    override val viewModel: PlayersViewModel by viewModels()

    override val screen: @Composable () -> Unit = {
        PlayersScreen(viewModel = viewModel)
    }

    override fun screenView() {
        // Implement analytics
    }
}

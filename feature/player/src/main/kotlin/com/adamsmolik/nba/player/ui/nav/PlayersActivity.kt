package com.adamsmolik.nba.player.ui.nav

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.adamsmolik.nba.core.ui.arch.BaseActivity
import com.adamsmolik.nba.player.LocalPlayerContract
import com.adamsmolik.nba.player.PlayerContract
import com.adamsmolik.nba.player.ui.screen.players.PlayersScreen
import com.adamsmolik.nba.player.ui.screen.players.PlayersViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlayersActivity : BaseActivity() {

    @Inject
    lateinit var playerContract: PlayerContract

    override val viewModel: PlayersViewModel by viewModels()

    override val screen: @Composable () -> Unit = {
        CompositionLocalProvider(
            LocalPlayerContract provides playerContract,
        ) {
            PlayersScreen(viewModel = viewModel)
        }
    }

    override fun screenView() {
        // Implement analytics
    }
}

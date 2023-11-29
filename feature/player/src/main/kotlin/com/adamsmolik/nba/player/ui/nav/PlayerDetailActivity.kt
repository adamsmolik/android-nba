package com.adamsmolik.nba.player.ui.nav

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.adamsmolik.nba.core.base.arch.BaseActivity
import com.adamsmolik.nba.core.base.navigation.ARG_INITIALIZER
import com.adamsmolik.nba.core.base.navigation.Initializer
import com.adamsmolik.nba.player.LocalPlayerContract
import com.adamsmolik.nba.player.PlayerContract
import com.adamsmolik.nba.player.ui.screen.detail.PlayerDetailScreen
import com.adamsmolik.nba.player.ui.screen.detail.PlayerDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayerDetailInitializer(
    val id: Int,
) : Initializer

@AndroidEntryPoint
class PlayerDetailActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context, initializer: PlayerDetailInitializer) = Intent(context, PlayerDetailActivity::class.java).apply {
            putExtra(ARG_INITIALIZER, initializer)
        }
    }

    @Inject
    lateinit var playerContract: PlayerContract

    override val viewModel: PlayerDetailViewModel by viewModels()

    override val screen: @Composable () -> Unit = {
        CompositionLocalProvider(
            LocalPlayerContract provides playerContract,
        ) {
            PlayerDetailScreen(viewModel = viewModel)
        }
    }

    override fun screenView() {
        // Implement analytics
    }
}

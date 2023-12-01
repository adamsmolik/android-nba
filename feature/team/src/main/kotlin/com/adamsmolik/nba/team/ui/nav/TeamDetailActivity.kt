package com.adamsmolik.nba.team.ui.nav

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.adamsmolik.nba.core.base.navigation.ARG_INITIALIZER
import com.adamsmolik.nba.core.base.navigation.Initializer
import com.adamsmolik.nba.core.ui.arch.BaseActivity
import com.adamsmolik.nba.team.ui.screen.detail.TeamDetailScreen
import com.adamsmolik.nba.team.ui.screen.detail.TeamDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamDetailInitializer(
    val id: Int,
) : Initializer

@AndroidEntryPoint
class TeamDetailActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context, initializer: TeamDetailInitializer) = Intent(context, TeamDetailActivity::class.java).apply {
            putExtra(ARG_INITIALIZER, initializer)
        }
    }

    override val viewModel: TeamDetailViewModel by viewModels()

    override val screen: @Composable () -> Unit = {
        TeamDetailScreen(viewModel = viewModel)
    }

    override fun screenView() {
        // Implement analytics
    }
}

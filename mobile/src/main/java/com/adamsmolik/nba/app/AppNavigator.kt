package com.adamsmolik.nba.app

import android.content.Context
import com.adamsmolik.nba.player.ui.nav.PlayerDetailActivity
import com.adamsmolik.nba.player.ui.nav.PlayerDetailInitializer
import com.adamsmolik.nba.team.ui.nav.TeamDetailActivity
import com.adamsmolik.nba.team.ui.nav.TeamDetailInitializer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigator @Inject constructor() {

    fun navigatePlayerDetail(
        context: Context,
        id: Int,
    ) = context.startActivity(
        PlayerDetailActivity.newIntent(
            context = context,
            initializer = PlayerDetailInitializer(
                id = id,
            ),
        ),
    )

    fun navigateTeamDetail(
        context: Context,
        id: Int,
    ) = context.startActivity(
        TeamDetailActivity.newIntent(
            context = context,
            initializer = TeamDetailInitializer(
                id = id,
            ),
        ),
    )
}

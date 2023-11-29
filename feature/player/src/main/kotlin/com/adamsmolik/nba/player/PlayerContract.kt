package com.adamsmolik.nba.player

import android.content.Context
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.adamsmolik.nba.core.base.composable.noLocalProviderFor

interface PlayerContract {
    fun navigatePlayerDetail(context: Context, id: Int)
    fun navigateTeamDetail(context: Context, id: Int)
}

val LocalPlayerContract: ProvidableCompositionLocal<PlayerContract> = staticCompositionLocalOf {
    noLocalProviderFor("PlayerContract")
}

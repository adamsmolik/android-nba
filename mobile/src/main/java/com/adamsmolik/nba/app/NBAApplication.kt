package com.adamsmolik.nba.app

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.adamsmolik.nba.core.network.di.CoilHttpClient
import com.adamsmolik.nba.player.PlayerContract
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class NBAApplication :
    Application(),
    ImageLoaderFactory,
    PlayerContract {

    @Inject
    lateinit var coilHttpClient: CoilHttpClient

    @Inject
    lateinit var appNavigator: AppNavigator

    override fun newImageLoader(): ImageLoader = ImageLoader.Builder(this)
        .okHttpClient(coilHttpClient.client)
        .build()

    override fun navigatePlayerDetail(context: Context, id: Int) = appNavigator.navigatePlayerDetail(context, id)
    override fun navigateTeamDetail(context: Context, id: Int) = appNavigator.navigateTeamDetail(context, id)
}

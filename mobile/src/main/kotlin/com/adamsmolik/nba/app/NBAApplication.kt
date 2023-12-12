package com.adamsmolik.nba.app

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.adamsmolik.nba.core.base.CoreConfig
import com.adamsmolik.nba.core.base.logging.TimberDebugTree
import com.adamsmolik.nba.core.network.di.CoilHttpClient
import com.adamsmolik.nba.player.PlayerContract
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import timber.log.Timber

@HiltAndroidApp
class NBAApplication :
    Application(),
    ImageLoaderFactory,
    PlayerContract {

    @Inject
    lateinit var coilHttpClient: CoilHttpClient

    @Inject
    lateinit var appNavigator: AppNavigator

    @Inject
    lateinit var timberDebugTreeProvider: TimberDebugTree

    override fun onCreate() {
        super.onCreate()

        initTimber()
        Timber.d("App init")
    }

    override fun newImageLoader(): ImageLoader = ImageLoader.Builder(this)
        .okHttpClient(coilHttpClient.client)
        .build()

    override fun navigatePlayerDetail(context: Context, id: Int) = appNavigator.navigatePlayerDetail(context, id)
    override fun navigateTeamDetail(context: Context, id: Int) = appNavigator.navigateTeamDetail(context, id)

    private fun initTimber() {
        if (CoreConfig.RELEASE_BUILD_TYPE) {
            if (!CoreConfig.PRODUCTION_FLAVOR) {
                // Log to console for all the flavors except Production
                Timber.plant(timberDebugTreeProvider)
            }
        } else {
            // Log to console for debug builds
            Timber.plant(timberDebugTreeProvider)
        }
    }
}

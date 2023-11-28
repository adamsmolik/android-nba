package com.adamsmolik.nba.app

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.adamsmolik.nba.core.network.di.CoilHttpClient
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class NBAApplication : Application(), ImageLoaderFactory {

    @Inject
    lateinit var coilHttpClient: CoilHttpClient

    override fun newImageLoader(): ImageLoader = ImageLoader.Builder(this)
        .okHttpClient(coilHttpClient.client)
        .build()
}

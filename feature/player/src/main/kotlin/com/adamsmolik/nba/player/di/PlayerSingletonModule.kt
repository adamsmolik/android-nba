package com.adamsmolik.nba.player.di

import android.app.Application
import com.adamsmolik.nba.player.PlayerContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PlayerSingletonModule {

    @Provides
    fun providePlayerDetailContract(application: Application): PlayerContract = application as PlayerContract
}

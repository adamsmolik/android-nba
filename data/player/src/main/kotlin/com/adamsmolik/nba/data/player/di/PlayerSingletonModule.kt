package com.adamsmolik.nba.data.player.di

import com.adamsmolik.nba.core.network.di.BDLService
import com.adamsmolik.nba.data.player.api.PlayerApi
import com.adamsmolik.nba.data.player.repository.PlayerRepositoryImpl
import com.adamsmolik.nba.domain.player.repository.PlayerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object PlayerSingletonModule {

    @Provides
    fun providePlayerApi(bdsService: BDLService): PlayerApi = bdsService.client.create()
}

@Module
@InstallIn(SingletonComponent::class)
interface PlayerSingletonBindModule {

    @Binds
    fun bindPlayerRepository(repo: PlayerRepositoryImpl): PlayerRepository
}

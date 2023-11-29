package com.adamsmolik.nba.data.team.di

import com.adamsmolik.nba.core.network.di.BDLService
import com.adamsmolik.nba.data.team.api.TeamApi
import com.adamsmolik.nba.data.team.repository.TeamRepositoryImpl
import com.adamsmolik.nba.domain.team.repository.TeamRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object TeamSingletonModule {

    @Provides
    fun provideTeamApi(bdsService: BDLService): TeamApi = bdsService.client.create()
}

@Module
@InstallIn(SingletonComponent::class)
interface TeamSingletonBindModule {

    @Binds
    fun bindTeamRepository(repo: TeamRepositoryImpl): TeamRepository
}

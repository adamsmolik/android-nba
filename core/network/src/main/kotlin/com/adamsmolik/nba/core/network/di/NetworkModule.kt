package com.adamsmolik.nba.core.network.di

import com.adamsmolik.nba.core.base.CoreConfig
import com.adamsmolik.nba.core.network.retrofit.adapter.ResultCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = when (CoreConfig.RELEASE_BUILD_TYPE) {
            true -> HttpLoggingInterceptor.Level.BASIC
            false -> HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideApiHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): ApiHttpClient = ApiHttpClient(
        OkHttpClient.Builder()
            .connectTimeout(CoreConfig.Api.REQUEST_TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(CoreConfig.Api.REQUEST_TIMEOUT_SEC, TimeUnit.SECONDS)
            .writeTimeout(CoreConfig.Api.REQUEST_TIMEOUT_SEC, TimeUnit.SECONDS)
            .dispatcher(
                Dispatcher().apply {
                    maxRequests = CoreConfig.Api.MAX_PARALLEL_REQUESTS
                    maxRequestsPerHost = CoreConfig.Api.MAX_PARALLEL_REQUESTS
                },
            )
            .retryOnConnectionFailure(false)
            .addInterceptor(httpLoggingInterceptor)
            .build(),
    )

    @Provides
    @Singleton
    fun provideCoilHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): CoilHttpClient = CoilHttpClient(
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .retryOnConnectionFailure(false)
            .build(),
    )

    @Provides
    @Singleton
    fun provideBDLService(
        httpClient: ApiHttpClient,
        moshi: Moshi,
        resultCallAdapterFactory: ResultCallAdapterFactory,
    ): BDLService = BDLService(
        Retrofit.Builder()
            .baseUrl(CoreConfig.Api.BDLService.BASE_URL)
            .client(httpClient.client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(resultCallAdapterFactory)
            .build(),
    )
}

data class ApiHttpClient(
    val client: OkHttpClient,
)

data class CoilHttpClient(
    val client: OkHttpClient,
)

data class BDLService(
    val client: Retrofit,
)

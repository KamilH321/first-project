package ru.itis.network.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.buildconfig.api.BuildConfigProvider
import ru.itis.network.OMDbApi
import ru.itis.network.interceptor.ApiKeyInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    fun provideOkHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(
        buildConfigProvider: BuildConfigProvider,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(buildConfigProvider.getOMDbApiBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesOMDbApi(retrofit: Retrofit): OMDbApi {
        return retrofit.create(OMDbApi::class.java)
    }
}
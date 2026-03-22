package ru.itis.di

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.buildconfig.impl.BuildConfigProviderImpl
import ru.itis.data.mapper.FilmModelMapper
import ru.itis.data.repository.FilmRepositoryImpl
import ru.itis.domain.repository.FilmRepository
import ru.itis.network.OMDbApi
import java.util.concurrent.TimeUnit

object ServiceLocator {

    private val buildConfigProviderImpl = BuildConfigProviderImpl()

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(buildConfigProviderImpl.getOMDbApiBaseUrl())
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val omdbApi = retrofit.create(OMDbApi::class.java)

    fun getOMDbApi() = omdbApi

    fun getFilmRepository(): FilmRepository {
        return FilmRepositoryImpl(
            omdbApi = getOMDbApi(),
            filmModelMapper = FilmModelMapper(),
            buildConfigProvider = buildConfigProviderImpl
        )
    }
}
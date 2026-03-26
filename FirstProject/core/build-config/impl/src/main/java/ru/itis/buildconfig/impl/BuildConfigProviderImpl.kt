package ru.itis.buildconfig.impl

import ru.itis.buildconfig.api.BuildConfigProvider

class BuildConfigProviderImpl: BuildConfigProvider {

    override fun getOMDbApiBaseUrl(): String = BuildConfig.OMDB_API_BASE_URL

    override fun getOMDbApiKey(): String = BuildConfig.OMDB_API_KEY
}
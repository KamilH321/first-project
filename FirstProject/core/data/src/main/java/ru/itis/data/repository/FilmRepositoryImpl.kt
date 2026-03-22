package ru.itis.data.repository

import ru.itis.buildconfig.api.BuildConfigProvider
import ru.itis.data.mapper.FilmModelMapper
import ru.itis.domain.model.FilmModel
import ru.itis.domain.repository.FilmRepository
import ru.itis.network.OMDbApi

class FilmRepositoryImpl(
    private val omdbApi: OMDbApi,
    private val filmModelMapper: FilmModelMapper,
    private val buildConfigProvider: BuildConfigProvider
): FilmRepository {

    private val API_KEY = buildConfigProvider.getOMDbApiKey()

    override suspend fun searchByQuery(query: String): List<FilmModel> {
        val response = omdbApi.getDataByQuery(
            apiKey = API_KEY,
            query = query
        )
        return filmModelMapper.map(response)
    }
}
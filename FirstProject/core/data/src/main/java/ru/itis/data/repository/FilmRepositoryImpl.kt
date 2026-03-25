package ru.itis.data.repository

import ru.itis.buildconfig.api.BuildConfigProvider
import ru.itis.data.mapper.FilmModelMapper
import ru.itis.domain.model.FilmModel
import ru.itis.domain.model.FullInfoFilmModel
import ru.itis.domain.repository.FilmRepository
import ru.itis.network.OMDbApi

class FilmRepositoryImpl(
    private val omdbApi: OMDbApi,
    private val filmModelMapper: FilmModelMapper
): FilmRepository {

    override suspend fun searchByQuery(query: String): List<FilmModel> {
        val response = omdbApi.getDataByQuery(query = query)
        return filmModelMapper.map(response)
    }

    override suspend fun searchById(query: String): FullInfoFilmModel {
        val response = omdbApi.getFilmById(query)
        return filmModelMapper.map(response)
    }
}
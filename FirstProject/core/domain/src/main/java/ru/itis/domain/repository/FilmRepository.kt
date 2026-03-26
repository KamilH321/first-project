package ru.itis.domain.repository

import ru.itis.domain.model.FilmModel
import ru.itis.domain.model.FullInfoFilmModel

interface FilmRepository {

    suspend fun searchByQuery(query: String): List<FilmModel>

    suspend fun searchById(query: String): FullInfoFilmModel
}
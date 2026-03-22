package ru.itis.domain.repository

import ru.itis.domain.model.FilmModel

interface FilmRepository {

    suspend fun searchByQuery(query: String): List<FilmModel>
}
package com.example.firstproject.repository

import com.example.firstproject.di.DatabaseService
import com.example.firstproject.mapper.FilmModelMapper
import com.example.firstproject.models.FilmDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FilmRepository(
    private val mapper: FilmModelMapper,
    private val ioDispatcher: CoroutineDispatcher
) {
    private val filmDao = lazy { DatabaseService.getDatabase().filmDao }

    suspend fun createNewFilm(filmData: FilmDataModel){
        withContext(ioDispatcher){
            val entity = mapper.map(filmData)
            filmDao.value.putFilmData(entity)
        }
    }

    suspend fun getFilmsByUser(userId: Int) : List<FilmDataModel> =
        withContext(ioDispatcher) {
            filmDao.value.getFilmsDataByUserId(userId).map { filmEntity -> mapper.map(filmEntity) }
        }

    suspend fun isFilmExists(title: String, userId: Int): Boolean =
        withContext(ioDispatcher) {
            filmDao.value.isFilmExists(title, userId) > 0
        }

    suspend fun getFilms() : List<FilmDataModel> =
        withContext(ioDispatcher) {
            filmDao.value.getFilmsData().map { filmEntity -> mapper.map(filmEntity) }
        }
}
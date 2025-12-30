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
}
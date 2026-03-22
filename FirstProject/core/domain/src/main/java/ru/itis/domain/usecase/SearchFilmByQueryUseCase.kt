package ru.itis.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.domain.model.FilmModel
import ru.itis.domain.repository.FilmRepository

class SearchFilmByQueryUseCase(private val filmRepository: FilmRepository) {

    suspend operator fun invoke(query: String): List<FilmModel> {
        return withContext(Dispatchers.IO){
            filmRepository.searchByQuery(query)
        }
    }
}
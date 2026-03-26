package ru.itis.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.domain.model.FilmModel
import ru.itis.domain.model.FullInfoFilmModel
import ru.itis.domain.repository.FilmRepository

class SearchFilmByIdUseCase(private val filmRepository: FilmRepository) {

    suspend operator fun invoke(query: String): FullInfoFilmModel {
        return withContext(Dispatchers.IO){
            filmRepository.searchById(query)
        }
    }
}
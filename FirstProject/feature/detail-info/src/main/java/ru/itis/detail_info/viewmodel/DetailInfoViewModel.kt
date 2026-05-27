package ru.itis.detail_info.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.itis.api.HttpExceptionHandler
import ru.itis.domain.model.FullInfoFilmModel
import ru.itis.domain.usecase.SearchFilmByIdUseCase
import ru.itis.utils.runCatching
import javax.inject.Inject

class DetailInfoViewModel @AssistedInject constructor(
    private val httpExceptionHandler: HttpExceptionHandler,
    private val searchFilmByIdUseCase: SearchFilmByIdUseCase,
    @Assisted private val filmId: String
): ViewModel() {

    private val _film = MutableStateFlow<FullInfoFilmModel?>(null)
    val film: StateFlow<FullInfoFilmModel?> = _film

    init {
        getFilm()
    }

    fun getFilm(){
        viewModelScope.launch {
            runCatching(httpExceptionHandler) {
                searchFilmByIdUseCase(filmId)
            }.onSuccess {
                _film.value = it
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(filmId: String): DetailInfoViewModel
    }
}
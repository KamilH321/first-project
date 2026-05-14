package ru.itis.detail_info.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.itis.api.HttpExceptionHandler
import ru.itis.domain.model.FullInfoFilmModel
import ru.itis.domain.usecase.SearchFilmByIdUseCase
import ru.itis.utils.runCatching
import javax.inject.Inject

class DetailInfoViewModel @Inject constructor(
    private val httpExceptionHandler: HttpExceptionHandler,
    private val searchFilmByIdUseCase: SearchFilmByIdUseCase
): ViewModel() {

    private val _film = MutableStateFlow<FullInfoFilmModel?>(null)
    val film: StateFlow<FullInfoFilmModel?> = _film

    fun getFilm(query: String){
        viewModelScope.launch {
            runCatching(httpExceptionHandler) {
                searchFilmByIdUseCase(query)
            }.onSuccess {
                _film.value = it
            }
        }
    }
}
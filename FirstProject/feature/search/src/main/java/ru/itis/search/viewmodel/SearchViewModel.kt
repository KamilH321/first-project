package ru.itis.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import ru.itis.api.HttpExceptionHandler
import ru.itis.domain.model.FilmModel
import ru.itis.domain.usecase.SearchFilmByQueryUseCase
import ru.itis.utils.runCatching
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val httpExceptionHandler: HttpExceptionHandler,
    private val searchFilmByQueryUseCase: SearchFilmByQueryUseCase
): ViewModel() {

    private val _filmList = MutableStateFlow<List<FilmModel>>(listOf())
    val filmList: StateFlow<List<FilmModel>> = _filmList.asStateFlow()

    fun getFilmList(query: String) {
        viewModelScope.launch {
            runCatching(httpExceptionHandler) {
                searchFilmByQueryUseCase(query)
            }.onSuccess {
                _filmList.value = it.toImmutableList()
            }
        }
    }
}
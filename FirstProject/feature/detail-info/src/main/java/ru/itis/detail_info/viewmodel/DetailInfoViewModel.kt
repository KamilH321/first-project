package ru.itis.detail_info.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.itis.api.HttpExceptionHandler
import ru.itis.di.ServiceLocator
import ru.itis.domain.model.FullInfoFilmModel
import ru.itis.domain.usecase.SearchFilmByIdUseCase
import ru.itis.utils.runCatching
import kotlin.reflect.KClass

class DetailInfoViewModel(
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

    companion object {
        val Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: KClass<T>,
                extras: CreationExtras
            ): T {
                val handler = ServiceLocator.getHttpExceptionHandler()
                val useCase = SearchFilmByIdUseCase(filmRepository = ServiceLocator.getFilmRepository())

                return DetailInfoViewModel(
                    httpExceptionHandler = handler,
                    searchFilmByIdUseCase = useCase
                ) as T
            }
        }
    }
}
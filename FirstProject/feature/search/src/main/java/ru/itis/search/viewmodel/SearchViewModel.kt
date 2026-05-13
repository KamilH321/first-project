    package ru.itis.search.viewmodel

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.ViewModelProvider
    import androidx.lifecycle.viewModelScope
    import androidx.lifecycle.viewmodel.CreationExtras
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.launch
    import ru.itis.api.HttpExceptionHandler
    import ru.itis.di.ServiceLocator
    import ru.itis.domain.model.FilmModel
    import ru.itis.domain.usecase.SearchFilmByQueryUseCase
    import ru.itis.utils.runCatching
    import kotlin.reflect.KClass

    class SearchViewModel(
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
                    _filmList.value = it
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
                    val useCase = SearchFilmByQueryUseCase(filmRepository = ServiceLocator.getFilmRepository())

                    return SearchViewModel(
                        httpExceptionHandler = handler,
                        searchFilmByQueryUseCase = useCase
                    ) as T
                }
            }
        }
    }
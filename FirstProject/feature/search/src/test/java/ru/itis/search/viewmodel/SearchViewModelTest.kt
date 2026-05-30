package ru.itis.search.viewmodel

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.itis.api.HttpExceptionHandler
import ru.itis.domain.model.FilmModel
import ru.itis.domain.usecase.SearchFilmByQueryUseCase
import ru.itis.test.MainDispatcherRule

class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val searchFilmByQueryUseCase: SearchFilmByQueryUseCase = mockk()
    val httpExceptionHandler: HttpExceptionHandler = mockk(relaxed = true)

    lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        viewModel = SearchViewModel(httpExceptionHandler, searchFilmByQueryUseCase)
    }

    @Test
    fun `Проверка_на_успешное_обновление_списка_фильмов`() = runTest {

        val query = "Fight Club"
        val expectedFilms = listOf<FilmModel>(
            mockk {
                every { id } returns "1"
                every { title } returns "Fight Club"
            },
            mockk {
                every { id } returns "2"
                every { title } returns "Interstellar"
            }
        )

        coEvery { searchFilmByQueryUseCase(query) } returns expectedFilms

        viewModel.getFilmList(query)

        advanceUntilIdle()

        assertEquals(expectedFilms, viewModel.filmList.value)
    }

    @Test
    fun `Проверка_на_успешное_обновление_списка_фильмов_с_пустым_результатом`() = runTest {

        val query = "Some Movie"

        coEvery { searchFilmByQueryUseCase(query) } returns emptyList()

        viewModel.getFilmList(query)

        advanceUntilIdle()

        assertTrue(viewModel.filmList.value.isEmpty())
    }
}
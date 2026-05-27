package ru.itis.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.itis.domain.model.FilmModel
import ru.itis.domain.repository.FilmRepository
import ru.itis.test.MainDispatcherRule

class SearchFilmByQueryUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val filmRepository: FilmRepository = mockk()
    lateinit var useCase: SearchFilmByQueryUseCase

    @Before
    fun setUp() {
        useCase = SearchFilmByQueryUseCase(filmRepository)
    }

    @Test
    fun `Получение_списка_фильмов_по_запросу`() = runTest{

        val query = "Inception"

        val expectedFilms = listOf<FilmModel>(
            mockk {
                every { id } returns "1"
                every { title } returns "Inception"
            },
            mockk {
                every { id } returns "2"
                every { title } returns "Interstellar"
            }
        )

        coEvery { filmRepository.searchByQuery(query) } returns expectedFilms

        val result = useCase(query)

        assertEquals(expectedFilms, result)
        coVerify(exactly = 1) { filmRepository.searchByQuery(query) }
    }
}
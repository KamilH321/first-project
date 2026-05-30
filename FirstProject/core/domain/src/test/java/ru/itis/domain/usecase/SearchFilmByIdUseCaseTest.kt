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
import ru.itis.domain.model.FullInfoFilmModel
import ru.itis.domain.repository.FilmRepository
import ru.itis.test.MainDispatcherRule


class SearchFilmByIdUseCaseTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val filmRepository: FilmRepository = mockk()
    lateinit var useCase: SearchFilmByIdUseCase

    @Before
    fun setUp() {
        useCase = SearchFilmByIdUseCase(filmRepository)
    }

    @Test
    fun `Получение_списка_фильма_по_идентификатору`() = runTest{

        val filmId = "42"

        val expectedFilm = mockk<FullInfoFilmModel> {
            every { id } returns "42"
            every { title } returns "The Matrix"
        }

        coEvery { filmRepository.searchById(filmId) } returns expectedFilm

        val result = useCase(filmId)

        assertEquals(expectedFilm, result)
        coVerify(exactly = 1) { filmRepository.searchById(filmId) }
    }
}
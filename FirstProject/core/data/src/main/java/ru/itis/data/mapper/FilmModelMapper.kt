package ru.itis.data.mapper

import ru.itis.domain.model.FilmModel
import ru.itis.domain.model.FullInfoFilmModel
import ru.itis.network.pojo.OMDbFullResponseById
import ru.itis.network.pojo.OMDbResponse
import kotlin.String

class FilmModelMapper {

    fun map(input: OMDbResponse): List<FilmModel> {
        val filmList = input.search
        return filmList.map { filmInfo ->
            FilmModel(
                title = filmInfo.title,
                year = filmInfo.year,
                type = filmInfo.type,
                id = filmInfo.id,
                poster = filmInfo.poster,
            )
        }
    }

    fun map(input: OMDbFullResponseById): FullInfoFilmModel {
        with(input) {
            return FullInfoFilmModel(
                title = title,
                year = year,
                released = released,
                runtime = runtime,
                genre = genre,
                director = director,
                writer = writer,
                actors = actors,
                plot = plot,
                language = language,
                country = country,
                imdbRating = imdbRating,
                type = type,
                id = id,
                poster = poster
            )
        }
    }
}
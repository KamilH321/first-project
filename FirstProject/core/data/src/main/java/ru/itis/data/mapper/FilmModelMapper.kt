package ru.itis.data.mapper

import ru.itis.data.model.FilmDataModel
import ru.itis.domain.model.FilmModel
import ru.itis.network.pojo.FilmInfo
import ru.itis.network.pojo.OMDbResponse

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
}
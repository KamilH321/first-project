package com.example.firstproject.mapper

import com.example.firstproject.db.entity.FilmEntity
import com.example.firstproject.db.entity.UserEntity
import com.example.firstproject.models.FilmDataModel
import com.example.firstproject.models.UserDataModel

class FilmModelMapper {

    fun map(input: FilmDataModel): FilmEntity {
        with(input) {
            return FilmEntity(
                title = title,
                description = description,
                releaseYear = releaseYear,
                genre = genre,
                country = country,
                imageUrl = imageUrl,
                userId = userId
            )
        }
    }

    fun map(input: FilmEntity): FilmDataModel {
        with(input) {
            return FilmDataModel(
                title = title,
                description = description,
                releaseYear = releaseYear,
                genre = genre,
                country = country,
                imageUrl = imageUrl,
                userId = userId
            )
        }
    }
}
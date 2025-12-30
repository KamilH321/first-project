package com.example.firstproject.models

data class FilmDataModel(
    val title: String,
    val description: String,
    val releaseYear: Int,
    val genre: String,
    val country: String,
    val imageUrl: String,
    val userId: Int
)

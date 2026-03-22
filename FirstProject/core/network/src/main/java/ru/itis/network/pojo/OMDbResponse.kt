package ru.itis.network.pojo

import com.google.gson.annotations.SerializedName

data class OMDbResponse (
    @SerializedName(value = "Search")
    val search: List<FilmInfo>
)

data class FilmInfo (
    @SerializedName(value = "Title")
    val title: String,
    @SerializedName(value = "Year")
    val year: String,
    @SerializedName(value = "Type")
    val type: String,
    @SerializedName(value = "imdbID")
    val id: String,
    @SerializedName(value = "Poster")
    val poster: String
)

/*
Title": "Drive",
"Year": "2011",
"imdbID": "tt0780504",
"Type": "movie",
"Poster": "https://m.media-amazon.com/images/M/MV5BYTFmNTFlOTAtNzEyNi00MWU2LTg3MGEtYjA2NWY3MDliNjlkXkEyXkFqcGc@._V1_SX300.jpg"
*/
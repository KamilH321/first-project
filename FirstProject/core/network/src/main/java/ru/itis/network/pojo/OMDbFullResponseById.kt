package ru.itis.network.pojo

import com.google.gson.annotations.SerializedName

data class OMDbFullResponseById (
    @SerializedName(value = "Title")
    val title: String,
    @SerializedName(value = "Year")
    val year: String,
    @SerializedName(value = "Released")
    val released: String,
    @SerializedName(value = "Runtime")
    val runtime: String,
    @SerializedName(value = "Genre")
    val genre: String,
    @SerializedName(value = "Director")
    val director: String,
    @SerializedName(value = "Writer")
    val writer: String,
    @SerializedName(value = "Actors")
    val actors: String,
    @SerializedName(value = "Plot")
    val plot: String,
    @SerializedName(value = "Language")
    val language: String,
    @SerializedName(value = "Country")
    val country: String,
    @SerializedName(value = "imdbRating")
    val imdbRating: String,
    @SerializedName(value = "Type")
    val type: String,
    @SerializedName(value = "imdbID")
    val id: String,
    @SerializedName(value = "Poster")
    val poster: String

)


/*
{
    "Title": "Drive",
    "Year": "2011",
    "Rated": "R",
    "Released": "16 Sep 2011",
    "Runtime": "100 min",
    "Genre": "Action, Drama",
    "Director": "Nicolas Winding Refn",
    "Writer": "Hossein Amini, James Sallis",
    "Actors": "Ryan Gosling, Carey Mulligan, Bryan Cranston",
    "Plot": "A mysterious Hollywood action film stuntman gets in trouble with gangsters when he tries to help his neighbor's husband rob a pawn shop while serving as his getaway driver.",
    "Language": "English, Spanish",
    "Country": "United States",
    "Awards": "Nominated for 1 Oscar. 79 wins & 180 nominations total",
    "Poster": "https://m.media-amazon.com/images/M/MV5BYTFmNTFlOTAtNzEyNi00MWU2LTg3MGEtYjA2NWY3MDliNjlkXkEyXkFqcGc@._V1_SX300.jpg",
    "Ratings": [
        {
            "Source": "Internet Movie Database",
            "Value": "7.8/10"
        },
        {
            "Source": "Rotten Tomatoes",
            "Value": "93%"
        },
        {
            "Source": "Metacritic",
            "Value": "79/100"
        }
    ],
    "Metascore": "79",
    "imdbRating": "7.8",
    "imdbVotes": "754,976",
    "imdbID": "tt0780504",
    "Type": "movie",
    "DVD": "N/A",
    "BoxOffice": "$35,061,555",
    "Production": "N/A",
    "Website": "N/A",
    "Response": "True"
*/
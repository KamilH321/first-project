package com.example.firstproject.navigation

import kotlinx.serialization.Serializable

@Serializable
data object LoginScreenObject

@Serializable
data object RegisterScreenObject

@Serializable
data object HomeScreenObject

@Serializable
data class AddFilmScreenObject(val userId: Int)

@Serializable
data object ProfileScreenObject


package com.example.firstproject.navigation

import kotlinx.serialization.Serializable

@Serializable
data object LoginPageDataObject

@Serializable
data class NotesPageDataObject(
    val email: String,
    val password : String
)

@Serializable
data object AddNotePageDataObject


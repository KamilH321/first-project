package ru.itis.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object Search: NavKey

@Serializable
data class CommonInfo(val filmId: String = ""): NavKey
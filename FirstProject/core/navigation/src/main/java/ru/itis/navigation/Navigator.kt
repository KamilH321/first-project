package ru.itis.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey

interface Navigator {

    fun navigate(route: NavKey)

    fun popEntry()
}
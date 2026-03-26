package com.example.firstproject.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.NavKey
import ru.itis.navigation.CommonInfo
import ru.itis.navigation.Navigator
import ru.itis.navigation.Search

class NavigatorImpl: Navigator {

    private val backstack =  mutableStateListOf<Any>()

    init {
        backstack.add(Search)
    }

    override fun navigate(route: NavKey) {
        backstack.add(route)
    }

    override fun popEntry() {
        backstack.removeLastOrNull()
    }

    fun getBackStack() = backstack
}

package com.example.firstproject.navigation.di

import com.example.firstproject.navigation.NavigatorImpl
import dagger.Binds
import dagger.Module
import ru.itis.navigation.Navigator
import javax.inject.Singleton

@Module
interface NavigationModule {

    @Binds
    @Singleton
    fun bindNavigator(impl: NavigatorImpl): Navigator
}
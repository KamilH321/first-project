package ru.itis.data.di

import dagger.Binds
import dagger.Module
import ru.itis.data.repository.FilmRepositoryImpl
import ru.itis.domain.repository.FilmRepository
import javax.inject.Singleton

@Module
interface DataModule {

    @Binds
    @Singleton
    fun bindFilmRepositoryImpl(impl: FilmRepositoryImpl) : FilmRepository
}
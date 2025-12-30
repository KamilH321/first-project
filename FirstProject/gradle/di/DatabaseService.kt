package com.example.firstproject.di

import android.content.Context
import androidx.room.Room
import com.example.firstproject.db.AppDatabase
import com.example.firstproject.mapper.FilmModelMapper
import com.example.firstproject.mapper.UserModelMapper
import com.example.firstproject.repository.FilmRepository
import com.example.firstproject.repository.UserRepository
import kotlinx.coroutines.Dispatchers

object DatabaseService {

    private const val DB_NAME = "films_catalog.db"

    private var appDatabase: AppDatabase? = null

    private val userModelMapper = UserModelMapper()

    private val filmModelMapper = FilmModelMapper()

    private val userRepository = UserRepository(
        mapper = userModelMapper,
        ioDispatcher = Dispatchers.IO
    )

    private val filmRepository = FilmRepository(
        mapper = filmModelMapper,
        ioDispatcher = Dispatchers.IO

    )

    fun initDatabase(appCtx: Context){
        appDatabase = Room.databaseBuilder(appCtx, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration(true)
            .build()
    }

    fun getDatabase(): AppDatabase = appDatabase ?: throw IllegalStateException("Db is not initialized")

    fun getUserRepository(): UserRepository = userRepository

    fun getFilmRepository(): FilmRepository = filmRepository
}
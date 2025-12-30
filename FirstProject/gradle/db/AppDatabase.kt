package com.example.firstproject.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.firstproject.db.dao.FilmDao
import com.example.firstproject.db.dao.UserDao
import com.example.firstproject.db.entity.FilmEntity
import com.example.firstproject.db.entity.UserEntity

@Database(
    entities = [UserEntity::class, FilmEntity::class],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase(){

    abstract val userDao: UserDao

    abstract val filmDao: FilmDao
}

private const val DATABASE_VERSION = 1
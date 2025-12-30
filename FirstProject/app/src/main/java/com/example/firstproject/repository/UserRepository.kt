package com.example.firstproject.repository

import com.example.firstproject.db.entity.UserEntity
import com.example.firstproject.di.DatabaseService
import com.example.firstproject.mapper.UserModelMapper
import com.example.firstproject.models.UserDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRepository(
    private val mapper: UserModelMapper,
    private val ioDispatcher: CoroutineDispatcher
) {
    private val userDao = lazy { DatabaseService.getDatabase().userDao }

    suspend fun register(userData: UserDataModel) {
        withContext(ioDispatcher){
            val entity = mapper.map(userData)
            userDao.value.putUserData(entity)
        }
    }

    suspend fun login(email: String): UserEntity? =
        withContext(ioDispatcher) {
            userDao.value.getUserDataByEmail(email)
        }

    suspend fun getUserName(userId: Int) : String? =
        withContext(ioDispatcher){
            userDao.value.getUserNameById(userId)
        }
}
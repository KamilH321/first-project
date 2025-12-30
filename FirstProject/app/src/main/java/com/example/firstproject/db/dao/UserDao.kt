package com.example.firstproject.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.firstproject.db.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putUserData(user: UserEntity)

    @Query("SELECT id, name, email, password FROM users WHERE email = :userEmail")
    fun getUserDataByEmail(userEmail: String): UserEntity?

    @Query("SELECT name FROM users WHERE id = :userId")
    fun getUserNameById(userId: Int) : String?
}
package com.example.firstproject.mapper

import com.example.firstproject.db.entity.UserEntity
import com.example.firstproject.models.UserDataModel

class UserModelMapper {

    fun map(input: UserDataModel): UserEntity {
        with(input) {
            return UserEntity(
                name = name,
                email = email,
                password = password
            )
        }
    }

    fun map(input: UserEntity): UserDataModel {
        with(input) {
            return UserDataModel(
                name = name,
                email = email,
                password = password
            )
        }
    }
}
package ru.itis.impl

import retrofit2.HttpException
import ru.itis.api.HttpExceptionHandler

class HttpExceptionHandlerImpl(): HttpExceptionHandler {

    override fun handleException(ex: Throwable) {
        if (ex is HttpException){
            when (ex.code()) {
                404 -> {
                    println("Not found: ${ex.response()}")
                }
                401 -> {

                }
            }
        }
    }
}
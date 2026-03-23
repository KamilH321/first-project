package ru.itis.api

interface HttpExceptionHandler {

    fun handleException(ex: Throwable)
}
package ru.itis.impl

import android.content.Context
import android.widget.Toast
import retrofit2.HttpException
import ru.itis.api.HttpExceptionHandler
import ru.itis.utils.Constants

class HttpExceptionHandlerImpl(
    private val appContext: Context
): HttpExceptionHandler {

    override fun handleException(ex: Throwable) {
        if (ex is HttpException){
            when (ex.code()) {
                401 -> {
                    Toast.makeText(
                        appContext,
                        Constants.ERROR_401_TEXT,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
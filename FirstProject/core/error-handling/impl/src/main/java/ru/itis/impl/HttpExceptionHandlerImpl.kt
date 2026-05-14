package ru.itis.impl

import android.content.Context
import android.widget.Toast
import retrofit2.HttpException
import ru.itis.api.HttpExceptionHandler
import ru.itis.utils.Constants
import ru.itis.utils.context.ApplicationContext
import javax.inject.Inject

class HttpExceptionHandlerImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
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
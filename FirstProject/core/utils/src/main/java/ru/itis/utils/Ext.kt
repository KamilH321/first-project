package ru.itis.utils

import ru.itis.api.HttpExceptionHandler

inline fun <T, R> T.runCatching(
    httpExceptionHandler: HttpExceptionHandler,
    block: T.() -> R
): Result<R> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        httpExceptionHandler.handleException(ex = e)
        Result.failure(e)
    }
}
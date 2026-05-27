package ru.itis.impl

import dagger.Binds
import dagger.Module
import ru.itis.api.HttpExceptionHandler
import javax.inject.Singleton

@Module
interface HttpExceptionHandlerModule {

    @Binds
    @Singleton
    fun bindHttpExceptionHandlerToImpl(impl: HttpExceptionHandlerImpl): HttpExceptionHandler
}
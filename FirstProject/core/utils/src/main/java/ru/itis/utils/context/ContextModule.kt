package ru.itis.utils.context

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class ContextModule {

    @Provides
    @Singleton
    @ApplicationContext
    fun providesApplicationContext(application: Application): Context {
        return application
    }
}
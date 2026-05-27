package ru.itis.analytics.impl.di

import dagger.Binds
import dagger.Module
import ru.itis.analytics.api.Analytics
import ru.itis.analytics.impl.AnalyticsImpl
import javax.inject.Singleton

@Module
interface AnalyticsModule {

    @Binds
    @Singleton
    fun bindAnalyticsToImpl(impl: AnalyticsImpl): Analytics


}
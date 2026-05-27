package ru.itis.buildconfig.impl.di

import dagger.Binds
import dagger.Module
import ru.itis.buildconfig.api.BuildConfigProvider
import ru.itis.buildconfig.impl.BuildConfigProviderImpl

@Module
interface BuildConfigProviderModule {

    @Binds
    fun bindBuildConfigProviderToImpl(impl : BuildConfigProviderImpl): BuildConfigProvider
}
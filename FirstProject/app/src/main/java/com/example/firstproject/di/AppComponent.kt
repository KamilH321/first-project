package com.example.firstproject.di

import android.app.Application
import com.example.firstproject.MainActivity
import dagger.BindsInstance
import dagger.Component
import ru.itis.analytics.impl.di.AnalyticsModule
import ru.itis.buildconfig.impl.di.BuildConfigProviderModule
import ru.itis.data.di.DataModule
import ru.itis.detail_info.di.DetailInfoViewModelModule
import ru.itis.detail_info.viewmodel.DetailInfoViewModel
import ru.itis.di.ViewModelFactoryModule
import ru.itis.impl.HttpExceptionHandlerModule
import ru.itis.network.di.NetworkModule
import ru.itis.search.di.SearchViewModelModule
import ru.itis.utils.context.ContextModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        BuildConfigProviderModule::class,
        DataModule::class,
        HttpExceptionHandlerModule::class,
        ContextModule::class,
        SearchViewModelModule::class,
        ViewModelFactoryModule::class,
        AnalyticsModule::class
    ]

)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
    fun inject(activity: MainActivity)

    fun detailInfoViewModelFactory(): DetailInfoViewModel.Factory
}
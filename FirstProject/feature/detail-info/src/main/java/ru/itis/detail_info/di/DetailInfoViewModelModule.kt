package ru.itis.detail_info.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.itis.detail_info.viewmodel.DetailInfoViewModel
import ru.itis.di.ViewModelKey

@Module
interface DetailInfoViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailInfoViewModel::class)
    fun bindAuthViewModel(viewModel: DetailInfoViewModel): ViewModel
}
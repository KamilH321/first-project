package ru.itis.search.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.itis.di.ViewModelKey
import ru.itis.search.viewmodel.SearchViewModel

@Module
interface SearchViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindSearchViewModel(viewModelModule: SearchViewModel): ViewModel
}
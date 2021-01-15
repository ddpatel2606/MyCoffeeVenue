package com.dixitpatel.mycoffeevenue.ui.main

import androidx.lifecycle.ViewModelProvider
import com.dixitpatel.mycoffeevenue.network.ApiInterface
import com.dixitpatel.mycoffeevenue.repository.MainViewRepository
import com.dixitpatel.mycoffeevenue.repository.Repository
import com.dixitpatel.mycoffeevenue.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

/**
 *  Main Activity Module : MainActivity bind with ViewModel
 */
@Module
class MainActivityModule {

    @Provides
    fun providesMainViewRepository() : Repository {
        return MainViewRepository()
    }

    @Provides
    fun providesViewModel(apiInterface: ApiInterface, mainViewRepository: MainViewRepository): MainActivityViewModel {
        return MainActivityViewModel(apiInterface,mainViewRepository)
    }

    @Provides
    fun provideViewModelProvider(viewModel: MainActivityViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }

}


package com.zeynelerdi.pastryshop.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zeynelerdi.pastryshop.home.HomeViewModel
import com.zeynelerdi.pastryshop.pageDetail.DetailViewModel
import com.zeynelerdi.pastryshop.utils.DaggerViewModelFactory
import com.zeynelerdi.pastryshop.utils.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * Dagger [Module] to inject view models using dagger.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
@Module
internal abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    internal abstract fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel
}


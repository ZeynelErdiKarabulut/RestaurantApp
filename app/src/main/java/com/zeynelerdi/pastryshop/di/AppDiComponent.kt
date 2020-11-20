

package com.zeynelerdi.pastryshop.di

import com.zeynelerdi.pastryshop.SplashActivity
import com.zeynelerdi.pastryshop.home.HomeFragment
import com.zeynelerdi.pastryshop.pageDetail.DetailFragment
import com.zeynelerdi.pastryshop.repository.RepoDiModule
import com.zeynelerdi.pastryshop.utils.ApplicationScope
import dagger.Component

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * Dagger [Component] for whole application. This component provides repository, view model and other
 * root component dependencies.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
@ApplicationScope
@Component(
        dependencies = [RootComponent::class],
        modules = [ViewModelFactoryModule::class, RepoDiModule::class]
)
interface AppDiComponent {

    fun inject(homeFragment: HomeFragment)

    fun inject(detailFragment: DetailFragment)

    fun inject(splashActivity: SplashActivity)
}
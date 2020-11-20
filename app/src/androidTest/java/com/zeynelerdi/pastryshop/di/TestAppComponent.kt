package com.zeynelerdi.pastryshop.di

import com.zeynelerdi.pastryshop.home.HomeFragmentTest
import com.zeynelerdi.pastryshop.pageDetail.DetailFragmentTest
import com.zeynelerdi.pastryshop.repository.RepoDiModule
import com.zeynelerdi.pastryshop.utils.ApplicationScope
import dagger.Component

/**
 * Created by zeynelerdi on 04-Jun-18.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
@ApplicationScope
@Component(
        dependencies = [MockRootComponent::class],
        modules = [RepoDiModule::class]
)
interface TestAppComponent{

    fun inject(detailFragmentTest: DetailFragmentTest)
    fun inject(homeFragmentTest: HomeFragmentTest)
}



package com.zeynelerdi.pastryshop.di

import com.zeynelerdi.pastryshop.TestPSApplication
import com.zeynelerdi.pastryshop.repository.RepoDiModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
@Singleton
@Component(modules = [MockRootDiModule::class])
internal interface MockRootComponent : RootComponent {

    fun inject(testPSApplication: TestPSApplication)

    fun getTestPSApplication(): TestPSApplication
}

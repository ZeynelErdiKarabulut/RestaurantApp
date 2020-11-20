

package com.zeynelerdi.pastryshop.di

import android.app.Application
import android.content.Context
import com.zeynelerdi.pastryshop.PSApplication
import com.zeynelerdi.pastryshop.repository.db.PSDatabase
import com.zeynelerdi.pastryshop.utils.BaseApplication
import com.zeynelerdi.pastryshop.utils.SharedPrefsProvider
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * Dagger [Component] that injects dependencies provided by [RootDiModule] into [BaseApplication].
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 * @see RootDiModule
 */
@Singleton
@Component(modules = [RootDiModule::class])
internal interface RootComponent {

    fun inject(application: PSApplication)

    fun getContext(): Context

    fun getApplication(): Application

    fun geBaseApplication(): BaseApplication

    fun getSharedPrefsProvider(): SharedPrefsProvider

    fun getDatabase(): PSDatabase

    @Named(RootDiModule.BASE_URL)
    fun getBaseUrl(): String
}

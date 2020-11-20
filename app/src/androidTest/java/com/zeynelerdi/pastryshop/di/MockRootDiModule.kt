

package com.zeynelerdi.pastryshop.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.zeynelerdi.pastryshop.TestPSApplication
import com.zeynelerdi.pastryshop.repository.db.PSDatabase
import com.zeynelerdi.pastryshop.utils.BaseApplication
import com.zeynelerdi.pastryshop.utils.SharedPrefsProvider
import com.zeynelerdi.testutils.MockSharedPreference
import dagger.Module
import dagger.Provides
import java.io.IOException
import java.util.logging.Handler
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
@Module
internal class MockRootDiModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Singleton
    @Provides
    fun provideBaseApplication(): BaseApplication {
        return application as BaseApplication
    }

    @Singleton
    @Provides
    fun provideTestApplication(): TestPSApplication {
        return application as TestPSApplication
    }

    @Singleton
    @Provides
    fun provideSharedPreferenceProvider(): SharedPrefsProvider {

        // Create in memory shared preference
        return SharedPrefsProvider(MockSharedPreference())
    }

    @Singleton
    @Provides
    @Named(RootDiModule.BASE_URL)
    fun provideBaseUrl(application : TestPSApplication): String {
        return application.baseUrl
    }

    @Singleton
    @Provides
    fun provideDatabase(application: Application): PSDatabase {
        return Room.inMemoryDatabaseBuilder(
                application,
                PSDatabase::class.java
        ).build()
    }
}

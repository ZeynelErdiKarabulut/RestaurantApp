

package com.zeynelerdi.pastryshop.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.preference.PreferenceManager
import com.zeynelerdi.pastryshop.BuildConfig
import com.zeynelerdi.pastryshop.repository.db.PSDatabase
import com.zeynelerdi.pastryshop.utils.BaseApplication
import com.zeynelerdi.pastryshop.utils.SharedPrefsProvider
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * Dagger [Module] to provide application, database and network base url dependencies. In the test
 * environment you can mock this dependencies and inject mocks into the application.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
@Module
internal class RootDiModule(private val application: Application) {

    companion object {
        const val BASE_URL = "base_url"
    }

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
    fun provideSharedPreferenceProvider(context: Context): SharedPrefsProvider {
        return SharedPrefsProvider(PreferenceManager.getDefaultSharedPreferences(context))
    }

    /**
     * Room database .
     *
     * @see PSDatabase
     */
    @Singleton
    @Provides
    fun provideDatabase(application: Application): PSDatabase {
        return Room.databaseBuilder(
                application,
                PSDatabase::class.java,
                PSDatabase.DB_NAME
        ).build()
    }

    /**
     * Base url of the API endpoints.
     */
    @Singleton
    @Provides
    @Named(BASE_URL)
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }
}

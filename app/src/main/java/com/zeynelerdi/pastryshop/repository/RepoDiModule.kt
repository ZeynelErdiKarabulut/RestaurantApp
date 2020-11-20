

package com.zeynelerdi.pastryshop.repository

import com.zeynelerdi.pastryshop.BuildConfig
import com.zeynelerdi.pastryshop.di.RootDiModule
import com.zeynelerdi.pastryshop.repository.db.PSDatabase
import com.zeynelerdi.pastryshop.repository.db.PagesDao
import com.zeynelerdi.pastryshop.repository.network.Network
import com.zeynelerdi.pastryshop.utils.ApplicationScope
import com.zeynelerdi.pastryshop.utils.SharedPrefsProvider
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * Dagger [Module] to provide the dependencies such as [Repository, [PagesDao], [Network] etc.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
@Module
internal class RepoDiModule {

    companion object {
        const val ENABLE_LOG = "enable_log"
    }

    @Provides
    @ApplicationScope
    @Named(ENABLE_LOG)
    internal fun provideIsEnableLogging(): Boolean {
        return BuildConfig.BUILD_TYPE.contains("debug", true)
    }

    @Provides
    @ApplicationScope
    internal fun provideNetwork(
            @Named(RootDiModule.BASE_URL) baseUrl: String,
            @Named(ENABLE_LOG) enableLogging: Boolean
    ): Network {
        return Network(baseUrl, enableLogging)
    }

    @Provides
    @ApplicationScope
    fun providePagesDao(database: PSDatabase): PagesDao {
        return database.pagesDao()
    }

    @Provides
    @ApplicationScope
    fun provideRepository(
            network: Network,
            pagesDao: PagesDao,
            sharedPrefsProvider: SharedPrefsProvider
    ): Repository {
        return RepositoryImpl(network, pagesDao, sharedPrefsProvider)
    }
}



package com.zeynelerdi.pastryshop.repository

import com.zeynelerdi.pastryshop.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Zeynel Erdi Karabulut on 02/06/18.
 *
 * @author [zeynelerdi](https://github.com/ZeynelErdiKarabulut)
 */
@RunWith(JUnit4::class)
class RepoDiModuleTest {

    @Test
    fun checkEnableLogging() {
        assertEquals(BuildConfig.BUILD_TYPE == "debug", RepoDiModule().provideIsEnableLogging())
    }

    @Test
    fun checkProvideNetwork() {
        val testBaseUrl = "http://example.com/"
        val network = RepoDiModule().provideNetwork(testBaseUrl, true)

        // Validate the base url
        assertEquals(testBaseUrl, network.getRetrofitClient().baseUrl().toString())

        // Validate the logging flag
        val interceptors = network.okHttpClient.interceptors()
        Assert.assertEquals(1, interceptors.size)
        Assert.assertTrue(interceptors[0] is HttpLoggingInterceptor)
        Assert.assertEquals(HttpLoggingInterceptor.Level.BODY, (interceptors[0] as HttpLoggingInterceptor).level)
    }
}
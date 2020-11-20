

package com.zeynelerdi.pastryshop.repository.network

import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert
import org.junit.Test
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * Created by Zeynel Erdi Karabulut on 02/06/18.
 *
 * @author [zeynelerdi](https://github.com/ZeynelErdiKarabulut)
 */
class NetworkTest {

    private val testBaseUrl = "http://example.com/"

    @Test
    @Throws(IOException::class)
    fun checkReadTimeOut() {
        val okHttpClient = Network(testBaseUrl, true).okHttpClient
        Assert.assertEquals(NetworkConfig.READ_TIMEOUT * 60 * 1000, okHttpClient.readTimeoutMillis().toLong())
    }

    @Test
    @Throws(IOException::class)
    fun checkWriteTimeOut() {
        val okHttpClient = Network(testBaseUrl, true).okHttpClient
        Assert.assertEquals(NetworkConfig.WRITE_TIMEOUT * 60 * 1000, okHttpClient.writeTimeoutMillis().toLong())
    }

    @Test
    @Throws(IOException::class)
    fun checkConnectionTimeOut() {
        val okHttpClient = Network(testBaseUrl, true).okHttpClient
        Assert.assertEquals(NetworkConfig.READ_TIMEOUT * 60 * 1000, okHttpClient.readTimeoutMillis().toLong())
    }

    @Test
    @Throws(IOException::class)
    fun checkLoggingEnable() {
        val interceptors = Network(testBaseUrl, true).okHttpClient.interceptors()

        Assert.assertEquals(1, interceptors.size)
        Assert.assertTrue(interceptors[0] is HttpLoggingInterceptor)
        Assert.assertEquals(HttpLoggingInterceptor.Level.BODY, (interceptors[0] as HttpLoggingInterceptor).level)
    }

    @Test
    @Throws(IOException::class)
    fun checkLoggingDisable() {
        val okHttpClient = Network(testBaseUrl, false).okHttpClient
        Assert.assertTrue(okHttpClient.interceptors().isEmpty())
    }

    @Test
    @Throws(IOException::class)
    fun checkBaseUrl() {
        val retrofit = Network(testBaseUrl, false).getRetrofitClient()
        Assert.assertEquals(testBaseUrl, retrofit.baseUrl().toString())
    }

    @Test
    @Throws(IOException::class)
    fun checkCallAdapterFactories() {
        val callAdapterFactories = Network(testBaseUrl, false)
                .getRetrofitClient().callAdapterFactories()

        Assert.assertEquals(2, callAdapterFactories.size)

        var isRxCallAdapterAdded = false
        callAdapterFactories.forEach {
            if (it is RxJava2CallAdapterFactory) isRxCallAdapterAdded = true
        }
        Assert.assertTrue(isRxCallAdapterAdded)
    }

    @Test
    @Throws(IOException::class)
    fun checkConverterFactory() {
        val converterFactories = Network(testBaseUrl, false)
                .getRetrofitClient().converterFactories()

        Assert.assertEquals(2, converterFactories.size)

        var isGsonConverterAdded = false
        converterFactories.forEach {
            if (it is GsonConverterFactory) isGsonConverterAdded = true
        }
        Assert.assertTrue(isGsonConverterAdded)
    }
}
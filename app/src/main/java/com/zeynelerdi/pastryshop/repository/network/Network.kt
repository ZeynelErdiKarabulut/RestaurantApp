

package com.zeynelerdi.pastryshop.repository.network

import android.support.annotation.VisibleForTesting
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * Class to provide the [Retrofit] client with the given [baseUrl].
 *
 * @param enableLog If you want to turn on the request/response logging set this to true.
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
class Network(private val baseUrl: String, enableLog: Boolean) {

    /**
     * [Gson] instance to parse json responses.
     *
     * @see gsonConverterFactory
     */
    private val sGson: Gson = GsonBuilder()
            .setLenient()
            .create()

    /**
     * OkHttp instance. New instances will be shallow copy of this instance.
     *
     * @see .getOkHttpClientBuilder
     */
    @VisibleForTesting
    internal val okHttpClient: OkHttpClient

    /**
     * [RxJava2CallAdapterFactory] for converting responses to Rx observables.
     */
    private val rxCallAdapterFactory = RxJava2CallAdapterFactory.create()

    /**
     * [GsonConverterFactory] to parse json responses using [sGson].
     *
     * @see sGson
     */
    private val gsonConverterFactory = GsonConverterFactory.create(sGson)

    init {
        val httpClientBuilder = OkHttpClient.Builder()
                .readTimeout(NetworkConfig.READ_TIMEOUT, TimeUnit.MINUTES)
                .writeTimeout(NetworkConfig.WRITE_TIMEOUT, TimeUnit.MINUTES)
                .connectTimeout(NetworkConfig.CONNECTION_TIMEOUT, TimeUnit.MINUTES)

        //Add debug interceptors
        if (enableLog) {
            httpClientBuilder.addInterceptor(HttpLoggingInterceptor()
                    .apply { level = HttpLoggingInterceptor.Level.BODY }
            )
        }

        okHttpClient = httpClientBuilder.build()
    }

    /**
     * Get the retrofit client instance for given [baseUrl].
     */
    fun getRetrofitClient(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(rxCallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .build()
    }

}


package com.zeynelerdi.pastryshop.repository.network

import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * List of all the server endpoints.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
internal interface Endpoint {

    @GET("app_data.json")
    fun getData(): Single<GetDataResponse>
}
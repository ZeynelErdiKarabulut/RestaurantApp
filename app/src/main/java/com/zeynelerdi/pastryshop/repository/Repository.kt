

package com.zeynelerdi.pastryshop.repository

import com.zeynelerdi.pastryshop.bin.Contact
import com.zeynelerdi.pastryshop.bin.Pages
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * Protocol that defines
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
interface Repository {

    /**
     * Get the list of all [Pages] to display in the view.
     *
     * @return [Observable] of list of [Pages]. Once the subscriber subscribes to this [Observable],
     * it will keep emitting whenever there are any changes into the data until the stream gets dispose.
     */
    fun getPages(): Observable<ArrayList<Pages>>

    /**
     * Get the details of the [Pages] with [pageId].
     *
     * @return [Observable] of [Pages]. Once the subscriber subscribes to this [Observable],
     * it will keep emitting whenever there are any changes into the data until the stream gets dispose.
     */
    fun getPage(pageId: Long): Observable<Pages>

    /**
     * Get the [Contact] information.
     *
     * @return [Single] of [Contact].
     */
    fun getContactInfo(): Single<Contact>

    /**
     * Call the server and refresh all the data into the cache.
     *
     * @return [Single] of list of [Pages].
     */
    fun refreshData(): Single<ArrayList<Pages>>
}
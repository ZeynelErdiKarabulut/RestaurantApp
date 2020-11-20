

package com.zeynelerdi.pastryshop.repository

import com.zeynelerdi.pastryshop.bin.Contact
import com.zeynelerdi.pastryshop.bin.Pages
import com.zeynelerdi.pastryshop.repository.db.PagesDao
import com.zeynelerdi.pastryshop.repository.network.Endpoint
import com.zeynelerdi.pastryshop.repository.network.Network
import com.zeynelerdi.pastryshop.utils.SharedPrefsProvider
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * Concrete implementation of the [Repository]. This class provides required data to other layer
 * of the application (such as views and view models) by managing caches and network requests.
 *
 * @param network [Network] for  saving data into network
 * @param pagesDao [PagesDao] for saving data into database
 * @param sharedPrefsProvider [SharedPrefsProvider] for saving data into shared preferences
 * @see Repository
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
internal class RepositoryImpl(private val network: Network,
                              private val pagesDao: PagesDao,
                              private val sharedPrefsProvider: SharedPrefsProvider) : Repository {

    /**
     * Call the server and refresh all the data into the cache.
     *
     * @return [Single] of list of [Pages].
     */
    override fun refreshData(): Single<ArrayList<Pages>> {

        return network.getRetrofitClient()
                .create(Endpoint::class.java)
                .getData()  // Make network request
                .map {

                    // Save the contact to the database
                    sharedPrefsProvider.savePreferences(Contact.PREF_KEY_PHONE, it.contactInfo.phone)
                    sharedPrefsProvider.savePreferences(Contact.PREF_KEY_EMAIL, it.contactInfo.email)
                    sharedPrefsProvider.savePreferences(Contact.PREF_KEY_TWITTER, it.contactInfo.twitter)


                    // Convert the response into the list of pages to save in database.
                    // Mark the updated time to the current UNIX milliseconds.
                    val updateTime = System.currentTimeMillis()
                    val pages = ArrayList<Pages>()

                    it.pagesItem.map { it.toPage() }
                            .forEach {
                                it.updateMills = updateTime
                                pages.add(it)
                            }

                    //Save to each page into database
                    pages.forEach {

                        if (pagesDao.getPageFromId(it.id) == null) {

                            // There is no page for this id present into the db.
                            // Insert new raw.
                            pagesDao.insert(it)
                        } else {

                            // Information for this page is already present.
                            // Update the card raw.
                            pagesDao.update(it)
                        }
                    }

                    // Delete all the old pages which where not returned from the server in the response.
                    if (pages.isNotEmpty()) {
                        pagesDao.deleteNotUpdatedPages(pages[0].updateMills)
                    }
                    return@map pages
                }
    }

    /**
     * Get the list of [Pages] to display in the view. This will load the cached data from the
     * database and emits the new result every time there are any changes made to the database. If
     * there are not any cached data into the database, this method will retrieve the data from the
     * server and cache it to the database for future use.
     *
     * @return [Observable] of list of [Pages]. Once the subscriber subscribes to this [Observable],
     * it will keep emitting whenever there are any changes into the data until the stream gets dispose.
     */
    override fun getPages(): Observable<ArrayList<Pages>> {
        return pagesDao.getCount()
                .flatMapObservable {
                    // Check the number of items in the database.
                    if (it == 0) {

                        // If the number of items are 0, database is not synced before.
                        // Make a network call to load the list of pages from the server.
                        return@flatMapObservable refreshData().toObservable()
                                .flatMap {

                                    // Once the data synced successfully,
                                    // read all the pages from the database & start observing
                                    // database changes.
                                    pagesDao.getAllPages().toObservable()
                                }
                    }

                    // There are cached items into the database.
                    // Read all the pages from the database & start observing database changes.
                    return@flatMapObservable pagesDao.getAllPages().toObservable()
                }
                .map { it as ArrayList<Pages> } //Map list as array list. (Room doesn't support array list as output type)
    }

    /**
     * Get the details of the [Pages] with [pageId]. This will load the cached [Pages] data from the
     * database and emits the new result every time there are any changes made to the database. If
     * there isn't any cached data for the [pageId] into the database, this method will retrieve the
     * data from the server using [refreshData] and cache it to the database for future use.
     *
     * @return [Observable] of [Pages]. Once the subscriber subscribes to this [Observable],
     * it will keep emitting whenever there are any changes into the data until the stream gets dispose.
     */
    override fun getPage(pageId: Long): Observable<Pages> {
        return pagesDao.hasPage(pageId)
                .flatMapObservable {
                    // Check the number of items in the database.
                    if (it == 0) {

                        // If the number of items are 0, database is not synced before.
                        // Make a network call to load the list of pages from the server.
                        return@flatMapObservable refreshData().toObservable()
                                .flatMap {

                                    // Once the data synced successfully,
                                    // read all the pages from the database & start observing
                                    // database changes.
                                    pagesDao.observePage(pageId).toObservable()
                                }
                    }

                    // There are cached items into the database.
                    // Read all the pages from the database & start observing database changes.
                    return@flatMapObservable pagesDao.observePage(pageId).toObservable()
                }
    }

    /**
     * Get the [Contact] information.
     *
     * @return [Single] of [Contact].
     */
    override fun getContactInfo(): Single<Contact> {
        return Single.create {
            val phone = sharedPrefsProvider.getStringFromPreferences(Contact.PREF_KEY_PHONE, null)
            val email = sharedPrefsProvider.getStringFromPreferences(Contact.PREF_KEY_EMAIL, null)
            val twitter = sharedPrefsProvider.getStringFromPreferences(Contact.PREF_KEY_TWITTER, null)


            if (phone == null || email == null || twitter == null) {
                it.onError(Throwable("No contact information available."))
            } else {
                it.onSuccess(Contact(
                        phone = phone,
                        email = email,
                        twitter = twitter
                ))
            }
        }
    }
}
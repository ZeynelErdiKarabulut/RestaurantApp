

package com.zeynelerdi.pastryshop

import com.zeynelerdi.pastryshop.bin.Contact
import com.zeynelerdi.pastryshop.bin.Pages
import com.zeynelerdi.pastryshop.repository.db.PagesDao
import com.zeynelerdi.pastryshop.utils.SharedPrefsProvider
import javax.inject.Inject

/**
 * Created by Zeynel Erdi Karabulut on 03/06/18.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
class CacheDataManager @Inject constructor(
        private val pagesDao: PagesDao,
        private val sharedPrefsProvider: SharedPrefsProvider
) {

    companion object {

        internal const val testTitle = "test_title"
        internal const val testsDescription = "test_description"
        internal val testImages = ArrayList<String>()

        internal const val testPhone = "3726583269"
        internal const val testEmail = "test@example.com"
        internal const val testTwitter = "@zeynelerdi"

        init {
            testImages.add("http://eample.com/image1.jgp")
            testImages.add("http://eample.com/image2.jgp")
        }
    }

    /**
     * Fill the cache/database using the fake items.
     */
    internal fun fillCache() {
        clearCache()

        pagesDao.insert(Pages(
                id = 1,
                title = testTitle,
                description = testsDescription,
                image = testImages
        ).apply {
            updateMills = System.currentTimeMillis()
        })
        pagesDao.insert(Pages(
                id = 2,
                title = testTitle,
                description = testsDescription,
                image = testImages
        ).apply {
            updateMills = System.currentTimeMillis()
        })
        pagesDao.insert(Pages(
                id = 3,
                title = testTitle,
                description = testsDescription,
                image = testImages
        ).apply {
            updateMills = System.currentTimeMillis()
        })

        // Save the contact to the database
        sharedPrefsProvider.savePreferences(Contact.PREF_KEY_PHONE, testPhone)
        sharedPrefsProvider.savePreferences(Contact.PREF_KEY_EMAIL, testEmail)
        sharedPrefsProvider.savePreferences(Contact.PREF_KEY_TWITTER, testTwitter)
    }

    /**
     * Clear the cache with database and shared preference.
     */
    internal fun clearCache() {
        pagesDao.nukeTable()
        sharedPrefsProvider.nukePrefrance()
    }
}

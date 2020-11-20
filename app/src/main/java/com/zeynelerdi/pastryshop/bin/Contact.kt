

package com.zeynelerdi.pastryshop.bin

/**
 * Created by Zeynel Erdi Karabulut on 02/06/18.
 * Bin that holds the contact information.
 *
 * @param phone Mobile number.
 * @param email Email address.
 * @param twitter Url of the twitter handler.
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
data class Contact(

        val phone: String,

        val email: String,

        val twitter: String
) {

    companion object {

        // Preference keys name.
        const val PREF_KEY_PHONE = "phone"
        const val PREF_KEY_EMAIL = "email"
        const val PREF_KEY_TWITTER = "twitter"
    }
}
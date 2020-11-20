

package com.zeynelerdi.pastryshop.repository.network

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * Network related configurations.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */

internal object NetworkConfig {

    /**
     * Request read timeout in minutes.
     */
    internal const val READ_TIMEOUT = 1L

    /**
     * Request write timeout in minutes.
     */
    internal const val WRITE_TIMEOUT = 1L

    /**
     * Network connection timeout in minutes.
     */
    internal const val CONNECTION_TIMEOUT = 1L
}

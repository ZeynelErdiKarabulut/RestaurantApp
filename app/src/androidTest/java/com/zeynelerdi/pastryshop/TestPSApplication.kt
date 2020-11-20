

package com.zeynelerdi.pastryshop

import com.zeynelerdi.pastryshop.di.DaggerMockRootComponent
import com.zeynelerdi.pastryshop.di.MockRootDiModule
import com.zeynelerdi.pastryshop.di.MockRootComponent
import com.zeynelerdi.pastryshop.di.RootComponent
import com.zeynelerdi.pastryshop.utils.BaseApplication
import com.zeynelerdi.testutils.MockServerManager
import java.io.IOException

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
internal class TestPSApplication : BaseApplication() {

    var mockServerManager = MockServerManager()

    var baseUrl : String = ""

    override fun onCreate() {
        super.onCreate()

        // Start the mock server
        val thread = object : Thread() {
            override fun run() {
                try {
                    mockServerManager.startMockWebServer()
                    // Pass the mock web server url
                    baseUrl = mockServerManager.getBaseUrl()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        thread.start()

        // Wait for the server to start
        Thread.sleep(1000L)
    }

    /**
     * Prepare the [RootComponent] that will contain the root of the dependency graph. You can easily
     * mock these objects for tests by providing mock version of [RootComponent]. See android test
     * source for detail.
     */
    override fun prepareRootComponent(): RootComponent {
        return DaggerMockRootComponent.builder()
                .mockRootDiModule(MockRootDiModule(this@TestPSApplication))
                .build()
    }

    override fun injectRootComponent() {
        (rootComponent as MockRootComponent).inject(this@TestPSApplication)
    }
}



package com.zeynelerdi.testutils

import android.annotation.SuppressLint
import android.content.Context
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.io.Closeable
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.nio.file.Paths

/**
 * Created by Zeynel Erdi Karabulut on 05/12/17.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
class MockServerManager : Closeable {

    private lateinit var mockWebServer: MockWebServer

    @SuppressLint("NewApi")
    fun getResponsesPath(): String {

        if (System.getenv("TRAVIS") == "true") {
            return "${System.getenv("TRAVIS_BUILD_DIR")}/testutils/src/main/res/raw"
        }

        @Suppress("UselessCallOnNotNull")
        val rootDirPath = Paths.get("").toAbsolutePath().toString().let {
            if (it.isNullOrEmpty()) {
                System.getenv("PROJECT_ROOT")
            } else {
                it.substring(0 until it.lastIndexOf("PastryShop"))
                        .plus("PastryShop")
            }
        }

        return "$rootDirPath/testutils/src/main/res/raw"
    }

    /**
     * Start mock web server for the wikipedia api.
     *
     * @return [MockWebServer]
     */
    fun startMockWebServer(): MockWebServer {
        try {
            mockWebServer = MockWebServer()
            mockWebServer.start()
            return mockWebServer
        } catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException("Failed to start mock server.")
        }
    }

    /**
     * Enqueue the next response in [mockWebServer].
     */
    @JvmOverloads
    fun enqueueResponse(response: String, type: String = "application/json") {
        mockWebServer.enqueue(MockResponse()
                .setHeader("Content-type", type)
                .setBody(response)
                .setResponseCode(HttpURLConnection.HTTP_OK))
    }

    /**
     * Enqueue the next response in [mockWebServer].
     */
    @JvmOverloads
    fun enqueueResponse(response: File, type: String = "application/json") {
        enqueueResponse(response = FileReader.getStringFromFile(file = response), type = type)
    }

    /**
     * Enqueue the next response in [mockWebServer].
     */
    @JvmOverloads
    fun enqueueResponse(context: Context, rawFile: Int, type: String = "application/json") {
        enqueueResponse(response = FileReader.getStringFromRawFile(context, rawFile), type = type)
    }


    fun getBaseUrl() = mockWebServer.url("/").toString()

    override fun close() {
        mockWebServer.shutdown()
    }
}

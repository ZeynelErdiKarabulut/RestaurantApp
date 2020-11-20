package com.zeynelerdi.pastryshop

import android.app.Application
import android.app.Instrumentation
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner


/**
 * Created by zeynelerdi on 04-Jun-18.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
class UiTestsRunner : AndroidJUnitRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {

        // Use the test application class that can inject mock dependencies using dagger.
        return Instrumentation.newApplication(TestPSApplication::class.java, context)
    }
}

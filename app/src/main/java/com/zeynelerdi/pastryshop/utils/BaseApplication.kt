

package com.zeynelerdi.pastryshop.utils

import android.app.Application
import android.content.Context
import com.zeynelerdi.pastryshop.di.RootComponent

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * Base [Application] that can be extended in different build variants.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
internal abstract class BaseApplication : Application() {

    /**
     * Dagger component to provide the root objects of the dependency graph.
     *
     * @see RootComponent
     */
    protected lateinit var rootComponent: RootComponent

    /**
     * Prepare the [RootComponent] that will contain the root of the dependency graph. You can easily
     * mock these objects for tests by providing mock version of [RootComponent]. See android test
     * source for detail.
     */
    protected abstract fun prepareRootComponent(): RootComponent

    /**
     * Inject [RootComponent] into the class that inherits [BaseApplication].
     */
    protected abstract fun injectRootComponent()

    override fun onCreate() {
        super.onCreate()

        //Create app component
        rootComponent = prepareRootComponent()

        //Inject dagger
        injectRootComponent()
    }

    companion object {

        /**
         * Static factory method to get [rootComponent]. This is casts [Application] to [BaseApplication]
         * internally.
         */
        fun getRootComponent(context: Context): RootComponent {
            return (context.applicationContext as BaseApplication).rootComponent
        }
    }
}

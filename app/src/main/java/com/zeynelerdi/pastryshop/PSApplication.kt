

package com.zeynelerdi.pastryshop

import com.zeynelerdi.pastryshop.di.DaggerRootComponent
import com.zeynelerdi.pastryshop.di.RootComponent
import com.zeynelerdi.pastryshop.di.RootDiModule
import com.zeynelerdi.pastryshop.utils.BaseApplication

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * [BaseApplication] for the app.
 *
 * @see BaseApplication
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
internal class PSApplication : BaseApplication() {

    override fun prepareRootComponent(): RootComponent {
        return DaggerRootComponent.builder()
                .rootDiModule(RootDiModule(this@PSApplication))
                .build()
    }

    override fun injectRootComponent() {
        rootComponent.inject(this@PSApplication)
    }
}

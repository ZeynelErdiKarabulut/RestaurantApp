

package com.zeynelerdi.pastryshop

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.zeynelerdi.pastryshop.di.DaggerAppDiComponent
import com.zeynelerdi.pastryshop.main.MainActivity
import com.zeynelerdi.pastryshop.repository.Repository
import com.zeynelerdi.pastryshop.utils.BaseApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * The launcher activity for the application.
 */
class SplashActivity : AppCompatActivity() {
    private val tag = "SplashActivity"

    @Inject
    internal lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppDiComponent.builder()
                .rootComponent(BaseApplication.getRootComponent(this@SplashActivity))
                .build()
                .inject(this@SplashActivity)

        // Refresh the data
        // Fire the network call and forget.
        repository.refreshData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    // Do nothing
                }, {
                    // Do nothing
                    Log.e(tag, it.message)
                })

        // Navigate to the main screen
        MainActivity.launch(this@SplashActivity, false)
    }
}

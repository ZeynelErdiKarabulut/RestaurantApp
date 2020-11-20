

package com.zeynelerdi.pastryshop.utils

import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import android.support.annotation.VisibleForTesting
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * Base class for [ViewModel].
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
abstract class BaseViewModel : ViewModel() {

    /**
     * [CompositeDisposable] to hold all the disposables from Rx and repository.
     */
    @VisibleForTesting
    internal val compositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * Add new [Disposable] to be dispose when the view model cleared.
     */
    protected fun addDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    @CallSuper
    override fun onCleared() {
        super.onCleared()

        //Delete all the API connections.
        compositeDisposable.dispose()
    }
}

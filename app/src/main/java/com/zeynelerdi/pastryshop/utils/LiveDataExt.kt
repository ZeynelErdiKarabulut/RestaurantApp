

package com.zeynelerdi.pastryshop.utils

import android.arch.lifecycle.MutableLiveData

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
fun <T> MutableLiveData<T>.recall(){
    value = value
}
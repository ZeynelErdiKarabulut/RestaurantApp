

package com.zeynelerdi.pastryshop.utils

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.MotionEvent


/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * Custom [CoordinatorLayout].
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
class PSCoordinatorLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : CoordinatorLayout(context, attrs) {

    /**
     * True to allow [PSCoordinatorLayout] to respond user touch events else false.
     */
    var allowTouchEvents = true

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return allowTouchEvents && super.onTouchEvent(ev)
    }
}
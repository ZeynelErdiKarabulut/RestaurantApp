

package com.zeynelerdi.pastryshop.utils

import android.content.Context
import android.support.v7.widget.LinearLayoutManager

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * Custom [LinearLayoutManager].
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
class PSLinearLayoutManager(context: Context,
                            orientation: Int = LinearLayoutManager.VERTICAL,
                            reverseLayout: Boolean = false) : LinearLayoutManager(context, orientation, reverseLayout) {
    /**
     * True to allow [PSLinearLayoutManager] to scroll the recycler view.
     */
    var isScrollEnabled = true

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }

    override fun canScrollHorizontally(): Boolean {
        return isScrollEnabled && super.canScrollHorizontally()
    }
}
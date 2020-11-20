

package com.zeynelerdi.pastryshop.utils

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
fun Fragment.showSnack(message: String,
                       actionName: String? = null,
                       actionListener: View.OnClickListener? = null,
                       duration: Int = Snackbar.LENGTH_SHORT) {

    if (activity == null) return

    val snackbar = Snackbar.make((activity!!.findViewById<ViewGroup>(android.R.id.content)).getChildAt(0),
            message, duration)

    actionName?.let {
        snackbar.setAction(actionName, actionListener)
        snackbar.setActionTextColor(context!!.getColorCompat(android.R.color.holo_orange_dark))
    }

    snackbar.show()
}
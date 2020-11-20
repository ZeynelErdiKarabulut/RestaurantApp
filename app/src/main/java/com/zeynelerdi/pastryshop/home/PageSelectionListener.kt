

package com.zeynelerdi.pastryshop.home

import android.view.View
import com.zeynelerdi.pastryshop.bin.Pages

/**
 * Created by Zeynel Erdi Karabulut on 02/06/18.
 * Listener to get callbacks whenever any page is selected from the list.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
internal interface PageSelectionListener {

    /**
     * This callback will be called whenever any page from the pages list gets clicked. It provides
     * selected [page] info and [itemView] of the row.
     */
    fun onPageSelected(page: Pages, itemView: View)
}
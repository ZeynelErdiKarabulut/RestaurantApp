

package com.zeynelerdi.pastryshop.home

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * [RecyclerView.ItemDecoration] to add margins to each items.
 *
 * @param margin desirable margin size in px between the views in the recyclerView
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
class PagesListItemDecorator(private val margin: Int) : RecyclerView.ItemDecoration() {

    /**
     * Set different margins for the items inside the recyclerView. It will set [margin] to top,
     * right and bottom of the list. If the item is at the first position, left margin will be [margin].
     */
    override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        outRect.top = margin
        outRect.bottom = margin

        //we only add top margin to the first row
        if (position <= 0) {
            outRect.left = margin
        }
        outRect.right = margin
    }
}
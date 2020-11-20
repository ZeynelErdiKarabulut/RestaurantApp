

package com.zeynelerdi.pastryshop.home

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.zeynelerdi.pastryshop.bin.Pages

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
internal class PagesAdapter(
        private val cards: ArrayList<Pages>,
        private val pageSelectionListener: PageSelectionListener)
    : RecyclerView.Adapter<PagesListViewHolder>() {

    var isClickEnabled = true

    internal var imageHeight = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagesListViewHolder {
        return PagesListViewHolder.create(parent = parent)
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: PagesListViewHolder, position: Int) {
        holder.bind(
                page = cards[position],
                imageHeight = imageHeight,
                onClick = {
                    if (isClickEnabled) {
                        pageSelectionListener.onPageSelected(it, holder.itemView)
                    }
                }
        )
    }
}
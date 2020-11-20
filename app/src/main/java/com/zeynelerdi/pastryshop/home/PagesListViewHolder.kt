

package com.zeynelerdi.pastryshop.home

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zeynelerdi.pastryshop.R
import com.zeynelerdi.pastryshop.bin.Pages
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_dashboard_card.view.*

/**
 * Created by Zeynel Erdi Karabulut on 01/06/20.
 * [RecyclerView.ViewHolder] for holding the view for the pages list.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
class PagesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {

        /**
         * Create new instance of [PagesListViewHolder].
         *
         * @param parent Parent [ViewGroup].
         */
        fun create(parent: ViewGroup): PagesListViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_dashboard_card, parent, false)

            return PagesListViewHolder(view)
        }
    }

    /**
     * Bind the [page] with the [itemView]. [imageHeight] is the height of the image view that
     * displays page cover image. Whenever user clicks on the [itemView], [onClick] will invoke to
     * perform given actions.
     */
    fun bind(page: Pages, imageHeight: Int, onClick: (page: Pages) -> Unit) {
        itemView.card_title_tv.text = page.title
        itemView.card_description_tv.text = page.description

        //Set image
        itemView.card_iv.layoutParams.height = imageHeight
        if (imageHeight > 0) {
            Picasso.get().load(page.image[0])
                    .resizeDimen(R.dimen.home_cards_image_max_height, R.dimen.home_cards_image_max_height)
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
                    .into(itemView.card_iv)
        }

        itemView.setOnClickListener { onClick.invoke(page) }

        //Set transition names
        with(itemView.context) {
            ViewCompat.setTransitionName(itemView.card_title_tv,
                    "${getString(R.string.transition_name_home_card_title)}_${page.id}")
            ViewCompat.setTransitionName(itemView.card_description_tv,
                    "${getString(R.string.transition_name_home_card_description)}_${page.id}")
            ViewCompat.setTransitionName(itemView.card_iv,
                    "${getString(R.string.transition_name_home_card_image)}_${page.id}")
            ViewCompat.setTransitionName(itemView.card,
                    "${getString(R.string.transition_name_home_card_view)}_${page.id}")
            ViewCompat.setTransitionName(itemView.detail_close_btn_stub,
                    "${getString(R.string.transition_name_close_button)}_${page.id}")
        }
    }
}


package com.zeynelerdi.pastryshop.pageDetail

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.zeynelerdi.pastryshop.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image_pager.view.*


/**
 * Created by Zeynel Erdi Karabulut on 03/06/18.
 * [PagerAdapter] to display images into the viewpager.
 *
 * @param images List of urls of the images to display in the view pager.
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
internal class ImagePagerAdapter(
        private val context: Context,
        private val images: ArrayList<String>) : PagerAdapter() {

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as FrameLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_image_pager, container, false)

        // Display the image
        Picasso.get()
                .load(images[position])
                .noFade()
                .placeholder(R.drawable.ic_placeholder)
                .into(itemView.pager_image_iv)

        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as FrameLayout)
    }
}
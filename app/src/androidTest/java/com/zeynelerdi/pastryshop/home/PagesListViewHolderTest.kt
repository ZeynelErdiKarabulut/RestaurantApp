

package com.zeynelerdi.pastryshop.home

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.runner.AndroidJUnit4
import android.support.v4.view.ViewCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.zeynelerdi.pastryshop.R
import com.zeynelerdi.pastryshop.bin.Pages
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Zeynel Erdi Karabulut on 03/06/18.
 *
 * @author [zeynelerdi](https://github.com/ZeynelErdiKarabulut)
 */
@RunWith(AndroidJUnit4::class)
class PagesListViewHolderTest {

    private val testTitle = "test_title"
    private val testsDescription = "test_description"
    private val testImages = ArrayList<String>()

    init {
        testImages.add("http://example.com/image.jpg")
    }

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getTargetContext()
    }

    @Test
    @UiThreadTest
    fun checkCreateHolder() {
        val holder = PagesListViewHolder.create(LinearLayout(context))

        assertNotNull(holder)
        assertNotNull(holder.itemView.findViewById<AppCompatTextView>(R.id.card_title_tv))
        assertNotNull(holder.itemView.findViewById<AppCompatTextView>(R.id.card_description_tv))
        assertNotNull(holder.itemView.findViewById<AppCompatImageView>(R.id.card_iv))
    }

    @Test
    @UiThreadTest
    fun checkBindViewHolder() {
        val page = Pages(
                id = 1,
                title = testTitle,
                description = testsDescription,
                image = testImages
        )
        val imageViewHeight = 100
        var clickCount = 0

        val itemView = LayoutInflater.from(context).inflate(R.layout.item_dashboard_card, null)

        val holder = PagesListViewHolder(itemView)

        // Bind the adapter
        holder.bind(page, imageViewHeight, { clickCount++ })

        // Validate title
        assertEquals(page.title,
                holder.itemView.findViewById<AppCompatTextView>(R.id.card_title_tv).text)

        // Validate description
        assertEquals(page.description,
                holder.itemView.findViewById<AppCompatTextView>(R.id.card_description_tv).text)
        assertEquals(context.resources.getInteger(R.integer.home_cards_description_max_line),
                holder.itemView.findViewById<AppCompatTextView>(R.id.card_description_tv).maxLines)

        // Validate image view
        assertEquals(imageViewHeight,
                holder.itemView.findViewById<AppCompatImageView>(R.id.card_iv).layoutParams.height)

        // Validate card click
        itemView.performClick()
        assertEquals(1, clickCount)
    }

    @Test
    @UiThreadTest
    fun checkTraditionNames() {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_dashboard_card, null)

        val holder = PagesListViewHolder(itemView)
        val page = Pages(
                id = 1,
                title = testTitle,
                description = testsDescription,
                image = testImages
        )

        // Bind the adapter
        holder.bind(page, 100, { /* Do nothing */ })

        // Validate transition names
        assertEquals("${context.getString(R.string.transition_name_home_card_title)}_${page.id}",
                ViewCompat.getTransitionName(holder.itemView.findViewById<AppCompatTextView>(R.id.card_title_tv)))

        assertEquals("${context.getString(R.string.transition_name_home_card_description)}_${page.id}",
                ViewCompat.getTransitionName(holder.itemView.findViewById<AppCompatTextView>(R.id.card_description_tv)))

        assertEquals("${context.getString(R.string.transition_name_home_card_image)}_${page.id}",
                ViewCompat.getTransitionName(holder.itemView.findViewById<AppCompatTextView>(R.id.card_iv)))

        assertEquals("${context.getString(R.string.transition_name_home_card_view)}_${page.id}",
                ViewCompat.getTransitionName(holder.itemView.findViewById<AppCompatTextView>(R.id.card)))

        assertEquals("${context.getString(R.string.transition_name_close_button)}_${page.id}",
                ViewCompat.getTransitionName(holder.itemView.findViewById<AppCompatTextView>(R.id.detail_close_btn_stub)))
    }
}
package com.zeynelerdi.pastryshop.home

import android.content.Intent
import android.net.Uri
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.UiDevice
import com.zeynelerdi.pastryshop.CacheDataManager
import com.zeynelerdi.pastryshop.R
import com.zeynelerdi.pastryshop.TestPSApplication
import com.zeynelerdi.pastryshop.bin.Contact
import com.zeynelerdi.pastryshop.di.DaggerTestAppComponent
import com.zeynelerdi.pastryshop.di.MockRootComponent
import com.zeynelerdi.pastryshop.utils.BaseApplication
import com.zeynelerdi.pastryshop.utils.SharedPrefsProvider
import com.zeynelerdi.testutils.ElapsedTimeIdlingResource
import com.zeynelerdi.testutils.FragmentTestRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Created by zeynelerdi on 04-Jun-18.
 *
 * @author [zeynelerdi](https://github.com/ZeynelErdiKarabulut)
 */
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @Rule
    @JvmField
    val rule = FragmentTestRule(HomeFragment::class.java)

    @Inject
    internal lateinit var cacheDataManager: CacheDataManager

    @Before
    fun setUp() {
        DaggerTestAppComponent.builder()
                .mockRootComponent((BaseApplication.getRootComponent(rule.activity)) as MockRootComponent)
                .build()
                .inject(this@HomeFragmentTest)

        cacheDataManager.fillCache()
    }

    @Test
    fun checkIfTitleDisplayed_Portrait() {
        // Check with the toolbar is expanded
        onView(withId(R.id.shop_name_tv))
                .check(matches(withText(rule.activity.getString(R.string.shop_name))))

        // Check with the toolbar is collapsed
        // Swipe up
        onView(withId(R.id.home_collapsing_toolbar)).perform(ViewActions.swipeUp())

        // Check it the shop name is still visible
        onView(withId(R.id.shop_name_tv))
                .check(matches(withText(rule.activity.getString(R.string.shop_name))))
    }

    @Test
    fun checkIfTitleDisplayed_Landscape() {
        //Switch to landscape
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.setOrientationLeft()

        // Check with the toolbar is expanded
        onView(withId(R.id.shop_name_tv))
                .check(matches(withText(rule.activity.getString(R.string.shop_name).toUpperCase())))

        // Check with the toolbar is collapsed
        // Swipe up
        onView(withId(R.id.home_collapsing_toolbar)).perform(ViewActions.swipeUp())

        // Check it the shop name is still visible
        onView(withId(R.id.shop_name_tv))
                .check(matches(withText(rule.activity.getString(R.string.shop_name).toUpperCase())))

        device.setOrientationNatural()
    }

    @Test
    fun checkIfSubTitleDisplayed_Portrait() {
        // Check with the toolbar is expanded
        onView(withId(R.id.shop_subtitle_tv))
                .check(matches(withText(rule.activity.getString(R.string.shop_subtitle).toUpperCase())))

        // Check with the toolbar is collapsed
        // Swipe up
        onView(withId(R.id.home_collapsing_toolbar)).perform(ViewActions.swipeUp())

        // Check it the shop name is still visible
        onView(withId(R.id.shop_subtitle_tv))
                .check(matches(withText(rule.activity.getString(R.string.shop_subtitle).toUpperCase())))
    }

    @Test
    fun checkIfSubTitleDisplayed_Landscape() {
        //Switch to landscape
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.setOrientationLeft()

        // Check with the toolbar is expanded
        onView(withId(R.id.shop_subtitle_tv))
                .check(matches(withText(rule.activity.getString(R.string.shop_subtitle).toUpperCase())))

        // Check with the toolbar is collapsed
        // Swipe up
        onView(withId(R.id.home_collapsing_toolbar)).perform(ViewActions.swipeUp())

        // Check it the shop name is still visible
        onView(withId(R.id.shop_subtitle_tv))
                .check(matches(withText(rule.activity.getString(R.string.shop_subtitle).toUpperCase())))

        device.setOrientationNatural()
    }

    @Test
    fun checkDialer() {
        Intents.init()

        // Wait for the network to get updated response
        val idlingResource = ElapsedTimeIdlingResource(1000)
        IdlingRegistry.getInstance().register(idlingResource)

        onView(withId(R.id.phone_fab)).perform(ViewActions.click())

        intended(hasAction(Intent.ACTION_DIAL))
        intended(hasData(Uri.parse("tel:${CacheDataManager.testPhone}")))

        Intents.release()

        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}

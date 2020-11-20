

package com.zeynelerdi.pastryshop.utils

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.view.getMotionEvent
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Zeynel Erdi Karabulut on 03/06/18.
 *
 * @author [zeynelerdi](https://github.com/ZeynelErdiKarabulut)
 */
@RunWith(AndroidJUnit4::class)
class PSCoordinatorLayoutTest {

    @Test
    fun checkAllowTouchEvents() {
        val psCoordinatorLayout = PSCoordinatorLayout(InstrumentationRegistry.getTargetContext())

        // Check the initial state
        assertTrue(psCoordinatorLayout.allowTouchEvents)

        // Allow touch events
        psCoordinatorLayout.allowTouchEvents = false

        assertFalse(psCoordinatorLayout.onTouchEvent(getMotionEvent()))
    }
}
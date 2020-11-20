

package com.zeynelerdi.pastryshop.utils

import android.content.Intent
import android.graphics.Color
import com.zeynelerdi.pastryshop.main.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Created by Zeynel Erdi Karabulut on 02/06/18.
 *
 * @author [zeynelerdi](https://github.com/ZeynelErdiKarabulut)
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21], manifest = Config.NONE)
class ContextExtensionKtTest {

    @Test
    @Throws(Exception::class)
    fun testGetColor() {
        assertEquals(Color.WHITE, RuntimeEnvironment.application.getColorCompat(android.R.color.white))
    }


    @Test
    @Throws(Exception::class)
    fun checkPrepareLaunchIntent_InNewTask() {
        val launchIntent = RuntimeEnvironment.application
                .prepareLaunchIntent(MainActivity::class.java, true)

        assertEquals(MainActivity::class.java.name, launchIntent.component.className)
        assertEquals(Intent.FLAG_ACTIVITY_CLEAR_TASK
                or Intent.FLAG_ACTIVITY_CLEAR_TOP
                or Intent.FLAG_ACTIVITY_NEW_TASK, launchIntent.flags)
    }

    @Test
    @Throws(Exception::class)
    fun checkPrepareLaunchIntent_WithoutNewTask() {
        val launchIntent = RuntimeEnvironment.application
                .prepareLaunchIntent(MainActivity::class.java, false)

        assertEquals(MainActivity::class.java.name, launchIntent.component.className)
        assertEquals(0, launchIntent.flags)
    }
}
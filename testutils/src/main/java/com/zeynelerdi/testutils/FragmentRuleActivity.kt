

package com.zeynelerdi.testutils

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * Created by Zeynel Erdi Karabulut on 21-Jul-17.
 * This is the test activity to load fragments inside it while running the instrumentation tests.
 */

class FragmentRuleActivity : AppCompatActivity() {

    companion object {
        const val CONTAINER_ID = 384
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val frameLayout = FrameLayout(this@FragmentRuleActivity)
        setContentView(frameLayout)

        frameLayout.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        frameLayout.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        frameLayout.id = CONTAINER_ID
    }
}

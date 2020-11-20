

package com.zeynelerdi.testutils

import android.support.test.rule.ActivityTestRule
import android.support.v4.app.Fragment
import org.junit.Assert.fail

/**
 * Created by Zeynel Erdi Karabulut on 21-Jul-17.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
class FragmentTestRule<F : Fragment>(fragmentClass: Class<F>,
                                     fragmentInstance: F? = null)
    : ActivityTestRule<FragmentRuleActivity>(FragmentRuleActivity::class.java) {

    lateinit var fragment: F

    init {
        if (fragmentInstance == null) {
            try {
                fragment = fragmentClass.newInstance()
            } catch (e: InstantiationException) {
                fail(String.format("%s: Could not insert %s into FragmentRuleActivity: %s",
                        javaClass.simpleName,
                        fragmentClass.simpleName,
                        e.message))
            } catch (e: IllegalAccessException) {
                fail(String.format("%s: Could not insert %s into FragmentRuleActivity: %s", javaClass.simpleName, fragmentClass.simpleName, e.message))
            }
        } else {
            fragment = fragmentInstance
        }
    }

    override fun afterActivityLaunched() {
        super.afterActivityLaunched()

        //Instantiate and insert the fragment into the container layout
        val manager = activity.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(FragmentRuleActivity.CONTAINER_ID, fragment)
        transaction.commit()


    }
}



package android.view

/**
 * Created by Zeynel Erdi Karabulut on 03/06/18.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
fun getMotionEvent(): MotionEvent = MotionEvent.obtain(
        1L,
        1L,
        MotionEvent.ACTION_DOWN,
        1.0F,
        1.0F,
        1
)
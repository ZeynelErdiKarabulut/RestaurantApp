

package com.zeynelerdi.pastryshop.bin

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Zeynel Erdi Karabulut on 03/06/18.
 *
 * @author [zeynelerdi](https://github.com/ZeynelErdiKarabulut)
 */
@RunWith(JUnit4::class)
class PagesTest {

    private val testId = 1L
    private val testTitle = "test_title"
    private val testsDescription = "test_description"
    private val testImages = ArrayList<String>()

    init {
        testImages.add("http://eample.com/image1.jgp")
        testImages.add("http://eample.com/image2.jgp")
    }

    @Test
    fun checkInitialize() {
        val page = Pages(
                id = testId,
                title = testTitle,
                description = testsDescription,
                image = testImages
        )

        assertEquals(testId, page.id)
        assertEquals(testTitle, page.title)
        assertEquals(testsDescription, page.description)
        page.image.forEachIndexed { index, image -> assertEquals(testImages[index], image) }
    }


    @Test
    fun checkUpdateMills_ZeroMills() {
        try {
            val page = Pages(
                    id = testId,
                    title = testTitle,
                    description = testsDescription,
                    image = testImages
            )
            page.updateMills = 0

            fail("This should throw an exception.")
        } catch (e: IllegalArgumentException) {
            //Passed
        }
    }


    @Test
    fun checkUpdateMills_NegativeMills() {
        try {
            val page = Pages(
                    id = testId,
                    title = testTitle,
                    description = testsDescription,
                    image = testImages
            )
            page.updateMills = -19

            fail("This should throw an exception.")
        } catch (e: IllegalArgumentException) {
            //Passed
        }
    }


    @Test
    fun checkUpdateMills() {
        val currentMills = System.currentTimeMillis()
        try {
            val page = Pages(
                    id = testId,
                    title = testTitle,
                    description = testsDescription,
                    image = testImages
            )
            page.updateMills = currentMills

            assertEquals(currentMills, page.updateMills)
        } catch (e: IllegalArgumentException) {
            fail(e.message)
        }
    }
}
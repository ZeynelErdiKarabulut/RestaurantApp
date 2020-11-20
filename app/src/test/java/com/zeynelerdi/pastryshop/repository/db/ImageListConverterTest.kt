

package com.zeynelerdi.pastryshop.repository.db

import android.os.Build
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by Zeynel Erdi Karabulut on 02/06/18.
 *
 * @author [zeynelerdi](https://github.com/ZeynelErdiKarabulut)
 */


@RunWith(Enclosed::class)
class ImageListConverterTest {

    @RunWith(JUnit4::class)
    class ImageListConverterTest {

        @Test
        fun checkToImageList_NullInput() {
            val list = ImageListConverter().toImageList(null)
            assertTrue(list.isEmpty())
        }

        @Test
        fun checkToImageList_SingleImage() {
            val testImage = "http://image.com/1.jpg"
            val list = ImageListConverter().toImageList(testImage)
            assertEquals(1, list.size)
            assertEquals(testImage, list[0])
        }

        @Test
        fun checkToImageList_MultipleImage() {
            val testImage = "http://image.com/1.jpg"
            val testImage1 = "http://image.com/2.jpg"
            val input = "http://image.com/1.jpg,http://image.com/2.jpg"

            val list = ImageListConverter().toImageList(input)
            assertEquals(2, list.size)
            assertEquals(testImage, list[0])
            assertEquals(testImage1, list[1])
        }
    }

    @RunWith(RobolectricTestRunner::class)
    @Config(sdk = [Build.VERSION_CODES.LOLLIPOP], manifest = Config.NONE)
    class ImageListConverterRoboElectricTest {

        @Test
        fun checkFromImageList_SingleImage() {
            val testImage = "http://image.com/1.jpg"

            val imageList = ArrayList<String>()
            imageList.add(testImage)

            val imgString = ImageListConverter().fromImageList(imageList)
            assertEquals(testImage, imgString)
        }

        @Test
        fun checkFromImageList_MultipleImage() {
            val testImage = "http://image.com/1.jpg"
            val testImage1 = "http://image.com/2.jpg"
            val output = "http://image.com/1.jpg,http://image.com/2.jpg"

            val imageList = ArrayList<String>()
            imageList.add(testImage)
            imageList.add(testImage1)

            val imgString = ImageListConverter().fromImageList(imageList)
            assertEquals(output, imgString)
        }
    }
}
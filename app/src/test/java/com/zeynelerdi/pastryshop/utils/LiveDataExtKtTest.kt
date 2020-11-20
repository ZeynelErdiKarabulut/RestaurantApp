

package com.zeynelerdi.pastryshop.utils

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Zeynel Erdi Karabulut on 02/06/18.
 *
 * @author [zeynelerdi](https://github.com/ZeynelErdiKarabulut)
 */
@RunWith(JUnit4::class)
class LiveDataExtKtTest {
    private val testString = "test"

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var testLiveData: MutableLiveData<String>
    @Mock
    private lateinit var eventObserver: Observer<String>


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this@LiveDataExtKtTest)

        testLiveData = MutableLiveData()
        testLiveData.value = testString
    }

    @Test
    fun checkRecall() {
        testLiveData.observeForever(eventObserver)

        testLiveData.recall()

        Mockito.verify(eventObserver, Mockito.times(2))
                .onChanged(testString)
    }
}
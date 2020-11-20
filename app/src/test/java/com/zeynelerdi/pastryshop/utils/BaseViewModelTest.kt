

package com.zeynelerdi.pastryshop.utils

import io.reactivex.Observable
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.TimeUnit

/**
 * Created by Zeynel Erdi Karabulut on 02/06/18.
 *
 * @author [zeynelerdi](https://github.com/ZeynelErdiKarabulut)
 */
@RunWith(JUnit4::class)
class BaseViewModelTest {

    class TestViewModel : BaseViewModel() {
        fun addNewDisposable() {
            addDisposable(Observable.timer(10, TimeUnit.MINUTES).subscribe())
        }

        fun forceClear() {
            onCleared()
        }
    }

    private lateinit var testViewModel: TestViewModel

    @Before
    fun setUp() {
        testViewModel = TestViewModel()
    }

    @After
    fun clear() {
        testViewModel.forceClear()
    }

    @Test
    fun checkAddDisposable() {
        testViewModel.addNewDisposable()
        assertEquals(1, testViewModel.compositeDisposable.size())
    }

    @Test
    fun checkClearDisposable() {
        testViewModel.addNewDisposable()
        assertEquals(1, testViewModel.compositeDisposable.size())

        testViewModel.forceClear()
        assertEquals(0, testViewModel.compositeDisposable.size())
    }

}
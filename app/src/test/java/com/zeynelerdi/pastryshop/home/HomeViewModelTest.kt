

package com.zeynelerdi.pastryshop.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.zeynelerdi.pastryshop.MockCacheManager
import com.zeynelerdi.pastryshop.bin.Pages
import com.zeynelerdi.pastryshop.repository.Repository
import com.zeynelerdi.pastryshop.repository.RepositoryImpl
import com.zeynelerdi.pastryshop.repository.db.MockPagesDao
import com.zeynelerdi.pastryshop.repository.network.Network
import com.zeynelerdi.pastryshop.utils.SharedPrefsProvider
import com.zeynelerdi.testutils.MockServerManager
import com.zeynelerdi.testutils.MockSharedPreference
import com.zeynelerdi.testutils.RxSchedulersOverrideRule
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.io.File

/**
 * Created by Zeynel Erdi Karabulut on 03/06/18.
 *
 * @author [zeynelerdi](https://github.com/ZeynelErdiKarabulut)
 */
@RunWith(JUnit4::class)
class HomeViewModelTest {

    @Rule
    @JvmField
    val rxJavaRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var mockCacheManager: MockCacheManager
    private lateinit var mockServerManager: MockServerManager
    private lateinit var mockPagesDao: MockPagesDao
    private lateinit var mockSharedPrefsProvider: SharedPrefsProvider
    private lateinit var repository: Repository
    private lateinit var model: HomeViewModel

    @Mock
    private lateinit var pagesObserver: Observer<ArrayList<Pages>>

    @Mock
    private lateinit var errorPagesObserver: Observer<String>


    @Before
    fun setUp() {
        // Set repository
        mockServerManager = MockServerManager()
        mockPagesDao = MockPagesDao(ArrayList())
        mockSharedPrefsProvider = SharedPrefsProvider(MockSharedPreference())
        mockCacheManager = MockCacheManager(mockPagesDao, mockSharedPrefsProvider)

        mockServerManager.startMockWebServer()

        repository = RepositoryImpl(
                network = Network(mockServerManager.getBaseUrl(), true),
                pagesDao = mockPagesDao,
                sharedPrefsProvider = mockSharedPrefsProvider
        )

        MockitoAnnotations.initMocks(this@HomeViewModelTest)
    }

    @After
    fun tearUp() {
        mockServerManager.close()
        mockPagesDao.nukeTable()
    }

    @Test
    fun `check block ui flag`() {
        // Prepare data
        mockCacheManager.fillCache()

        // Initialize model
        model = HomeViewModel(repository)

        // UI should not block
        assertFalse(model.isBlockUi.value!!)
    }

    @Test
    fun `check load pages with filled cache`() {
        // Prepare data
        mockCacheManager.fillCache()

        // Initialize model
        model = HomeViewModel(repository)
        model.pages.observeForever(pagesObserver)
        model.errorLoadingPages.observeForever(errorPagesObserver)

        verify(pagesObserver, Mockito.times(1)).onChanged(model.pages.value)
        verify(errorPagesObserver, Mockito.never()).onChanged(anyString())
        assertFalse(model.isBlockUi.value!!)

        // Check the pages list
        assertEquals(3, model.pages.value!!.size)
        assertEquals(mockCacheManager.testTitle, model.pages.value!![0].title)
        assertEquals(mockCacheManager.testTitle, model.pages.value!![1].title)
        assertEquals(mockCacheManager.testTitle, model.pages.value!![2].title)
    }

    @Test
    fun `check load pages with empty cache`() {
        // Prepare data
        mockServerManager.enqueueResponse(File(mockServerManager.getResponsesPath() + "/app_data.json"))
        mockCacheManager.clearCache()

        // Initialize model
        model = HomeViewModel(repository)
        model.pages.observeForever(pagesObserver)
        model.errorLoadingPages.observeForever(errorPagesObserver)

        verify(pagesObserver, Mockito.times(1)).onChanged(model.pages.value)
        verify(errorPagesObserver, Mockito.never()).onChanged(anyString())
        assertFalse(model.isBlockUi.value!!)

        // Check the pages list
        assertEquals(3, model.pages.value!!.size)
        assertEquals("About", model.pages.value!![0].title)
        assertEquals("Menu", model.pages.value!![1].title)
        assertEquals("Credits", model.pages.value!![2].title)
    }

    @Test
    fun `check load pages with network error`() {
        // Prepare data
        mockServerManager.enqueueResponse("")
        mockCacheManager.clearCache()

        // Initialize model
        model = HomeViewModel(repository)
        model.pages.observeForever(pagesObserver)
        model.errorLoadingPages.observeForever(errorPagesObserver)

        verify(pagesObserver, Mockito.times(1)).onChanged(ArrayList(0))
        verify(errorPagesObserver, Mockito.times(1))
                .onChanged("End of input at line 1 column 1 path \$" /* Invalid json */)
        assertTrue(model.isBlockUi.value!!) // Error occurred. Keep UI blocked.
    }

    @Test
    fun `check load contact info with filled cache`() {
        // Prepare data
        mockCacheManager.fillCache()

        // Initialize model
        model = HomeViewModel(repository)
        model.loadPhoneNumber()

        // Check the phone number loaded
        assertEquals(mockCacheManager.testPhone, model.phoneNumber)
    }

    @Test
    fun `check load contact info with empty cache`() {
        // Prepare data
        mockServerManager.enqueueResponse(File(mockServerManager.getResponsesPath() + "/app_data.json"))
        mockCacheManager.clearCache()

        // Initialize model
        model = HomeViewModel(repository)
        model.loadPhoneNumber()

        // Check the phone number loaded
        assertEquals("+91 8866 756632" /* See the test response */, model.phoneNumber)
    }

    @Test
    fun `check load contact info with network error`() {
        // Prepare data
        mockServerManager.enqueueResponse("")
        mockCacheManager.clearCache()

        // Initialize model
        model = HomeViewModel(repository)
        model.loadPhoneNumber()

        // Check the phone number loaded
        assertNull(model.phoneNumber)
    }
}


package com.zeynelerdi.pastryshop.utils

import com.zeynelerdi.testutils.MockSharedPreference
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Zeynel Erdi Karabulut on 19-Jul-17.
 * Test class for [SharedPrefsProvider].
 */
@RunWith(JUnit4::class)
class SharedPrefsProviderTest {

    private val TEST_KEY = "test_key"

    private lateinit var mockSharedPreference: MockSharedPreference

    private lateinit var sharedPrefsProvider: SharedPrefsProvider

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mockSharedPreference = MockSharedPreference()
        sharedPrefsProvider = SharedPrefsProvider(mockSharedPreference)
    }

    @Test
    @Throws(Exception::class)
    fun removePreferences() {
        val editor = mockSharedPreference.edit()
        editor.putString(TEST_KEY, "String")
        editor.apply()

        sharedPrefsProvider.removePreferences(TEST_KEY)
        assertTrue(mockSharedPreference.getString(TEST_KEY, null) == null)
    }

    @Test
    @Throws(Exception::class)
    fun savePreferences() {
        assertFalse(mockSharedPreference.getInt(TEST_KEY, -1) != -1)
        sharedPrefsProvider.savePreferences(TEST_KEY, "String")
        assertTrue(mockSharedPreference.getString(TEST_KEY, null) != null)
    }

    @Test
    @Throws(Exception::class)
    fun savePreferences1() {
        assertFalse(mockSharedPreference.getInt(TEST_KEY, -1) != -1)
        sharedPrefsProvider.savePreferences(TEST_KEY, 1)
        assertTrue(mockSharedPreference.getInt(TEST_KEY, -1) != -1)
    }

    @Test
    @Throws(Exception::class)
    fun savePreferences2() {
        assertFalse(mockSharedPreference.getLong(TEST_KEY, -1) != -1L)
        sharedPrefsProvider.savePreferences(TEST_KEY, 100000L)
        assertTrue(mockSharedPreference.getLong(TEST_KEY, -1) != -1L)
    }

    @Test
    @Throws(Exception::class)
    fun savePreferences3() {
        assertFalse(mockSharedPreference.getBoolean(TEST_KEY, false))
        sharedPrefsProvider.savePreferences(TEST_KEY, true)
        assertTrue(mockSharedPreference.getBoolean(TEST_KEY, false))
    }

    @Test
    @Throws(Exception::class)
    fun getStringFromPreferences() {
        val testVal = "String"

        val editor = mockSharedPreference.edit()
        editor.putString(TEST_KEY, testVal)
        editor.apply()

        assertTrue(sharedPrefsProvider.getStringFromPreferences(TEST_KEY) == testVal)
    }

    @Test
    @Throws(Exception::class)
    fun getBoolFromPreferences() {
        val testVal = true

        val editor = mockSharedPreference.edit()
        editor.putBoolean(TEST_KEY, true)
        editor.apply()


        assertTrue(sharedPrefsProvider.getBoolFromPreferences(TEST_KEY) == testVal)
    }

    @Test
    @Throws(Exception::class)
    fun getLongFromPreference() {
        val testVal = 100000L

        val editor = mockSharedPreference.edit()
        editor.putLong(TEST_KEY, testVal)
        editor.apply()

        assertTrue(sharedPrefsProvider.getLongFromPreference(TEST_KEY) == testVal)
    }

    @Test
    @Throws(Exception::class)
    fun getIntFromPreference() {
        val testVal = 100

        val editor = mockSharedPreference.edit()
        editor.putInt(TEST_KEY, testVal)
        editor.apply()

        assertTrue(sharedPrefsProvider.getIntFromPreference(TEST_KEY) == testVal)
    }
}

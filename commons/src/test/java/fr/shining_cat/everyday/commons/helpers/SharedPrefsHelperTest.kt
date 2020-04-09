package fr.shining_cat.everyday.commons.helpers

import android.content.SharedPreferences
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SharedPrefsHelperTest {

    @Mock
    private lateinit var mockSharedPreferences: SharedPreferences

    @Mock
    private lateinit var mockSharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

//    @Test
//    fun `test get value`() {
//        val value = "value"
//        Mockito.`when`(
//            mockSharedPreferences.getString(
//                ArgumentMatchers.anyString(),
//                ArgumentMatchers.anyString()
//            )
//        ).thenReturn(value)
//        val sharedPrefsHelper =
//            SharedPrefsHelper(
//                mockSharedPreferences
//            )
//        Assert.assertEquals(value, sharedPrefsHelper.getvalue())
//        Mockito.verify(mockSharedPreferences).getString(
//            ArgumentMatchers.eq(
//                SharedPrefsHelperSettings.KEY_VALUE
//            ), ArgumentMatchers.eq("")
//        )
//    }
//
//    @Test
//    fun `test set value`() {
//        val value = "value"
//        Mockito.`when`(mockSharedPreferences.edit()).thenReturn(mockSharedPreferencesEditor)
//        Mockito.`when`(
//            mockSharedPreferencesEditor.putString(
//                ArgumentMatchers.anyString(),
//                ArgumentMatchers.anyString()
//            )
//        ).thenReturn(mockSharedPreferencesEditor)
//
//        val sharedPrefsHelper =
//            SharedPrefsHelper(
//                mockSharedPreferences
//            )
//        sharedPrefsHelper.setValue(value)
//
//        Mockito.verify(mockSharedPreferencesEditor).putString(
//            ArgumentMatchers.eq(
//                SharedPrefsHelperSettings.KEY_VALUE
//            ), ArgumentMatchers.eq(
//                value
//            )
//        )
//    }

}
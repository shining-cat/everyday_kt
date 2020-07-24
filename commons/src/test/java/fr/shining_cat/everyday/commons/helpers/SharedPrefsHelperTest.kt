package fr.shining_cat.everyday.commons.helpers

import android.content.SharedPreferences
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Before

class SharedPrefsHelperTest {

    @MockK
    private lateinit var mockSharedPreferences: SharedPreferences

    @MockK
    private lateinit var mockSharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { mockSharedPreferencesEditor.apply() } returns Unit
    }

    /* @Test
     fun `test get value`() {
         val value = true
         coEvery { mockSharedPreferences.getBoolean(any(), any()) } returns value
         val sharedPrefsHelper =
             SharedPrefsHelper(
                 mockSharedPreferences
             )
         Assert.assertEquals(value, sharedPrefsHelper.getvalue())
         coVerify {
             mockSharedPreferences.getBoolean(
                 eq(SharedPrefsHelperSettings.KEY_value),
                 eq(true)
             )
         }
     }

     @Test
     fun `test set value`() {
         val value = true
         coEvery { mockSharedPreferences.edit() } returns mockSharedPreferencesEditor
         coEvery {
             mockSharedPreferencesEditor.putBoolean(
                 any(),
                 any()
             )
         } returns mockSharedPreferencesEditor

         val sharedPrefsHelper =
             SharedPrefsHelper(
                 mockSharedPreferences
             )
         sharedPrefsHelper.setvalue(value)

         coVerify {
             mockSharedPreferencesEditor.putBoolean(
                 SharedPrefsHelperSettings.KEY_value,
                 value
             )
         }
     }*/
}
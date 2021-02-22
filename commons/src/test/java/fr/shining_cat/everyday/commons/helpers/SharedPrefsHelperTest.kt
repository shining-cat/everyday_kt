/*
 * Copyright (c) 2021. Shining-cat - Shiva Bernhard
 * This file is part of Everyday.
 *
 *     Everyday is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, version 3 of the License.
 *
 *     Everyday is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Everyday.  If not, see <https://www.gnu.org/licenses/>.
 */

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
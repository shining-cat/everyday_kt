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
import androidx.appcompat.app.AppCompatDelegate
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SharedPrefsHelperTest {

    @MockK
    private lateinit var mockSharedPreferences: SharedPreferences

    @MockK
    private lateinit var mockSharedPreferencesEditor: SharedPreferences.Editor

    private var sharedPrefsHelper:SharedPrefsHelper? = null

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sharedPrefsHelper = SharedPrefsHelper(mockSharedPreferences)
        coEvery { mockSharedPreferences.edit() } returns mockSharedPreferencesEditor
        coEvery {
            mockSharedPreferencesEditor.putString(
                any(),
                any()
            )
        } returns mockSharedPreferencesEditor
        coEvery {
            mockSharedPreferencesEditor.putLong(
                any(),
                any()
            )
        } returns mockSharedPreferencesEditor
        coEvery {
            mockSharedPreferencesEditor.putInt(
                any(),
                any()
            )
        } returns mockSharedPreferencesEditor
        coEvery { mockSharedPreferencesEditor.apply() } returns Unit

    }

     @Test
     fun `test get KEEP_SCREEN_ON`() {
         val value = true
         coEvery { mockSharedPreferences.getBoolean(any(), any()) } returns value
         assertEquals(value, sharedPrefsHelper?.getKeepScreenOn())
         coVerify {
             mockSharedPreferences.getBoolean(
                 eq(SharedPrefsHelperSettings.KEEP_SCREEN_ON),
                 eq(false)
             )
         }
     }

     @Test
     fun `test get DO_NOT_DISTURB`() {
         val value = true
         coEvery { mockSharedPreferences.getBoolean(any(), any()) } returns value
         assertEquals(value, sharedPrefsHelper?.getDoNotDisturb())
         coVerify {
             mockSharedPreferences.getBoolean(
                 eq(SharedPrefsHelperSettings.DO_NOT_DISTURB),
                 eq(false)
             )
         }
     }

     @Test
     fun `test get AEROPLANE_MODE_REMINDER`() {
         val value = true
         coEvery { mockSharedPreferences.getBoolean(any(), any()) } returns value
         assertEquals(value, sharedPrefsHelper?.getPlaneModeReminder())
         coVerify {
             mockSharedPreferences.getBoolean(
                 eq(SharedPrefsHelperSettings.AEROPLANE_MODE_REMINDER),
                 eq(false)
             )
         }
     }

     @Test
     fun `test get NOTIFICATION_ACTIVATED`() {
         val value = true
         coEvery { mockSharedPreferences.getBoolean(any(), any()) } returns value
         assertEquals(value, sharedPrefsHelper?.getNotificationActivated())
         coVerify {
             mockSharedPreferences.getBoolean(
                 eq(SharedPrefsHelperSettings.NOTIFICATION_ACTIVATED),
                 eq(false)
             )
         }
     }

     @Test
     fun `test get INFINITE_SESSION`() {
         val value = true
         coEvery { mockSharedPreferences.getBoolean(any(), any()) } returns value
         assertEquals(value, sharedPrefsHelper?.getInfiniteSession())
         coVerify {
             mockSharedPreferences.getBoolean(
                 eq(SharedPrefsHelperSettings.INFINITE_SESSION),
                 eq(false)
             )
         }
     }

     @Test
     fun `test get REWARDS_ACTIVATED`() {
         val value = false
         coEvery { mockSharedPreferences.getBoolean(any(), any()) } returns value
         assertEquals(value, sharedPrefsHelper?.getRewardsActivated())
         coVerify {
             mockSharedPreferences.getBoolean(
                 eq(SharedPrefsHelperSettings.REWARDS_ACTIVATED),
                 eq(true)
             )
         }
     }

     @Test
     fun `test get STATISTICS_ACTIVATED`() {
         val value = false
         coEvery { mockSharedPreferences.getBoolean(any(), any()) } returns value
         assertEquals(value, sharedPrefsHelper?.getStatisticsActivated())
         coVerify {
             mockSharedPreferences.getBoolean(
                 eq(SharedPrefsHelperSettings.STATISTICS_ACTIVATED),
                 eq(true)
             )
         }
     }

     @Test
     fun `test get NOTIFICATION_TIME`() {
         val value = "notification Time"
         coEvery { mockSharedPreferences.getString(any(), any()) } returns value
         assertEquals(value, sharedPrefsHelper?.getNotificationTime())
         coVerify {
             mockSharedPreferences.getString(
                 eq(SharedPrefsHelperSettings.NOTIFICATION_TIME),
                 eq("12:00")
             )
         }
     }

     @Test
     fun `test set NOTIFICATION_TIME`() {
         val value = "notification Time"
         sharedPrefsHelper?.setNotificationTime(value)
         coVerify {
             mockSharedPreferencesEditor.putString(
                 SharedPrefsHelperSettings.NOTIFICATION_TIME,
                 value
             )
         }
     }

     @Test
     fun `test get NOTIFICATION_TEXT`() {
         val value = "notification text"
         coEvery { mockSharedPreferences.getString(any(), any()) } returns value
         assertEquals(value, sharedPrefsHelper?.getNotificationText())
         coVerify {
             mockSharedPreferences.getString(
                 eq(SharedPrefsHelperSettings.NOTIFICATION_TEXT),
                 eq("")
             )
         }
     }

     @Test
     fun `test set NOTIFICATION_TEXT`() {
         val value = "notification text"
         sharedPrefsHelper?.setNotificationText(value)
         coVerify {
             mockSharedPreferencesEditor.putString(
                 SharedPrefsHelperSettings.NOTIFICATION_TEXT,
                 value
             )
         }
     }

     @Test
     fun `test get NOTIFICATION_SOUND_TITLE`() {
         val value = "notification sound title"
         coEvery { mockSharedPreferences.getString(any(), any()) } returns value
         assertEquals(value, sharedPrefsHelper?.getNotificationSoundTitle())
         coVerify {
             mockSharedPreferences.getString(
                 eq(SharedPrefsHelperSettings.NOTIFICATION_SOUND_TITLE),
                 eq("")
             )
         }
     }

     @Test
     fun `test set NOTIFICATION_SOUND_TITLE`() {
         val value = "notification sound title"
         sharedPrefsHelper?.setNotificationSoundTitle(value)
         coVerify {
             mockSharedPreferencesEditor.putString(
                 SharedPrefsHelperSettings.NOTIFICATION_SOUND_TITLE,
                 value
             )
         }
     }

     @Test
     fun `test get COUNTDOWN_LENGTH`() {
         val value = 12345L
         coEvery { mockSharedPreferences.getLong(any(), any()) } returns value
         assertEquals(value, sharedPrefsHelper?.getCountDownLength())
         coVerify {
             mockSharedPreferences.getLong(
                 eq(SharedPrefsHelperSettings.COUNTDOWN_LENGTH),
                 eq(5000L)
             )
         }
     }

     @Test
     fun `test set COUNTDOWN_LENGTH`() {
         val value = 12345L
         sharedPrefsHelper?.setCountDownLength(value)
         coVerify {
             mockSharedPreferencesEditor.putLong(
                 SharedPrefsHelperSettings.COUNTDOWN_LENGTH,
                 value
             )
         }
     }

     @Test
     fun `test get DEFAULT_NIGHT_MODE`() {
         val value = 12345
         coEvery { mockSharedPreferences.getInt(any(), any()) } returns value
         assertEquals(value, sharedPrefsHelper?.getDefaultNightMode())
         coVerify {
             mockSharedPreferences.getInt(
                 eq(SharedPrefsHelperSettings.DEFAULT_NIGHT_MODE),
                 eq(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
             )
         }
     }

     @Test
     fun `test set DEFAULT_NIGHT_MODE`() {
         val value = 12345
         sharedPrefsHelper?.setDefaultNightMode(value)
         coVerify {
             mockSharedPreferencesEditor.putInt(
                 SharedPrefsHelperSettings.DEFAULT_NIGHT_MODE,
                 value
             )
         }
     }

}
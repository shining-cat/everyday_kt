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
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM

object SharedPrefsHelperSettings {

    const val NAME = "everyday.prefs"

    const val KEEP_SCREEN_ON = "sessions.keep.screen.on"
    const val DO_NOT_DISTURB = "sessions.do.not.disturb"
    const val AEROPLANE_MODE_REMINDER = "sessions.aeroplane.mode.reminder"
    const val NOTIFICATION_ACTIVATED = "notification.activated"
    const val NOTIFICATION_TIME = "notification.time"
    const val NOTIFICATION_TEXT = "notification.text"
    const val NOTIFICATION_SOUND_URI = "notification.sound.uri"
    const val NOTIFICATION_SOUND_TITLE = "notification.sound.title"
    const val INFINITE_SESSION = "customization.infinite.session"
    const val COUNTDOWN_LENGTH = "customization.countdown.length"
    const val DEFAULT_NIGHT_MODE = "customization.default.night.mode"
    const val REWARDS_ACTIVATED = "customization.rewards.activated"
    const val STATISTICS_ACTIVATED = "customization.stats.activated"
}

class SharedPrefsHelper(private val sharedPreferences: SharedPreferences) {

    // only getters for these settings as they are handled by native jetpack preferences
    fun getKeepScreenOn(): Boolean {
        return sharedPreferences.getBoolean(
            SharedPrefsHelperSettings.KEEP_SCREEN_ON,
            false
        )
    }

    fun getDoNotDisturb(): Boolean {
        return sharedPreferences.getBoolean(
            SharedPrefsHelperSettings.DO_NOT_DISTURB,
            false
        )
    }

    fun getPlaneModeReminder(): Boolean {
        return sharedPreferences.getBoolean(
            SharedPrefsHelperSettings.AEROPLANE_MODE_REMINDER,
            false
        )
    }

    fun getNotificationActivated(): Boolean {
        return sharedPreferences.getBoolean(
            SharedPrefsHelperSettings.NOTIFICATION_ACTIVATED,
            false
        )
    }

    fun getInfiniteSession(): Boolean {
        return sharedPreferences.getBoolean(
            SharedPrefsHelperSettings.INFINITE_SESSION,
            false
        )
    }

    fun getRewardsActivated(): Boolean {
        return sharedPreferences.getBoolean(
            SharedPrefsHelperSettings.REWARDS_ACTIVATED,
            true
        )
    }

    fun getStatisticsActivated(): Boolean {
        return sharedPreferences.getBoolean(
            SharedPrefsHelperSettings.STATISTICS_ACTIVATED,
            true
        )
    }

    // needed to customize these settings UI, so needed custom setters too
    fun getNotificationTime(): String {
        return sharedPreferences.getString(
            SharedPrefsHelperSettings.NOTIFICATION_TIME,
            "12:00"
        ) ?: "12:00"
    }

    fun setNotificationTime(notificationTime: String) {
        sharedPreferences.edit()
            .putString(SharedPrefsHelperSettings.NOTIFICATION_TIME, notificationTime).apply()
    }

    fun getNotificationText(): String {
        return sharedPreferences.getString(
            SharedPrefsHelperSettings.NOTIFICATION_TEXT,
            ""
        )
            ?: "" // TODO: check if empty on app init, and if it is, set to R.string.preference_notification_text to handle translation
    }

    fun setNotificationText(notificationText: String) {
        sharedPreferences.edit()
            .putString(SharedPrefsHelperSettings.NOTIFICATION_TEXT, notificationText).apply()
    }

    fun getNotificationSoundUri(): String {
        return sharedPreferences.getString(
            SharedPrefsHelperSettings.NOTIFICATION_SOUND_URI,
            "" // "" means Silent
        ) ?: ""
    } // TODO: check if empty on app init, and if it is, set to RingtoneManager.getDefaultUri(mRingtoneType)

    fun setNotificationSoundUri(selectedRingtoneUri: String) {
        sharedPreferences.edit()
            .putString(SharedPrefsHelperSettings.NOTIFICATION_SOUND_URI, selectedRingtoneUri)
            .apply()
    }

    fun getNotificationSoundTitle(): String {
        return sharedPreferences.getString(
            SharedPrefsHelperSettings.NOTIFICATION_SOUND_TITLE,
            ""
        ) ?: ""
    } // TODO: check if empty on app init, and if it is, set to set to R.string.silence to handle translation

    fun setNotificationSoundTitle(selectedRingtoneTitle: String) {
        sharedPreferences.edit()
            .putString(SharedPrefsHelperSettings.NOTIFICATION_SOUND_TITLE, selectedRingtoneTitle)
            .apply()
    }

    fun getCountDownLength(): Long {
        return sharedPreferences.getLong(
            SharedPrefsHelperSettings.COUNTDOWN_LENGTH,
            5000L
        )
    }

    fun setCountDownLength(length: Long) {
        sharedPreferences.edit()
            .putLong(SharedPrefsHelperSettings.COUNTDOWN_LENGTH, length).apply()
    }

    fun getDefaultNightMode(): Int {
        return sharedPreferences.getInt(
            SharedPrefsHelperSettings.DEFAULT_NIGHT_MODE,
            MODE_NIGHT_FOLLOW_SYSTEM
        )
    }

    fun setDefaultNightMode(defaultNightMode: Int) {
        sharedPreferences.edit()
            .putInt(SharedPrefsHelperSettings.DEFAULT_NIGHT_MODE, defaultNightMode).apply()
    }
}

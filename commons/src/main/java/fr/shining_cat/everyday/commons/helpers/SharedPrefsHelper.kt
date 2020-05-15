package fr.shining_cat.everyday.commons.helpers

import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM

object SharedPrefsHelperSettings {
    const val NAME = "everyday.prefs"

    const val KEEP_SCREEN_ON = "sessions.keep.screen.on"
    const val DO_NOT_DISTURB = "sessions.do.not.disturb"
    const val PLANE_MODE_REMINDER = "sessions.plane.mode.reminder"
    const val NOTIFICATION_ACTIVATED = "notification.activated"
    const val NOTIFICATION_TIME = "notification.time"
    const val NOTIFICATION_TEXT = "notification.text"
    const val NOTIFICATION_SOUND = "notification.sound"
    const val INFINITE_SESSION = "customization.infinite.session"
    const val DEFAULT_NIGHT_MODE = "customization.default.night.mode"
    const val REWARDS_ACTIVATED = "customization.rewards.activated"
    const val STATISTICS_ACTIVATED = "customization.stats.activated"

}

//SharedPreferences are set through a PreferenceFragmentCompat in fr.shining_cat.everyday.settings.views.SettingsFragment, so we only handle getters here
class SharedPrefsHelper(private val sharedPreferences: SharedPreferences) {

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
            SharedPrefsHelperSettings.PLANE_MODE_REMINDER,
            false
        )
    }

    fun getNotificationActivated(): Boolean {
        return sharedPreferences.getBoolean(
            SharedPrefsHelperSettings.NOTIFICATION_ACTIVATED,
            false
        )
    }

    fun getNotificationTime(): Long {
        return sharedPreferences.getLong(
            SharedPrefsHelperSettings.NOTIFICATION_TIME,
            0L //TODO: set default value to 12:00
        )
    }

    fun getNotificationText(): String {
        return sharedPreferences.getString(
            SharedPrefsHelperSettings.NOTIFICATION_TEXT,
            ""
        )
            ?: ""//TODO: check if empty on app init, and if it is, set to R.string.preference_notification_text to handle translation
    }

    fun setNotificationText(notificationText: String) {
        sharedPreferences.edit()
            .putString(SharedPrefsHelperSettings.NOTIFICATION_TEXT, notificationText).apply()
    }

    fun getNotificationSound(): String {
        return sharedPreferences.getString(
            SharedPrefsHelperSettings.NOTIFICATION_SOUND,
            "" //"" means Silent
        ) ?: ""
    }

    fun getInfiniteSession(): Boolean {
        return sharedPreferences.getBoolean(
            SharedPrefsHelperSettings.INFINITE_SESSION,
            false
        )
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
}
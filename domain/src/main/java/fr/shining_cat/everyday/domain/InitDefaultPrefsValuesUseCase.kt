package fr.shining_cat.everyday.domain

import android.content.Context
import android.net.Uri
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelper

class InitDefaultPrefsValuesUseCase(
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val logger: Logger
) {

    private val LOG_TAG = InitDefaultPrefsValuesUseCase::class.java.name

    suspend fun execute(context: Context, deviceDefaultRingtoneUri: Uri, deviceDefaultRingtoneTitle: String) {
        checkNotificationTextInit(context)
        checkNotificationSoundDefaultUriSet(deviceDefaultRingtoneUri)
        checkNotificationSoundDefaultTitleSet(deviceDefaultRingtoneTitle)
    }

    private fun checkNotificationTextInit(context: Context) {
        val notificationTextInPrefs = sharedPrefsHelper.getNotificationText()
        if (notificationTextInPrefs.isBlank()) {
            sharedPrefsHelper.setNotificationText(context.resources.getString(R.string.notificationsPreferences_notification_text_default_text))
        }
    }

    private fun checkNotificationSoundDefaultUriSet(deviceDefaultRingtoneUri: Uri) {
        val notificationSoundDefaultUriInPrefs = sharedPrefsHelper.getNotificationSoundUri()
        if (notificationSoundDefaultUriInPrefs.isBlank()) {
            sharedPrefsHelper.setNotificationSoundUri(deviceDefaultRingtoneUri.toString())
        }
    }

    private fun checkNotificationSoundDefaultTitleSet(deviceDefaultRingtoneTitle: String) {
        val notificationSoundDefaultTitleInPrefs = sharedPrefsHelper.getNotificationSoundTitle()
        if (notificationSoundDefaultTitleInPrefs.isBlank()) {
            sharedPrefsHelper.setNotificationSoundTitle(deviceDefaultRingtoneTitle)
        }
    }
}
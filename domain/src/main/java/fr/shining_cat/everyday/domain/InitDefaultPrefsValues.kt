package fr.shining_cat.everyday.domain

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelper

class InitDefaultPrefsValues(
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val logger: Logger
) {

    suspend fun execute(context: Context, deviceDefaultRingtoneUri: Uri) {
        checkNotificationTextInit(context)
        checkNotificationSoundDefaultUriSet(deviceDefaultRingtoneUri)
        checkNotificationSoundDefaultTitleSet(context, deviceDefaultRingtoneUri)
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

    private fun checkNotificationSoundDefaultTitleSet(context: Context, deviceDefaultRingtoneUri: Uri) {
        val notificationSoundDefaultTitleInPrefs = sharedPrefsHelper.getNotificationSoundTitle()
        if (notificationSoundDefaultTitleInPrefs.isBlank()) {
            val deviceDefaultRingtone = RingtoneManager.getRingtone(context, deviceDefaultRingtoneUri)
            sharedPrefsHelper.setNotificationSoundTitle(deviceDefaultRingtone.getTitle(context))
        }
    }

}
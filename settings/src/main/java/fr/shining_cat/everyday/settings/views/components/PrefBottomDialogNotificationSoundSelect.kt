package fr.shining_cat.everyday.settings.views.components

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.preference.Preference
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelper
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleRingtonePicker
import fr.shining_cat.everyday.settings.R

class PrefBottomDialogNotificationSoundSelect(
    context: Context,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val fragmentManager: FragmentManager,
    private val logger: Logger
) : Preference(context) {

    private val LOG_TAG = PrefBottomDialogNotificationSoundSelect::class.java.name

    init {
        summary = sharedPrefsHelper.getNotificationSoundTitle()
        title = context.getString(R.string.notificationsPreferences_notification_sound_title)
        isIconSpaceReserved = false
    }

    override fun onClick() {
        openDialog()
    }

    private fun openDialog() {
        val selectedNotificationSoundUri = sharedPrefsHelper.getNotificationSoundUri()
        val ringtonesAssets =
            context.resources.getStringArray(fr.shining_cat.everyday.commons.R.array.ringtonesAssetsNames)
        val ringtonesTitles =
            context.resources.getStringArray(fr.shining_cat.everyday.commons.R.array.ringtonesTitles)
        val notificationSoundSelectDialogBottomSheetDialog =
            BottomDialogDismissibleRingtonePicker.newInstance(
                title = context.getString(R.string.notificationsPreferences_notification_sound_title),
                initialSelectionUri = selectedNotificationSoundUri,
                confirmButtonLabel = context.getString(R.string.generic_string_OK),
                showSilenceChoice = false,
                ringtonesAssetsNames = ringtonesAssets,
                ringtonesDisplayNames = ringtonesTitles
            )
        notificationSoundSelectDialogBottomSheetDialog.setBottomDialogDismissibleRingtonePickerListener(
            object :
                BottomDialogDismissibleRingtonePicker.BottomDialogDismissibleRingtonePickerListener {
                override fun onDismissed() {
                    //nothing to do here
                }

                override fun onValidateRingtoneSelected(
                    selectedRingtoneUri: String,
                    selectedRingtoneName: String
                ) {
                    sharedPrefsHelper.setNotificationSoundUri(selectedRingtoneUri)
                    sharedPrefsHelper.setNotificationSoundTitle(selectedRingtoneName)
                    summary = selectedRingtoneName
                }
            })
        notificationSoundSelectDialogBottomSheetDialog.show(
            fragmentManager,
            "openNotificationSoundSelectDialog"
        )
    }
}
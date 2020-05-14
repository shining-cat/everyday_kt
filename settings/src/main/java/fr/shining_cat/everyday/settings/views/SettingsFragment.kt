package fr.shining_cat.everyday.settings.views

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.preference.*
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelper
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelperSettings
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissableBigButton
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissableEditTextAndConfirm
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissableMessageAndConfirm
import fr.shining_cat.everyday.settings.R
import org.koin.android.ext.android.get

class SettingsFragment : PreferenceFragmentCompat() {

    private val LOG_TAG = SettingsFragment::class.java.name
    private val logger: Logger = get()
    private val sharedPrefsHelper: SharedPrefsHelper = get()

    private lateinit var notificationTimePreference: SwitchPreferenceCompat
    private lateinit var notificationTextPreference: Preference
    private lateinit var notificationSoundPreference: SwitchPreferenceCompat

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        //Set the name of the SharedPreferences file used to be ours instead of default
        preferenceManager.sharedPreferencesName = SharedPrefsHelperSettings.NAME
        //
        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)
        //
        setupSessionsPreferences(context, screen)
        setupNotificationsPreferences(context, screen)
        setupCustomisationPreferences(context, screen)
        setupDataManagementPreferences(context, screen)
        //
        preferenceScreen = screen
    }


    private fun setupSessionsPreferences(context: Context, screen: PreferenceScreen) {
        val keepScreenOnPreference = SwitchPreferenceCompat(context)
        keepScreenOnPreference.key = SharedPrefsHelperSettings.KEEP_SCREEN_ON
        keepScreenOnPreference.title = getString(R.string.keepScreenOnPreference_title)
        keepScreenOnPreference.summaryOff =
            getString(R.string.keepScreenOnPreference_switch_off_text)
        keepScreenOnPreference.summaryOn = getString(R.string.keepScreenOnPreference_switch_on_text)
        keepScreenOnPreference.setDefaultValue(false)
        keepScreenOnPreference.isIconSpaceReserved = false
        //
        //TODO:will need permission to alter do not disturb mode
        val doNotDisturbPreference = SwitchPreferenceCompat(context)
        doNotDisturbPreference.key = SharedPrefsHelperSettings.DO_NOT_DISTURB
        doNotDisturbPreference.title = getString(R.string.doNotDisturbPreference_title)
        doNotDisturbPreference.summaryOff =
            getString(R.string.doNotDisturbPreference_switch_off_text)
        doNotDisturbPreference.summaryOn = getString(R.string.doNotDisturbPreference_switch_on_text)
        doNotDisturbPreference.setDefaultValue(false)
        doNotDisturbPreference.isIconSpaceReserved = false
        //
        val planeModePreference = SwitchPreferenceCompat(context)
        planeModePreference.key = SharedPrefsHelperSettings.PLANE_MODE_REMINDER
        planeModePreference.title = getString(R.string.planeModePreference_title)
        planeModePreference.summaryOff = getString(R.string.planeModePreference_switch_off_text)
        planeModePreference.summaryOn = getString(R.string.planeModePreference_switch_on_text)
        planeModePreference.setDefaultValue(false)
        planeModePreference.isIconSpaceReserved = false
        //
        val sessionsCategory = PreferenceCategory(context)
        sessionsCategory.title = getString(R.string.sessionsPreferencesCategory_title)
        sessionsCategory.isIconSpaceReserved = false
        screen.addPreference(sessionsCategory)
        sessionsCategory.addPreference(keepScreenOnPreference)
        sessionsCategory.addPreference(doNotDisturbPreference)
        sessionsCategory.addPreference(planeModePreference)
    }

    /////////////////
    private fun setupNotificationsPreferences(context: Context, screen: PreferenceScreen) {
        val notificationActivatedPreference = SwitchPreferenceCompat(context)
        notificationActivatedPreference.key = SharedPrefsHelperSettings.NOTIFICATION_ACTIVATED
        notificationActivatedPreference.title =
            getString(R.string.notificationsPreferences_notification_activated_title)
        notificationActivatedPreference.isIconSpaceReserved = false
        notificationActivatedPreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                updateSubNotificationPreferences(newValue as Boolean)
                true
            }
        //
        notificationTimePreference = SwitchPreferenceCompat(context)
        notificationTimePreference.key = SharedPrefsHelperSettings.NOTIFICATION_TIME
        notificationTimePreference.title =
            getString(R.string.notificationsPreferencesCategory_title)
        notificationTimePreference.summaryOff =
            getString(R.string.notificationsPreferencesCategory_title)
        notificationTimePreference.summaryOn =
            getString(R.string.notificationsPreferencesCategory_title)
        notificationTimePreference.setDefaultValue(false)
        notificationTimePreference.isIconSpaceReserved = false
        //
        notificationTextPreference = Preference(context)
        notificationTextPreference.key = SharedPrefsHelperSettings.NOTIFICATION_TEXT
        notificationTextPreference.title =
            getString(R.string.notificationsPreferences_notification_text_title)
        notificationTextPreference.summary = getNotificationTextDisplay()
        notificationTextPreference.isIconSpaceReserved = false
        notificationTextPreference.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openNotificationTextInputDialog()
                true
            }
        //
        notificationSoundPreference = SwitchPreferenceCompat(context)
        notificationSoundPreference.key = SharedPrefsHelperSettings.NOTIFICATION_SOUND
        notificationSoundPreference.title =
            getString(R.string.notificationsPreferencesCategory_title)
        notificationSoundPreference.summaryOff =
            getString(R.string.notificationsPreferencesCategory_title)
        notificationSoundPreference.summaryOn =
            getString(R.string.notificationsPreferencesCategory_title)
        notificationSoundPreference.setDefaultValue(false)
        notificationSoundPreference.isIconSpaceReserved = false
        //
        val notificationsCategory = PreferenceCategoryLongSummary(context)
        notificationsCategory.title = getString(R.string.notificationsPreferencesCategory_title)
        notificationsCategory.summary = getString(R.string.notificationsPreferences_summary)
        notificationsCategory.isIconSpaceReserved = false
        notificationsCategory.isSingleLineTitle = false
        screen.addPreference(notificationsCategory)
        notificationsCategory.addPreference(notificationActivatedPreference)
        notificationsCategory.addPreference(notificationTimePreference)
        notificationsCategory.addPreference(notificationTextPreference)
        notificationsCategory.addPreference(notificationSoundPreference)
        //
        updateSubNotificationPreferences(sharedPrefsHelper.getNotificationActivated())
    }

    private fun updateSubNotificationPreferences(notificationActivated: Boolean) {
        logger.d(LOG_TAG, "updateSubNotificationPreferences::$notificationActivated")
        notificationTimePreference.isEnabled = notificationActivated
        notificationTextPreference.isEnabled = notificationActivated
        notificationSoundPreference.isEnabled = notificationActivated
        notificationTimePreference.isVisible = notificationActivated
        notificationTextPreference.isVisible = notificationActivated
        notificationSoundPreference.isVisible = notificationActivated
    }

    private fun getNotificationTextDisplay(): String{
        val presetNotificationText = sharedPrefsHelper.getNotificationText()
        return if (presetNotificationText.isNotBlank()) presetNotificationText
            else getString(R.string.notificationsPreferences_notification_text_default_text)
    }

    private fun openNotificationTextInputDialog() {
        val openNotificationTextInputBottomSheetDialog =
            BottomDialogDismissableEditTextAndConfirm.newInstance(
                getString(R.string.notificationsPreferences_notification_text_title),
                getNotificationTextDisplay(),
                getString(R.string.generic_string_OK)
            )
        openNotificationTextInputBottomSheetDialog.setBottomDialogDismissableMessageAndConfirmListener(
            object :
                BottomDialogDismissableEditTextAndConfirm.BottomDialogDismissableEditTextAndConfirmListener {
                override fun onDismissed() {
                    //nothing to do here
                }

                override fun onValidateInputText(inputText: String) {
                    sharedPrefsHelper.setNotificationText(inputText)
                    notificationTextPreference.summary = inputText
                }
            })
        openNotificationTextInputBottomSheetDialog.show(parentFragmentManager, "openNotificationTextInputDialog")
    }

    /////////////////
    private fun setupCustomisationPreferences(context: Context, screen: PreferenceScreen) {
        val defaultNightModePreference = ListPreference(context)
        defaultNightModePreference.key = SharedPrefsHelperSettings.DEFAULT_NIGHT_MODE
        defaultNightModePreference.title = getString(R.string.defaultNightModePreference_title)
        val defaultNightModeDisplayValues = arrayOf(
            getString(R.string.defaultNightModePreference_follow_system),
            getString(R.string.defaultNightModePreference_always_dark),
            getString(R.string.defaultNightModePreference_always_light)
        )
        val defaultNightModeSavedValues = arrayOf(
            MODE_NIGHT_FOLLOW_SYSTEM.toString(),
            MODE_NIGHT_YES.toString(),
            MODE_NIGHT_NO.toString()
        )
        val selectedNightMode = sharedPrefsHelper.getDefaultNightMode()
        val selectedNightModeDisplay =
            defaultNightModeDisplayValues[defaultNightModeSavedValues.indexOf(selectedNightMode.toString())]
        defaultNightModePreference.summary = selectedNightModeDisplay
        defaultNightModePreference.dialogTitle =
            getString(R.string.defaultNightModePreference_title)
        defaultNightModePreference.isIconSpaceReserved = false
        defaultNightModePreference.entries = defaultNightModeDisplayValues
        defaultNightModePreference.entryValues = defaultNightModeSavedValues
        defaultNightModePreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                setDefaultNightMode(Integer.valueOf(newValue as String))
                true
            }
        //
        val infiniteSessionPreference = SwitchPreferenceCompat(context)
        infiniteSessionPreference.key = SharedPrefsHelperSettings.INFINITE_SESSION
        infiniteSessionPreference.title =
            getString(R.string.infiniteSessionPreferencePreference_title)
        infiniteSessionPreference.summaryOff =
            getString(R.string.infiniteSessionPreferencePreference_switch_off_text)
        infiniteSessionPreference.summaryOn =
            getString(R.string.infiniteSessionPreferencePreference_switch_on_text)
        infiniteSessionPreference.setDefaultValue(false)
        infiniteSessionPreference.isIconSpaceReserved = false
        //
        val rewardsActivationPreference = SwitchPreferenceCompat(context)
        rewardsActivationPreference.key = SharedPrefsHelperSettings.REWARDS_ACTIVATED
        rewardsActivationPreference.title = getString(R.string.rewardsActivationPreference_title)
        rewardsActivationPreference.setDefaultValue(true)
        rewardsActivationPreference.isIconSpaceReserved = false
        //
        val statisticsActivationPreference = SwitchPreferenceCompat(context)
        statisticsActivationPreference.key = SharedPrefsHelperSettings.STATISTICS_ACTIVATED
        statisticsActivationPreference.title =
            getString(R.string.statisticsActivationPreference_title)
        statisticsActivationPreference.setDefaultValue(true)
        statisticsActivationPreference.isIconSpaceReserved = false
        //
        val customisationCategory = PreferenceCategory(context)
        customisationCategory.title = getString(R.string.customisationPreferencesCategory_title)
        customisationCategory.isIconSpaceReserved = false
        screen.addPreference(customisationCategory)
        customisationCategory.addPreference(defaultNightModePreference)
        customisationCategory.addPreference(infiniteSessionPreference)
        customisationCategory.addPreference(rewardsActivationPreference)
        customisationCategory.addPreference(statisticsActivationPreference)
    }

    /////////////////
    private fun setupDataManagementPreferences(context: Context, screen: PreferenceScreen) {
        val importSessionsPreference = Preference(context)
        importSessionsPreference.title = getString(R.string.importSessionsPreference_title)
        importSessionsPreference.isIconSpaceReserved = false
        importSessionsPreference.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openImportSessionsDialog()
                true
            }
        //
        val exportSessionsPreference = Preference(context)
        exportSessionsPreference.title = getString(R.string.exportSessionsPreference_title)
        exportSessionsPreference.isIconSpaceReserved = false
        exportSessionsPreference.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openExportSessionsDialog()
                true
            }
        //
        val eraseDataPreference = Preference(context)
        eraseDataPreference.title = getString(R.string.eraseDataPreference_title)
        eraseDataPreference.isIconSpaceReserved = false
        eraseDataPreference.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openEraseAllDataDialog()
                true
            }
        //
        val customisationCategory = PreferenceCategory(context)
        customisationCategory.title = getString(R.string.dataManagementPreferencesCategory_title)
        customisationCategory.isIconSpaceReserved = false
        screen.addPreference(customisationCategory)
        customisationCategory.addPreference(importSessionsPreference)
        customisationCategory.addPreference(exportSessionsPreference)
        customisationCategory.addPreference(eraseDataPreference)
    }

    private fun openExportSessionsDialog() {
        val openExportSessionsBottomSheetDialog =
            BottomDialogDismissableMessageAndConfirm.newInstance(
                getString(R.string.exportSessionsPreference_dialog_title),
                getString(R.string.exportSessionsPreference_dialog_message),
                getString(R.string.exportSessionsPreference_dialog_confirm_button)
            )
        openExportSessionsBottomSheetDialog.setBottomDialogDismissableMessageAndConfirmListener(
            object :
                BottomDialogDismissableMessageAndConfirm.BottomDialogDismissableMessageAndConfirmListener {
                override fun onDismissed() {
                    //nothing to do here
                }

                override fun onConfirmButtonClicked() {
                    //TODO: call export sessions Usecase
                    logger.d(
                        LOG_TAG,
                        "openExportSessionsDialog::onConfirmButtonClicked::TODO: call export sessions Usecase"
                    )
                }
            })
        openExportSessionsBottomSheetDialog.show(parentFragmentManager, "openExportSessionsDialog")

    }

    private fun openImportSessionsDialog() {
        val openImportSessionsBottomSheetDialog =
            BottomDialogDismissableMessageAndConfirm.newInstance(
                getString(R.string.importSessionsPreference_dialog_title),
                getString(R.string.importSessionsPreference_dialog_message),
                getString(R.string.importSessionsPreference_dialog_confirm_button)
            )
        openImportSessionsBottomSheetDialog.setBottomDialogDismissableMessageAndConfirmListener(
            object :
                BottomDialogDismissableMessageAndConfirm.BottomDialogDismissableMessageAndConfirmListener {
                override fun onDismissed() {
                    //nothing to do here
                }

                override fun onConfirmButtonClicked() {
                    //TODO: call import sessions Usecase
                    logger.d(
                        LOG_TAG,
                        "openImportSessionsDialog::onConfirmButtonClicked::TODO: call import sessions Usecase"
                    )
                }
            })
        openImportSessionsBottomSheetDialog.show(parentFragmentManager, "openImportSessionsDialog")
    }

    private fun openEraseAllDataDialog() {
        val openEraseAllDataBottomSheetDialog = BottomDialogDismissableBigButton.newInstance(
            getString(R.string.confirm_suppress),
            getString(R.string.generic_string_DELETE)
        )
        openEraseAllDataBottomSheetDialog.setBottomDialogDismissableBigButtonListener(object :
            BottomDialogDismissableBigButton.BottomDialogDismissableBigButtonListener {
            override fun onDismissed() {
                //nothing to do here
            }

            override fun onBigButtonClicked() {
                //TODO: call Erase all data Usecase
                logger.d(
                    LOG_TAG,
                    "openEraseAllDataDialog::onBigButtonClicked::TODO: call Erase all data Usecase"
                )
            }

        })
        openEraseAllDataBottomSheetDialog.show(parentFragmentManager, "openEraseAllDataDialog")
    }

}

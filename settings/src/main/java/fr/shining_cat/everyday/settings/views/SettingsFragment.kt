package fr.shining_cat.everyday.settings.views

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelper
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelperSettings
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleBigButton
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleMessageAndConfirm
import fr.shining_cat.everyday.settings.R
import fr.shining_cat.everyday.settings.views.components.*
import org.koin.android.ext.android.get

class SettingsFragment : PreferenceFragmentCompat() {

    private val LOG_TAG = SettingsFragment::class.java.name
    private val logger: Logger = get()
    private val sharedPrefsHelper: SharedPrefsHelper = get()

    private lateinit var prefBottomDialogBuilder: PrefBottomDialogBuilder
    private lateinit var notificationTextPref: PrefBottomDialogNotificationEditText
    private lateinit var notificationSoundPref: PrefBottomDialogNotificationSoundSelect
    private lateinit var notificationTimePref: PrefBottomDialogNotificationTimePicker
    private lateinit var defaultNightModePreference: Preference
    private lateinit var startCountDownLengthPreference: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        //Set the name of the SharedPreferences file to be ours instead of default
        preferenceManager.sharedPreferencesName = SharedPrefsHelperSettings.NAME
        //
        val prefContext = preferenceManager.context
        preferenceScreen = preferenceManager.createPreferenceScreen(prefContext)
        prefBottomDialogBuilder = PrefBottomDialogBuilder(
            prefContext,
            sharedPrefsHelper,
            parentFragmentManager,
            logger
        )
        //
        setupSessionsPreferences()
        setupNotificationsPreferences()
        setupCustomisationPreferences()
        setupDataManagementPreferences()
    }

    private fun setupSessionsPreferences() {
        val prefContext = preferenceManager.context
        val keepScreenOnPreference = SwitchPreferenceCompat(prefContext)
        keepScreenOnPreference.key = SharedPrefsHelperSettings.KEEP_SCREEN_ON
        keepScreenOnPreference.title = getString(R.string.keepScreenOnPreference_title)
        keepScreenOnPreference.summaryOff =
            getString(R.string.keepScreenOnPreference_switch_off_text)
        keepScreenOnPreference.summaryOn = getString(R.string.keepScreenOnPreference_switch_on_text)
        keepScreenOnPreference.setDefaultValue(false)
        keepScreenOnPreference.isIconSpaceReserved = false
        //
        //TODO:will need permission to alter do not disturb mode
        val doNotDisturbPreference = SwitchPreferenceCompat(prefContext)
        doNotDisturbPreference.key = SharedPrefsHelperSettings.DO_NOT_DISTURB
        doNotDisturbPreference.title = getString(R.string.doNotDisturbPreference_title)
        doNotDisturbPreference.summaryOff =
            getString(R.string.doNotDisturbPreference_switch_off_text)
        doNotDisturbPreference.summaryOn = getString(R.string.doNotDisturbPreference_switch_on_text)
        doNotDisturbPreference.setDefaultValue(false)
        doNotDisturbPreference.isIconSpaceReserved = false
        //
        val planeModePreference = SwitchPreferenceCompat(prefContext)
        planeModePreference.key = SharedPrefsHelperSettings.PLANE_MODE_REMINDER
        planeModePreference.title = getString(R.string.planeModePreference_title)
        planeModePreference.summaryOff = getString(R.string.planeModePreference_switch_off_text)
        planeModePreference.summaryOn = getString(R.string.planeModePreference_switch_on_text)
        planeModePreference.setDefaultValue(false)
        planeModePreference.isIconSpaceReserved = false
        //
        val sessionsCategory = PreferenceCategory(prefContext)
        sessionsCategory.title = getString(R.string.sessionsPreferencesCategory_title)
        sessionsCategory.isIconSpaceReserved = false
        preferenceScreen.addPreference(sessionsCategory)
        sessionsCategory.addPreference(keepScreenOnPreference)
        sessionsCategory.addPreference(doNotDisturbPreference)
        sessionsCategory.addPreference(planeModePreference)
    }

    /////////////////
    private fun setupNotificationsPreferences() {
        val prefContext = preferenceManager.context
        val notificationActivatedPreference = SwitchPreferenceCompat(prefContext)
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
        notificationTimePref = prefBottomDialogBuilder.buildPrefBottomDialogNotificationTimePicker()
        //
        notificationTextPref = prefBottomDialogBuilder.buildPrefBottomDialogNotificationEditText()
        //
        notificationSoundPref =
            prefBottomDialogBuilder.buildPrefBottomDialogNotificationSoundSelect()
        //
        val notificationsCategory =
            PreferenceCategoryLongSummary(
                prefContext
            )
        notificationsCategory.title = getString(R.string.notificationsPreferencesCategory_title)
        notificationsCategory.summary = getString(R.string.notificationsPreferences_summary)
        notificationsCategory.isIconSpaceReserved = false
        notificationsCategory.isSingleLineTitle = false
        preferenceScreen.addPreference(notificationsCategory)
        notificationsCategory.addPreference(notificationActivatedPreference)
        notificationsCategory.addPreference(notificationTimePref)
        notificationsCategory.addPreference(notificationTextPref)
        notificationsCategory.addPreference(notificationSoundPref)
        //
        updateSubNotificationPreferences(sharedPrefsHelper.getNotificationActivated())
    }

    private fun updateSubNotificationPreferences(notificationActivated: Boolean) {
        notificationTimePref.isEnabled = notificationActivated
        notificationTextPref.isEnabled = notificationActivated
        notificationSoundPref.isEnabled = notificationActivated
        notificationTimePref.isVisible = notificationActivated
        notificationTextPref.isVisible = notificationActivated
        notificationSoundPref.isVisible = notificationActivated
    }

    /////////////////

    private fun setupCustomisationPreferences() {
        val prefContext = preferenceManager.context
        //
        defaultNightModePreference =
            prefBottomDialogBuilder.buildPrefBottomDialogDefaultNightModeSelect()
        defaultNightModePreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, _ ->
                //force refresh of parent activity to apply theme choice
                activity?.recreate()
                true
            }
        //
        startCountDownLengthPreference = prefBottomDialogBuilder.buildPrefBottomDialogCountdownLengthPicker()
        //
        val infiniteSessionPreference = SwitchPreferenceCompat(prefContext)
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
        val rewardsActivationPreference = SwitchPreferenceCompat(prefContext)
        rewardsActivationPreference.key = SharedPrefsHelperSettings.REWARDS_ACTIVATED
        rewardsActivationPreference.title = getString(R.string.rewardsActivationPreference_title)
        rewardsActivationPreference.setDefaultValue(true)
        rewardsActivationPreference.isIconSpaceReserved = false
        //
        val statisticsActivationPreference = SwitchPreferenceCompat(prefContext)
        statisticsActivationPreference.key = SharedPrefsHelperSettings.STATISTICS_ACTIVATED
        statisticsActivationPreference.title =
            getString(R.string.statisticsActivationPreference_title)
        statisticsActivationPreference.setDefaultValue(true)
        statisticsActivationPreference.isIconSpaceReserved = false
        //
        val customisationCategory = PreferenceCategory(prefContext)
        customisationCategory.title = getString(R.string.customisationPreferencesCategory_title)
        customisationCategory.isIconSpaceReserved = false
        preferenceScreen.addPreference(customisationCategory)
        customisationCategory.addPreference(defaultNightModePreference)
        customisationCategory.addPreference(startCountDownLengthPreference)
        customisationCategory.addPreference(infiniteSessionPreference)
        customisationCategory.addPreference(rewardsActivationPreference)
        customisationCategory.addPreference(statisticsActivationPreference)
    }

   /////////////////
    private fun setupDataManagementPreferences() {
        val prefContext = preferenceManager.context
        val importSessionsPreference = Preference(prefContext)
        importSessionsPreference.title = getString(R.string.importSessionsPreference_title)
        importSessionsPreference.isIconSpaceReserved = false
        importSessionsPreference.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openImportSessionsDialog()
                true
            }
        //
        val exportSessionsPreference = Preference(prefContext)
        exportSessionsPreference.title = getString(R.string.exportSessionsPreference_title)
        exportSessionsPreference.isIconSpaceReserved = false
        exportSessionsPreference.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openExportSessionsDialog()
                true
            }
        //
        val eraseDataPreference = Preference(prefContext)
        eraseDataPreference.title = getString(R.string.eraseDataPreference_title)
        eraseDataPreference.isIconSpaceReserved = false
        eraseDataPreference.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openEraseAllDataDialog()
                true
            }
        //
        val customisationCategory = PreferenceCategory(prefContext)
        customisationCategory.title = getString(R.string.dataManagementPreferencesCategory_title)
        customisationCategory.isIconSpaceReserved = false
        preferenceScreen.addPreference(customisationCategory)
        customisationCategory.addPreference(importSessionsPreference)
        customisationCategory.addPreference(exportSessionsPreference)
        customisationCategory.addPreference(eraseDataPreference)
    }

    private fun openExportSessionsDialog() {
        val exportSessionsBottomSheetDialog =
            BottomDialogDismissibleMessageAndConfirm.newInstance(
                getString(R.string.exportSessionsPreference_dialog_title),
                getString(R.string.exportSessionsPreference_dialog_message),
                getString(R.string.exportSessionsPreference_dialog_confirm_button)
            )
        exportSessionsBottomSheetDialog.setBottomDialogDismissibleMessageAndConfirmListener(
            object :
                BottomDialogDismissibleMessageAndConfirm.BottomDialogDismissibleMessageAndConfirmListener {
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
        exportSessionsBottomSheetDialog.show(parentFragmentManager, "openExportSessionsDialog")

    }

    private fun openImportSessionsDialog() {
        val importSessionsBottomSheetDialog =
            BottomDialogDismissibleMessageAndConfirm.newInstance(
                getString(R.string.importSessionsPreference_dialog_title),
                getString(R.string.importSessionsPreference_dialog_message),
                getString(R.string.importSessionsPreference_dialog_confirm_button)
            )
        importSessionsBottomSheetDialog.setBottomDialogDismissibleMessageAndConfirmListener(
            object :
                BottomDialogDismissibleMessageAndConfirm.BottomDialogDismissibleMessageAndConfirmListener {
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
        importSessionsBottomSheetDialog.show(parentFragmentManager, "openImportSessionsDialog")
    }

    private fun openEraseAllDataDialog() {
        val eraseAllDataBottomSheetDialog = BottomDialogDismissibleBigButton.newInstance(
            getString(R.string.confirm_suppress),
            getString(R.string.generic_string_DELETE)
        )
        eraseAllDataBottomSheetDialog.setBottomDialogDismissibleBigButtonListener(object :
            BottomDialogDismissibleBigButton.BottomDialogDismissibleBigButtonListener {
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
        eraseAllDataBottomSheetDialog.show(parentFragmentManager, "openEraseAllDataDialog")
    }

}

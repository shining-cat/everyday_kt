package fr.shining_cat.everyday.settings.views

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.preference.*
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelper
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelperSettings
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleBigButton
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleMessageAndConfirm
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleSelectListAndConfirm
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleSpinnersDurationAndConfirm
import fr.shining_cat.everyday.settings.R
import fr.shining_cat.everyday.settings.views.components.PrefBottomDialogNotificationEditText
import fr.shining_cat.everyday.settings.views.components.PrefBottomDialogNotificationSoundSelect
import fr.shining_cat.everyday.settings.views.components.PrefBottomDialogNotificationTimePicker
import fr.shining_cat.everyday.settings.views.components.PreferenceCategoryLongSummary
import org.koin.android.ext.android.get

class SettingsFragment : PreferenceFragmentCompat() {

    private val LOG_TAG = SettingsFragment::class.java.name
    private val logger: Logger = get()
    private val sharedPrefsHelper: SharedPrefsHelper = get()

    private lateinit var notificationTextPreference: PrefBottomDialogNotificationEditText
    private lateinit var notificationSoundPreference: PrefBottomDialogNotificationSoundSelect
    private lateinit var defaultNightModePreference: Preference
    private lateinit var startCountDownLengthPreference: Preference
    private lateinit var notificationTimePreference: PrefBottomDialogNotificationTimePicker

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        //Set the name of the SharedPreferences file to be ours instead of default
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
        notificationTimePreference = PrefBottomDialogNotificationTimePicker(
            context,
            sharedPrefsHelper,
            parentFragmentManager,
            logger
        )
        //
        notificationTextPreference = PrefBottomDialogNotificationEditText(
            context,
            sharedPrefsHelper,
            parentFragmentManager,
            logger
        )
        //
        notificationSoundPreference = PrefBottomDialogNotificationSoundSelect(
            context,
            sharedPrefsHelper,
            parentFragmentManager,
            logger
        )
        //
        val notificationsCategory =
            PreferenceCategoryLongSummary(
                context
            )
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
        notificationTimePreference.isEnabled = notificationActivated
        notificationTextPreference.isEnabled = notificationActivated
        notificationSoundPreference.isEnabled = notificationActivated
        notificationTimePreference.isVisible = notificationActivated
        notificationTextPreference.isVisible = notificationActivated
        notificationSoundPreference.isVisible = notificationActivated
    }

    /////////////////

    private fun setupCustomisationPreferences(context: Context, screen: PreferenceScreen) {
        val defaultNightModeLabels = listOf(
            getString(R.string.defaultNightModePreference_follow_system),
            getString(R.string.defaultNightModePreference_always_dark),
            getString(R.string.defaultNightModePreference_always_light)
        )
        val defaultNightModeValues = listOf(
            MODE_NIGHT_FOLLOW_SYSTEM,
            MODE_NIGHT_YES,
            MODE_NIGHT_NO
        )
        defaultNightModePreference = Preference(context)
        defaultNightModePreference.title = getString(R.string.defaultNightModePreference_title)
        val selectedNightMode = sharedPrefsHelper.getDefaultNightMode()
        val selectedNightModeDisplay =
            defaultNightModeLabels[defaultNightModeValues.indexOf(selectedNightMode)]
        defaultNightModePreference.summary = selectedNightModeDisplay
        defaultNightModePreference.isIconSpaceReserved = false
        defaultNightModePreference.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openSelectDefaultNightModeDialog(
                    defaultNightModeLabels,
                    defaultNightModeValues
                )
                true
            }
        //
        startCountDownLengthPreference = Preference(context)
        startCountDownLengthPreference.title =
            getString(R.string.startCountDownLengthPreference_title)
        updateCountDownLengthSummary()
        startCountDownLengthPreference.isIconSpaceReserved = false
        startCountDownLengthPreference.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openSetCountDownLengthDialog()
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
        customisationCategory.addPreference(startCountDownLengthPreference)
        customisationCategory.addPreference(infiniteSessionPreference)
        customisationCategory.addPreference(rewardsActivationPreference)
        customisationCategory.addPreference(statisticsActivationPreference)
    }

    private fun openSetCountDownLengthDialog() {
        val setCountDownLengthBottomSheetDialog =
            BottomDialogDismissibleSpinnersDurationAndConfirm.newInstance(
                title = getString(R.string.startCountDownLengthPreference_title),
                showHours = false,
                showMinutes = false,
                showSeconds = true,
                explanationMessage = getString(R.string.startCountDownLengthPreference_explanation),
                confirmButtonLabel = getString(R.string.generic_string_OK),
                initialLengthMs = sharedPrefsHelper.getCountDownLength()
            )
        setCountDownLengthBottomSheetDialog.setBottomDialogDismissibleSpinnerSecondsAndConfirmListener(
            object :
                BottomDialogDismissibleSpinnersDurationAndConfirm.BottomDialogDismissibleSpinnerSecondsAndConfirmListener {
                override fun onDismissed() {
                    //nothing to do here
                }

                override fun onConfirmButtonClicked(lengthMs: Long) {
                    sharedPrefsHelper.setCountDownLength(lengthMs)
                    updateCountDownLengthSummary()
                }
            })
        setCountDownLengthBottomSheetDialog.show(parentFragmentManager, "openExportSessionsDialog")
    }

    private fun updateCountDownLengthSummary() {
        startCountDownLengthPreference.summary =
            getString(R.string.startCountDownLengthPreference_explanation) + ": " +
                    getString(R.string.startCountDownLengthPreference_value_display).format(
                        sharedPrefsHelper.getCountDownLength().toInt() / 1000
                    )
    }

    private fun openSelectDefaultNightModeDialog(
        labels: List<String>,
        androidDefaultNightModeValues: List<Int>
    ) {
        val selectedIndex =
            androidDefaultNightModeValues.indexOf(sharedPrefsHelper.getDefaultNightMode())
        val selectDefaultNightModeBottomSheetDialog =
            BottomDialogDismissibleSelectListAndConfirm.newInstance(
                getString(R.string.defaultNightModePreference_title),
                labels,
                getString(R.string.generic_string_VALIDATE),
                selectedIndex
            )
        selectDefaultNightModeBottomSheetDialog.setBottomDialogDismissibleSelectListAndConfirmListener(
            object :
                BottomDialogDismissibleSelectListAndConfirm.BottomDialogDismissibleSelectListAndConfirmListener {
                override fun onDismissed() {
                    //nothing to do here
                }

                override fun onValidateSelection(optionSelectedIndex: Int) {
                    logger.d(
                        LOG_TAG,
                        "openSelectDefaultNightModeDialog::onConfirmButtonClicked::selected index $optionSelectedIndex, displaying: ${labels[optionSelectedIndex]}"
                    )
                    val androidDefaultNightModeValue =
                        androidDefaultNightModeValues[optionSelectedIndex]
                    sharedPrefsHelper.setDefaultNightMode(androidDefaultNightModeValue)
                    setDefaultNightMode(androidDefaultNightModeValue)
                    //force refresh of parent activity to apply theme choice
                    activity?.recreate()
                    defaultNightModePreference.summary = labels[optionSelectedIndex]
                }
            })
        selectDefaultNightModeBottomSheetDialog.show(
            parentFragmentManager,
            "openSelectDefaultNightModeDialog"
        )
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

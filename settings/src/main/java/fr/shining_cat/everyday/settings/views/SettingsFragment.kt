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

package fr.shining_cat.everyday.settings.views

import android.content.Context
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelper
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelperSettings
import fr.shining_cat.everyday.settings.R
import fr.shining_cat.everyday.settings.views.components.PrefBottomDialogBuilder
import fr.shining_cat.everyday.settings.views.components.PrefBottomDialogNotificationEditText
import fr.shining_cat.everyday.settings.views.components.PrefBottomDialogNotificationSoundSelect
import fr.shining_cat.everyday.settings.views.components.PrefBottomDialogNotificationTimePicker
import fr.shining_cat.everyday.settings.views.components.PreferenceCategoryLongSummary
import org.koin.android.ext.android.get

class SettingsFragment : PreferenceFragmentCompat() {

    private val LOG_TAG = SettingsFragment::class.java.name
    private val logger: Logger = get()
    private val sharedPrefsHelper: SharedPrefsHelper = get()

    private var prefBottomDialogBuilder: PrefBottomDialogBuilder? = null
    private var notificationTextPref: PrefBottomDialogNotificationEditText? = null
    private var notificationSoundPref: PrefBottomDialogNotificationSoundSelect? = null
    private var notificationTimePref: PrefBottomDialogNotificationTimePicker? = null

    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?
    ) {
        // Set the name of the SharedPreferences file to be ours instead of default
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
        keepScreenOnPreference.summaryOff = getString(R.string.keepScreenOnPreference_switch_off_text)
        keepScreenOnPreference.summaryOn = getString(R.string.keepScreenOnPreference_switch_on_text)
        keepScreenOnPreference.setDefaultValue(false)
        keepScreenOnPreference.isIconSpaceReserved = false
        //
        // TODO:will need permission to alter do not disturb mode
        val doNotDisturbPreference = SwitchPreferenceCompat(prefContext)
        doNotDisturbPreference.key = SharedPrefsHelperSettings.DO_NOT_DISTURB
        doNotDisturbPreference.title = getString(R.string.doNotDisturbPreference_title)
        doNotDisturbPreference.summaryOff = getString(R.string.doNotDisturbPreference_switch_off_text)
        doNotDisturbPreference.summaryOn = getString(R.string.doNotDisturbPreference_switch_on_text)
        doNotDisturbPreference.setDefaultValue(false)
        doNotDisturbPreference.isIconSpaceReserved = false
        //
        val planeModePreference = SwitchPreferenceCompat(prefContext)
        planeModePreference.key = SharedPrefsHelperSettings.AEROPLANE_MODE_REMINDER
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

    // ///////////////
    private fun setupNotificationsPreferences() {
        val prefContext: Context = preferenceManager.context
        val notificationActivatedPreference = SwitchPreferenceCompat(prefContext)
        notificationActivatedPreference.key = SharedPrefsHelperSettings.NOTIFICATION_ACTIVATED
        notificationActivatedPreference.title = getString(R.string.notificationsPreferences_notification_activated_title)
        notificationActivatedPreference.isIconSpaceReserved = false
        notificationActivatedPreference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
            updateSubNotificationPreferences(newValue as Boolean)
            true
        }
        //
        val tempNotificationTimePref = prefBottomDialogBuilder?.buildPrefBottomDialogNotificationTimePicker()
        //
        val tempNotificationTextPref = prefBottomDialogBuilder?.buildPrefBottomDialogNotificationEditText()
        //
        val tempNotificationSoundPref = prefBottomDialogBuilder?.buildPrefBottomDialogNotificationSoundSelect()
        //
        if(tempNotificationTimePref == null || tempNotificationTextPref == null || tempNotificationSoundPref == null){
            logger.e(LOG_TAG, "setupNotificationsPreferences:: could not set preference because one of the m is null!")
            return
        }
        notificationTimePref = tempNotificationTimePref
        notificationTextPref = tempNotificationTextPref
        notificationSoundPref = tempNotificationSoundPref
        //
        val notificationsCategory = PreferenceCategoryLongSummary(
            prefContext
        )
        notificationsCategory.title = getString(R.string.notificationsPreferencesCategory_title)
        notificationsCategory.summary = getString(R.string.notificationsPreferences_summary)
        notificationsCategory.isIconSpaceReserved = false
        notificationsCategory.isSingleLineTitle = false
        preferenceScreen.addPreference(notificationsCategory)
        notificationsCategory.addPreference(notificationActivatedPreference)

        notificationsCategory.addPreference(tempNotificationTimePref)
        notificationsCategory.addPreference(tempNotificationTextPref)
        notificationsCategory.addPreference(tempNotificationSoundPref)
        //
        updateSubNotificationPreferences(sharedPrefsHelper.getNotificationActivated())
    }

    private fun updateSubNotificationPreferences(notificationActivated: Boolean) {
        notificationTimePref?.isEnabled = notificationActivated
        notificationTextPref?.isEnabled = notificationActivated
        notificationSoundPref?.isEnabled = notificationActivated
        notificationTimePref?.isVisible = notificationActivated
        notificationTextPref?.isVisible = notificationActivated
        notificationSoundPref?.isVisible = notificationActivated
    }

    // ///////////////

    private fun setupCustomisationPreferences() {
        val prefContext = preferenceManager.context
        //
        val defaultNightModePreference = prefBottomDialogBuilder?.buildPrefBottomDialogDefaultNightModeSelect()
        if(defaultNightModePreference == null){
            logger.e(LOG_TAG, "setupCustomisationPreferences::defaultNightModePreference is null!")
        }
        defaultNightModePreference?.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, _ ->
            // force refresh of parent activity to apply theme choice
            activity?.recreate()
            true
        }
        //
        val startCountDownLengthPreference = prefBottomDialogBuilder?.buildPrefBottomDialogCountdownLengthPicker()
        if(startCountDownLengthPreference == null){
            logger.e(LOG_TAG, "setupCustomisationPreferences::startCountDownLengthPreference is null!")
        }
        //
        val rewardsActivationPreference = SwitchPreferenceCompat(prefContext)
        rewardsActivationPreference.key = SharedPrefsHelperSettings.REWARDS_ACTIVATED
        rewardsActivationPreference.title = getString(R.string.rewardsActivationPreference_title)
        rewardsActivationPreference.setDefaultValue(true)
        rewardsActivationPreference.isIconSpaceReserved = false
        //
        val statisticsActivationPreference = SwitchPreferenceCompat(prefContext)
        statisticsActivationPreference.key = SharedPrefsHelperSettings.STATISTICS_ACTIVATED
        statisticsActivationPreference.title = getString(R.string.statisticsActivationPreference_title)
        statisticsActivationPreference.setDefaultValue(true)
        statisticsActivationPreference.isIconSpaceReserved = false
        //
        val customisationCategory = PreferenceCategory(prefContext)
        customisationCategory.title = getString(R.string.customisationPreferencesCategory_title)
        customisationCategory.isIconSpaceReserved = false
        preferenceScreen.addPreference(customisationCategory)
        defaultNightModePreference?.let {customisationCategory.addPreference(it)}
        startCountDownLengthPreference?.let {customisationCategory.addPreference(it)}
        customisationCategory.addPreference(rewardsActivationPreference)
        customisationCategory.addPreference(statisticsActivationPreference)
    }

    // ///////////////
    private fun setupDataManagementPreferences() {
        val prefContext = preferenceManager.context
        //
        val importSessionsPreference = prefBottomDialogBuilder?.buildPrefBottomDialogImportData()
        if(importSessionsPreference == null){
            logger.e(LOG_TAG, "setupDataManagementPreferences::importSessionsPreference is null!")
        }
        //
        val exportSessionsPreference = prefBottomDialogBuilder?.buildPrefBottomDialogExportData()
        if(exportSessionsPreference == null){
            logger.e(LOG_TAG, "setupDataManagementPreferences::exportSessionsPreference is null!")
        }
        //
        val eraseDataPreference = prefBottomDialogBuilder?.buildPrefBottomDialogEraseAllData()
        if(eraseDataPreference == null){
            logger.e(LOG_TAG, "setupDataManagementPreferences::eraseDataPreference is null!")
        }
        //
        val dataManagementCategory = PreferenceCategory(prefContext)
        dataManagementCategory.title = getString(R.string.dataManagementPreferencesCategory_title)
        dataManagementCategory.isIconSpaceReserved = false
        preferenceScreen.addPreference(dataManagementCategory)
        importSessionsPreference?.let {dataManagementCategory.addPreference(it)}
        exportSessionsPreference?.let {dataManagementCategory.addPreference(it)}
        eraseDataPreference?.let {dataManagementCategory.addPreference(it)}
    }
}
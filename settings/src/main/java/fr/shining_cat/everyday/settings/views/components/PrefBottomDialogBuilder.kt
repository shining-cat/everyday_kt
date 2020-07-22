package fr.shining_cat.everyday.settings.views.components

import android.content.Context
import androidx.fragment.app.FragmentManager
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelper

class PrefBottomDialogBuilder(
    private val context: Context,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val fragmentManager: FragmentManager,
    private val logger: Logger
) {

    fun buildPrefBottomDialogNotificationEditText() = PrefBottomDialogNotificationEditText(
        context,
        sharedPrefsHelper,
        fragmentManager,
        logger
    )

    fun buildPrefBottomDialogNotificationTimePicker() = PrefBottomDialogNotificationTimePicker(
        context,
        sharedPrefsHelper,
        fragmentManager,
        logger
    )

    fun buildPrefBottomDialogNotificationSoundSelect() = PrefBottomDialogNotificationSoundSelect(
        context,
        sharedPrefsHelper,
        fragmentManager,
        logger
    )

    fun buildPrefBottomDialogDefaultNightModeSelect() = PrefBottomDialogDefaultNightModeSelect(
        context,
        sharedPrefsHelper,
        fragmentManager,
        logger
    )

    fun buildPrefBottomDialogCountdownLengthPicker() = PrefBottomDialogCountdownLengthPicker(
        context,
        sharedPrefsHelper,
        fragmentManager,
        logger
    )

    fun buildPrefBottomDialogExportData() = PrefBottomDialogExportData(
        context,
        fragmentManager,
        logger
    )

    fun buildPrefBottomDialogImportData() = PrefBottomDialogImportData(
        context,
        fragmentManager,
        logger
    )

    fun buildPrefBottomDialogEraseAllData() = PrefBottomDialogEraseAllData(
        context,
        fragmentManager,
        logger
    )
}
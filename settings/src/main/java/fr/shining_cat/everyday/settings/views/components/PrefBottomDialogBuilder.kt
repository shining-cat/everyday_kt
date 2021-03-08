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
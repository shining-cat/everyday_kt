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
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentManager
import androidx.preference.Preference
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelper
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleSelectListAndConfirm
import fr.shining_cat.everyday.settings.R

class PrefBottomDialogDefaultNightModeSelect(
    context: Context,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val fragmentManager: FragmentManager,
    private val logger: Logger
) : Preference(context) {

    private val LOG_TAG = PrefBottomDialogDefaultNightModeSelect::class.java.name

    private val defaultNightModeLabels: List<String> = listOf(
        context.getString(R.string.defaultNightModePreference_follow_system),
        context.getString(R.string.defaultNightModePreference_always_dark),
        context.getString(R.string.defaultNightModePreference_always_light)
    )
    private val androidDefaultNightModeValues: List<Int> = listOf(
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
        AppCompatDelegate.MODE_NIGHT_YES,
        AppCompatDelegate.MODE_NIGHT_NO
    )

    init {
        val selectedNightMode = sharedPrefsHelper.getDefaultNightMode()
        val selectedNightModeDisplay =
            defaultNightModeLabels[androidDefaultNightModeValues.indexOf(selectedNightMode)]
        summary = selectedNightModeDisplay
        title = context.getString(R.string.defaultNightModePreference_title)
        isIconSpaceReserved = false
    }

    override fun onClick() {
        openDialog()
    }

    private fun openDialog() {
        val selectedIndex =
            androidDefaultNightModeValues.indexOf(sharedPrefsHelper.getDefaultNightMode())
        val selectDefaultNightModeBottomSheetDialog =
            BottomDialogDismissibleSelectListAndConfirm.newInstance(
                context.getString(R.string.defaultNightModePreference_title),
                defaultNightModeLabels,
                context.getString(R.string.generic_string_VALIDATE),
                selectedIndex
            )
        selectDefaultNightModeBottomSheetDialog.setBottomDialogDismissibleSelectListAndConfirmListener(
            object :
                BottomDialogDismissibleSelectListAndConfirm.BottomDialogDismissibleSelectListAndConfirmListener {
                override fun onDismissed() {
                    //nothing to do here
                }

                override fun onValidateSelection(optionSelectedIndex: Int) {
                    val androidDefaultNightModeValue =
                        androidDefaultNightModeValues[optionSelectedIndex]
                    sharedPrefsHelper.setDefaultNightMode(androidDefaultNightModeValue)
                    AppCompatDelegate.setDefaultNightMode(androidDefaultNightModeValue)
                    summary = defaultNightModeLabels[optionSelectedIndex]
                }
            })
        selectDefaultNightModeBottomSheetDialog.show(
            fragmentManager,
            "openSelectDefaultNightModeDialog"
        )
    }
}
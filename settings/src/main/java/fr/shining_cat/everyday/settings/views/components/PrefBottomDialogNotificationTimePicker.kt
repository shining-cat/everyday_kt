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
import androidx.preference.Preference
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelper
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleTimePicker
import fr.shining_cat.everyday.settings.R

class PrefBottomDialogNotificationTimePicker(
    context: Context,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val fragmentManager: FragmentManager,
    private val logger: Logger
) : Preference(context) {

    private val LOG_TAG = PrefBottomDialogNotificationTimePicker::class.java.name

    init {
        summary = sharedPrefsHelper.getNotificationTime()
        title = context.getString(R.string.notificationsPreferences_notification_time_title)
        isIconSpaceReserved = false
    }

    override fun onClick() {
        openDialog()
    }

    private fun openDialog() {
        val presetNotificationTime = sharedPrefsHelper.getNotificationTime()
        val initialHour = Integer.valueOf(presetNotificationTime.substring(0, 2))
        val initialMin = Integer.valueOf(presetNotificationTime.substring(3))
        val notificationTimeInputBottomSheetDialog =
            BottomDialogDismissibleTimePicker.newInstance(
                context.getString(R.string.notificationsPreferences_notification_time_title),
                context.getString(R.string.generic_string_OK),
                initialHour,
                initialMin
            )
        notificationTimeInputBottomSheetDialog.setBottomDialogDismissibleTimePickerListener(
            object :
                BottomDialogDismissibleTimePicker.BottomDialogDismissibleTimePickerListener {
                override fun onDismissed() {
                    //nothing to do here
                }

                override fun onConfirmButtonClicked(hour: Int, minutes: Int) {
                    val hoursAsString = String.format("%02d", hour)
                    val minutesAsString = String.format("%02d", minutes)
                    val notificationTime = "$hoursAsString:$minutesAsString"
                    sharedPrefsHelper.setNotificationTime(notificationTime)
                    summary = notificationTime
                }
            })
        notificationTimeInputBottomSheetDialog.show(
            fragmentManager,
            "openNotificationTimeInputDialog"
        )
    }
}
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
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleEditTextAndConfirm
import fr.shining_cat.everyday.settings.R

class PrefBottomDialogNotificationEditText(
    context: Context,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val fragmentManager: FragmentManager,
    private val logger: Logger
): Preference(context) {

    private val LOG_TAG = PrefBottomDialogNotificationEditText::class.java.name

    init {
        summary = getNotificationTextDisplay()
        title = context.getString(R.string.notificationsPreferences_notification_text_title)
        isIconSpaceReserved = false
    }

    private fun getNotificationTextDisplay(): String {
        val presetNotificationText = sharedPrefsHelper.getNotificationText()
        return if (presetNotificationText.isNotBlank()) presetNotificationText
        else context.getString(R.string.notificationsPreferences_notification_text_default_text)
    }

    override fun onClick() {
        openDialog()
    }

    private fun openDialog() {
        val notificationTextInputBottomSheetDialog = BottomDialogDismissibleEditTextAndConfirm.newInstance(
            context.getString(R.string.notificationsPreferences_notification_text_title),
            getNotificationTextDisplay(),
            context.getString(R.string.generic_string_OK)
        )
        notificationTextInputBottomSheetDialog.setBottomDialogDismissibleMessageAndConfirmListener(object:
            BottomDialogDismissibleEditTextAndConfirm.BottomDialogDismissibleEditTextAndConfirmListener {
            override fun onDismissed() {
                // nothing to do here
            }

            override fun onValidateInputText(inputText: String) {
                sharedPrefsHelper.setNotificationText(inputText)
                summary = inputText
            }
        })
        notificationTextInputBottomSheetDialog.show(
            fragmentManager,
            "openNotificationTextInputDialog"
        )
    }
}
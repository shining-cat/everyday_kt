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
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleMessageAndConfirm
import fr.shining_cat.everyday.settings.R

class PrefBottomDialogExportData(
    context: Context,
    private val fragmentManager: FragmentManager,
    private val logger: Logger
) : Preference(context) {

    private val LOG_TAG = PrefBottomDialogExportData::class.java.name

    init {
        title = context.getString(R.string.exportSessionsPreference_title)
        isIconSpaceReserved = false
    }

    override fun onClick() {
        openDialog()
    }

    private fun openDialog() {
        val setExportDataBottomSheetDialog =
            BottomDialogDismissibleMessageAndConfirm.newInstance(
                title = context.getString(R.string.exportSessionsPreference_dialog_title),
                message = context.getString(R.string.exportSessionsPreference_dialog_message),
                confirmButtonLabel = context.getString(R.string.exportSessionsPreference_dialog_confirm_button)
            )
        setExportDataBottomSheetDialog.setBottomDialogDismissibleMessageAndConfirmListener(
            object :
                BottomDialogDismissibleMessageAndConfirm.BottomDialogDismissibleMessageAndConfirmListener {
                override fun onDismissed() {
                    // nothing to do here
                }

                override fun onConfirmButtonClicked() {
                    // TODO: call export sessions UseCase
                    logger.e(
                        LOG_TAG,
                        "openExportSessionsDialog::onConfirmButtonClicked::TODO: call export sessions Usecase"
                    )
                }
            })
        setExportDataBottomSheetDialog.show(fragmentManager, "openExportSessionsDialog")
    }
}
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

class PrefBottomDialogImportData(
    context: Context,
    private val fragmentManager: FragmentManager,
    private val logger: Logger
) : Preference(context) {

    private val LOG_TAG = PrefBottomDialogImportData::class.java.name

    init {
        title = context.getString(R.string.importSessionsPreference_title)
        isIconSpaceReserved = false
    }

    override fun onClick() {
        openDialog()
    }

    private fun openDialog() {
        val setImportDataBottomSheetDialog =
            BottomDialogDismissibleMessageAndConfirm.newInstance(
                title = context.getString(R.string.importSessionsPreference_dialog_title),
                message = context.getString(R.string.importSessionsPreference_dialog_message),
                confirmButtonLabel = context.getString(R.string.importSessionsPreference_dialog_confirm_button)
            )
        setImportDataBottomSheetDialog.setBottomDialogDismissibleMessageAndConfirmListener(
            object :
                BottomDialogDismissibleMessageAndConfirm.BottomDialogDismissibleMessageAndConfirmListener {
                override fun onDismissed() {
                    //nothing to do here
                }

                override fun onConfirmButtonClicked() {
                    //TODO: call import sessions UseCase
                    logger.e(
                        LOG_TAG,
                        "openImportSessionsDialog::onConfirmButtonClicked::TODO: call import sessions Usecase"
                    )
                }

            })
        setImportDataBottomSheetDialog.show(fragmentManager, "openImportSessionsDialog")
    }
}
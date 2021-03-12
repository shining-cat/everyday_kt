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
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleBigButton
import fr.shining_cat.everyday.settings.R

class PrefBottomDialogEraseAllData(
    context: Context,
    private val fragmentManager: FragmentManager,
    private val logger: Logger
) : Preference(context) {

    private val LOG_TAG = PrefBottomDialogEraseAllData::class.java.name

    init {
        title = context.getString(R.string.eraseDataPreference_title)
        isIconSpaceReserved = false
    }

    override fun onClick() {
        openDialog()
    }

    private fun openDialog() {
        val eraseAllDataBottomSheetDialog = BottomDialogDismissibleBigButton.newInstance(
            title = context.getString(R.string.generic_string_CONFIRM_DELETE),
            bigButtonLabel = context.getString(R.string.generic_string_DELETE)
        )
        eraseAllDataBottomSheetDialog.setBottomDialogDismissibleBigButtonListener { // TODO: call Erase all data Usecase
            logger.e(
                LOG_TAG,
                "openEraseAllDataDialog::onBigButtonClicked"
            )
        }
        eraseAllDataBottomSheetDialog.show(
            fragmentManager,
            "openEraseAllDataDialog"
        )
    }
}
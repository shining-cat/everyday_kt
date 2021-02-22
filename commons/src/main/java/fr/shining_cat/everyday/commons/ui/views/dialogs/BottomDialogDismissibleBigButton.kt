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

package fr.shining_cat.everyday.commons.ui.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.shining_cat.everyday.commons.databinding.DialogBottomBigButtonBinding

class BottomDialogDismissibleBigButton : BottomSheetDialogFragment() {

    private val TITLE_ARG = "title_argument"
    private val BIG_BUTTON_LABEL_ARG = "big_button_label_argument"
    private var listener: BottomDialogDismissibleBigButtonListener? =
        null

    interface BottomDialogDismissibleBigButtonListener {
        fun onDismissed()
        fun onBigButtonClicked()
    }

    fun setBottomDialogDismissibleBigButtonListener(listener: BottomDialogDismissibleBigButtonListener) {
        this.listener = listener
    }

    companion object {
        fun newInstance(title: String, bigButtonLabel: String): BottomDialogDismissibleBigButton =
            BottomDialogDismissibleBigButton()
                .apply {
                    arguments = Bundle().apply {
                        putString(TITLE_ARG, title)
                        putString(BIG_BUTTON_LABEL_ARG, bigButtonLabel)
                    }
                }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val uiBindings = DialogBottomBigButtonBinding.inflate(layoutInflater)
        initUi(uiBindings)
        return uiBindings.root
    }

    private fun initUi(uiBindings: DialogBottomBigButtonBinding) {
        val title = arguments?.getString(TITLE_ARG, "") ?: ""
        val titleField = uiBindings.dialogBottomTitleZoneWithDismissButton.dialogBottomTitle
        titleField.text = title
        //
        val dismissButton =
            uiBindings.dialogBottomTitleZoneWithDismissButton.dialogBottomDismissButton
        dismissButton.setOnClickListener {
            listener?.onDismissed()
            dismiss()
        }
        //
        val bigButtonLabel = arguments?.getString(BIG_BUTTON_LABEL_ARG, "") ?: ""
        val bigButton = uiBindings.dialogBottomBigButton
        bigButton.text = bigButtonLabel
        bigButton.setOnClickListener { listener?.onBigButtonClicked() }
    }

}
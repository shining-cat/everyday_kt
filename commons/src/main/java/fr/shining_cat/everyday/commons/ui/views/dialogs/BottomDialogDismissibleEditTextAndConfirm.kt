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
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.shining_cat.everyday.commons.databinding.DialogBottomEditTextAndConfirmBinding

class BottomDialogDismissibleEditTextAndConfirm : BottomSheetDialogFragment() {

    private val TITLE_ARG = "title_argument"
    private val HINT_ARG = "hint_argument"
    private val CONFIRM_BUTTON_LABEL_ARG = "confirm_button_label_argument"
    private var listener: BottomDialogDismissibleEditTextAndConfirmListener? = null

    interface BottomDialogDismissibleEditTextAndConfirmListener {

        fun onDismissed()
        fun onValidateInputText(inputText: String)
    }

    fun setBottomDialogDismissibleMessageAndConfirmListener(
        listener: BottomDialogDismissibleEditTextAndConfirmListener
    ) {
        this.listener = listener
    }

    companion object {

        fun newInstance(
            title: String,
            editTextHint: String,
            confirmButtonLabel: String
        ): BottomDialogDismissibleEditTextAndConfirm = BottomDialogDismissibleEditTextAndConfirm().apply {
            arguments = Bundle().apply {
                putString(
                    TITLE_ARG,
                    title
                )
                putString(
                    HINT_ARG,
                    editTextHint
                )
                putString(
                    CONFIRM_BUTTON_LABEL_ARG,
                    confirmButtonLabel
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val uiBindings = DialogBottomEditTextAndConfirmBinding.inflate(layoutInflater)
        initUi(uiBindings)
        return uiBindings.root
    }

    private fun initUi(uiBindings: DialogBottomEditTextAndConfirmBinding) {
        val title = arguments?.getString(
            TITLE_ARG,
            ""
        ) ?: ""
        val titleField = uiBindings.dialogBottomTitleZoneWithDismissButton.dialogBottomTitle
        titleField.text = title
        //
        val dismissButton = uiBindings.dialogBottomTitleZoneWithDismissButton.dialogBottomDismissButton
        dismissButton.setOnClickListener {
            listener?.onDismissed()
            dismiss()
        }
        //
        val editTextHint = arguments?.getString(
            HINT_ARG,
            ""
        ) ?: ""
        val editText = uiBindings.dialogBottomEditText
        editText.setText(editTextHint)
        //
        val confirmButtonLabel = arguments?.getString(
            CONFIRM_BUTTON_LABEL_ARG,
            ""
        ) ?: ""
        val confirmButton = uiBindings.dialogBottomConfirmButton
        confirmButton.text = confirmButtonLabel
        confirmButton.setOnClickListener { transmitInputText(editText) }
    }

    private fun transmitInputText(editText: EditText) {
        val inputText = editText.text.toString()
        listener?.onValidateInputText(inputText)
        dismiss()
    }
}
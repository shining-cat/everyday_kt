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
import fr.shining_cat.everyday.commons.databinding.DialogBottomMessageAndConfirmBinding

class BottomDialogDismissibleMessageAndConfirm : BottomSheetDialogFragment() {

    private val TITLE_ARG = "title_argument"
    private val MESSAGE_ARG = "message_argument"
    private val CONFIRM_BUTTON_LABEL_ARG = "confirm_button_label_argument"
    private var listener: BottomDialogDismissibleMessageAndConfirmListener? = null

    interface BottomDialogDismissibleMessageAndConfirmListener {

        fun onDismissed()
        fun onConfirmButtonClicked()
    }

    fun setBottomDialogDismissibleMessageAndConfirmListener(
        listener: BottomDialogDismissibleMessageAndConfirmListener
    ) {
        this.listener = listener
    }

    companion object {

        fun newInstance(
            title: String,
            message: String,
            confirmButtonLabel: String
        ): BottomDialogDismissibleMessageAndConfirm =
            BottomDialogDismissibleMessageAndConfirm().apply {
                arguments = Bundle().apply {
                    putString(
                        TITLE_ARG,
                        title
                    )
                    putString(
                        MESSAGE_ARG,
                        message
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
        val uiBindings = DialogBottomMessageAndConfirmBinding.inflate(layoutInflater)
        initUi(uiBindings)
        return uiBindings.root
    }

    private fun initUi(uiBindings: DialogBottomMessageAndConfirmBinding) {
        val title = arguments?.getString(
            TITLE_ARG,
            ""
        ) ?: ""
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
        val message = arguments?.getString(
            MESSAGE_ARG,
            ""
        ) ?: ""
        val messageField = uiBindings.dialogBottomMessage
        messageField.text = message
        //
        val confirmButtonLabel = arguments?.getString(
            CONFIRM_BUTTON_LABEL_ARG,
            ""
        ) ?: ""
        val confirmButton = uiBindings.dialogBottomConfirmButton
        confirmButton.text = confirmButtonLabel
        confirmButton.setOnClickListener { listener?.onConfirmButtonClicked() }
    }
}
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

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.shining_cat.everyday.commons.databinding.DialogBottomTimePickerAndConfirmBinding

class BottomDialogDismissibleTimePicker : BottomSheetDialogFragment() {

    private val TITLE_ARG = "title_argument"
    private val HOUR_ARG = "hour_argument"
    private val MINUTE_ARG = "minute_argument"
    private val CONFIRM_BUTTON_LABEL_ARG = "confirm_button_label_argument"
    private var listener: BottomDialogDismissibleTimePickerListener? = null

    interface BottomDialogDismissibleTimePickerListener {

        fun onDismissed()
        fun onConfirmButtonClicked(
            hour: Int,
            minutes: Int
        )
    }

    fun setBottomDialogDismissibleTimePickerListener(
        listener: BottomDialogDismissibleTimePickerListener
    ) {
        this.listener = listener
    }

    companion object {

        fun newInstance(
            title: String,
            confirmButtonLabel: String,
            hour: Int = 0,
            minutes: Int = 0
        ): BottomDialogDismissibleTimePicker = BottomDialogDismissibleTimePicker().apply {
            arguments = Bundle().apply {
                putString(
                    TITLE_ARG,
                    title
                )
                putString(
                    CONFIRM_BUTTON_LABEL_ARG,
                    confirmButtonLabel
                )
                putInt(
                    HOUR_ARG,
                    hour
                )
                putInt(
                    MINUTE_ARG,
                    minutes
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val uiBindings = DialogBottomTimePickerAndConfirmBinding.inflate(layoutInflater)
        initUi(uiBindings)
        return uiBindings.root
    }

    private fun initUi(uiBindings: DialogBottomTimePickerAndConfirmBinding) {
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
        val timePicker = uiBindings.dialogBottomTimePicker
        timePicker.setIs24HourView(true)
        val hour = arguments?.getInt(
            HOUR_ARG,
            0
        ) ?: 0
        val minutes = arguments?.getInt(
            MINUTE_ARG,
            0
        ) ?: 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.hour = hour
            timePicker.minute = minutes
        } else @Suppress("DEPRECATION") {
            timePicker.currentHour = hour
            timePicker.currentMinute = minutes
        }
        //
        val confirmButtonLabel = arguments?.getString(
            CONFIRM_BUTTON_LABEL_ARG,
            ""
        ) ?: ""
        val confirmButton = uiBindings.dialogBottomConfirmButton
        confirmButton.text = confirmButtonLabel
        confirmButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                transmitSelectedTime(
                    timePicker.hour,
                    timePicker.minute
                )
            } else {
                @Suppress("DEPRECATION") transmitSelectedTime(
                    timePicker.currentHour,
                    timePicker.currentMinute
                )
            }
        }
    }

    private fun transmitSelectedTime(
        hour: Int = 0,
        minutes: Int = 0
    ) {
        listener?.onConfirmButtonClicked(
            hour,
            minutes
        )
        dismiss()
    }
}
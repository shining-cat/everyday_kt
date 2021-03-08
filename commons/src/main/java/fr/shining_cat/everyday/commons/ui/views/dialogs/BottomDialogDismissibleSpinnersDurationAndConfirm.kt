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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.shining_cat.everyday.commons.databinding.DialogBottomSpinnersDurationAndConfirmBinding
import java.util.concurrent.TimeUnit

class BottomDialogDismissibleSpinnersDurationAndConfirm : BottomSheetDialogFragment() {

    private val TITLE_ARG = "title_argument"
    private val SHOW_HOURS_ARG = "show_hours_argument"
    private val SHOW_MINUTES_ARG = "show_minutes_argument"
    private val SHOW_SECONDS_ARG = "show_seconds_argument"
    private val EXPLANATION_ARG = "explanation_argument"
    private val CONFIRM_BUTTON_LABEL_ARG = "confirm_button_label_argument"
    private val INITIAL_LENGTH_ARG = "initial_length_argument"

    private lateinit var hoursPicker: NumberPicker
    private lateinit var minutesPicker: NumberPicker
    private lateinit var secondsPicker: NumberPicker

    private var listener: BottomDialogDismissibleSpinnerSecondsAndConfirmListener? = null

    interface BottomDialogDismissibleSpinnerSecondsAndConfirmListener {

        fun onDismissed()
        fun onConfirmButtonClicked(lengthMs: Long)
    }

    fun setBottomDialogDismissibleSpinnerSecondsAndConfirmListener(
        listener: BottomDialogDismissibleSpinnerSecondsAndConfirmListener
    ) {
        this.listener = listener
    }

    companion object {

        fun newInstance(
            title: String,
            showHours: Boolean,
            showMinutes: Boolean,
            showSeconds: Boolean,
            explanationMessage: String,
            confirmButtonLabel: String,
            initialLengthMs: Long = 0L
        ): BottomDialogDismissibleSpinnersDurationAndConfirm = BottomDialogDismissibleSpinnersDurationAndConfirm().apply {
            arguments = Bundle().apply {
                putString(
                    TITLE_ARG,
                    title
                )
                putBoolean(
                    SHOW_HOURS_ARG,
                    showHours
                )
                putBoolean(
                    SHOW_MINUTES_ARG,
                    showMinutes
                )
                putBoolean(
                    SHOW_SECONDS_ARG,
                    showSeconds
                )
                putString(
                    EXPLANATION_ARG,
                    explanationMessage
                )
                putString(
                    CONFIRM_BUTTON_LABEL_ARG,
                    confirmButtonLabel
                )
                putLong(
                    INITIAL_LENGTH_ARG,
                    initialLengthMs
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val uiBindings = DialogBottomSpinnersDurationAndConfirmBinding.inflate(layoutInflater)
        initUi(uiBindings)
        return uiBindings.root
    }

    private fun initUi(uiBindings: DialogBottomSpinnersDurationAndConfirmBinding) {
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
        val showHoursPicker = arguments?.getBoolean(SHOW_HOURS_ARG) ?: false
        val showMinutesPicker = arguments?.getBoolean(SHOW_MINUTES_ARG) ?: false
        val showSecondsPicker = arguments?.getBoolean(SHOW_SECONDS_ARG) ?: false
        val initialLengthMs = arguments?.getLong(INITIAL_LENGTH_ARG) ?: 0L
        setPickers(
            uiBindings,
            showHoursPicker,
            showMinutesPicker,
            showSecondsPicker,
            initialLengthMs
        )
        //
        val explanation = arguments?.getString(
            EXPLANATION_ARG,
            ""
        ) ?: ""
        val explanationTv = uiBindings.dialogBottomInstruction
        if (explanation.isNotBlank()) {
            explanationTv.text = explanation
        }
        else {
            explanationTv.visibility = GONE
        }
        //
        val confirmButtonLabel = arguments?.getString(
            CONFIRM_BUTTON_LABEL_ARG,
            ""
        ) ?: ""
        val confirmButton = uiBindings.dialogBottomConfirmButton
        confirmButton.text = confirmButtonLabel
        confirmButton.setOnClickListener { transmitInputLength() }
    }

    private fun collectLengthSelected(): Long {
        val hours: Int = hoursPicker.value
        val minutes: Int = minutesPicker.value
        val seconds: Int = secondsPicker.value
        return ((hours * 60 + minutes) * 60 + seconds) * 1000.toLong()
    }

    private fun transmitInputLength() {
        listener?.onConfirmButtonClicked(
            collectLengthSelected()
        )
        dismiss()
    }

    private fun setPickers(
        uiBindings: DialogBottomSpinnersDurationAndConfirmBinding,
        showHoursPicker: Boolean,
        showMinutesPicker: Boolean,
        showSecondsPicker: Boolean,
        initialLengthMs: Long
    ) {
        setPicker(
            uiBindings.dialogBottomDurationHoursPicker,
            uiBindings.dialogBottomDurationHoursUnit,
            showHoursPicker,
            23
        )
        setPicker(
            uiBindings.dialogBottomDurationMinutesPicker,
            uiBindings.dialogBottomDurationMinutesUnit,
            showMinutesPicker,
            59
        )
        setPicker(
            uiBindings.dialogBottomDurationSecondsPicker,
            uiBindings.dialogBottomDurationSecondsUnit,
            showSecondsPicker,
            59
        )
        // convert initial time as ms to h, m, and s
        val initialLengthHours = TimeUnit.MILLISECONDS.toHours(initialLengthMs)
        val initialLengthMinutes = TimeUnit.MILLISECONDS.toMinutes(initialLengthMs)
        val initialLengthSeconds = TimeUnit.MILLISECONDS.toSeconds(initialLengthMs)
        val fullMinutes = (initialLengthMinutes - TimeUnit.HOURS.toMinutes(initialLengthHours))
        val fullSeconds = (initialLengthSeconds - (TimeUnit.HOURS.toSeconds(initialLengthHours) + TimeUnit.MINUTES.toSeconds(fullMinutes)))
        // apply to pickers
        hoursPicker.value = initialLengthHours.toInt()
        minutesPicker.value = fullMinutes.toInt()
        secondsPicker.value = fullSeconds.toInt()
        Log.d(
            "DIALOG",
            "fullHours = $initialLengthHours," + "fullMinutes = $fullMinutes, fullSeconds = $fullSeconds"
        )
    }

    private fun setPicker(
        picker: NumberPicker,
        units: TextView,
        showIt: Boolean,
        maxValue: Int = 0
    ) {
        val twoDigitsFormatter = NumberPicker.Formatter { i ->
            String.format(
                "%02d",
                i
            )
        }
        if (showIt) {
            picker.maxValue = maxValue
            picker.minValue = 0
            picker.setFormatter(twoDigitsFormatter)
            picker.wrapSelectorWheel = false
        }
        else {
            picker.visibility = GONE
            units.visibility = GONE
        }
    }
}
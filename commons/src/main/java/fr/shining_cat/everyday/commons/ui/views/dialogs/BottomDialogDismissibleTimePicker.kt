package fr.shining_cat.everyday.commons.ui.views.dialogs

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.shining_cat.everyday.commons.R

class BottomDialogDismissibleTimePicker : BottomSheetDialogFragment() {

    private val TITLE_ARG = "title_argument"
    private val HOUR_ARG = "hour_argument"
    private val MINUTE_ARG = "minute_argument"
    private val CONFIRM_BUTTON_LABEL_ARG = "confirm_button_label_argument"
    private var bottomDialogDismissibleTimePickerListener: BottomDialogDismissibleTimePickerListener? =
        null

    interface BottomDialogDismissibleTimePickerListener {
        fun onDismissed()
        fun onConfirmButtonClicked(hour: Int, minutes: Int)
    }

    fun setBottomDialogDismissibleTimePickerListener(listener: BottomDialogDismissibleTimePickerListener) {
        this.bottomDialogDismissibleTimePickerListener = listener
    }

    companion object {
        fun newInstance(
            title: String,
            confirmButtonLabel: String,
            hour: Int = 0,
            minutes: Int = 0
        ): BottomDialogDismissibleTimePicker =
            BottomDialogDismissibleTimePicker()
                .apply {
                    arguments = Bundle().apply {
                        putString(TITLE_ARG, title)
                        putString(CONFIRM_BUTTON_LABEL_ARG, confirmButtonLabel)
                        putInt(HOUR_ARG, hour)
                        putInt(MINUTE_ARG, minutes)
                    }
                }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_time_picker_and_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString(TITLE_ARG, "") ?: ""
        val titleField = view.findViewById<TextView>(R.id.dialog_bottom_title)
        titleField.text = title
        //
        val dismissButton = view.findViewById<ImageView>(R.id.dialog_bottom_dismiss_button)
        dismissButton.setOnClickListener {
            bottomDialogDismissibleTimePickerListener?.onDismissed()
            dismiss()
        }
        //
        val timePicker = view.findViewById<TimePicker>(R.id.dialog_bottom_time_picker)
        timePicker.setIs24HourView(true)
        val hour = arguments?.getInt(HOUR_ARG, 0) ?: 0
        val minutes = arguments?.getInt(MINUTE_ARG, 0) ?: 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.hour = hour
            timePicker.minute = minutes
        }
        else{
            timePicker.setCurrentHour(hour)
            timePicker.setCurrentMinute(minutes)
        }
        //
        val confirmButtonLabel = arguments?.getString(CONFIRM_BUTTON_LABEL_ARG, "") ?: ""
        val confirmButton = view.findViewById<Button>(R.id.dialog_bottom_confirm_button)
        confirmButton.text = confirmButtonLabel
        confirmButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                transmitSelectedTime(timePicker.hour, timePicker.minute)
            }
            else{
                transmitSelectedTime(timePicker.currentHour, timePicker.currentMinute)
            }
        }
    }

    private fun transmitSelectedTime(hour: Int = 0, minutes: Int = 0) {
        bottomDialogDismissibleTimePickerListener?.onConfirmButtonClicked(hour, minutes)
        dismiss()
    }

}
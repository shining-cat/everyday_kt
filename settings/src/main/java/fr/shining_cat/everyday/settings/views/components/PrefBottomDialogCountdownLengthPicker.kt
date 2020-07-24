package fr.shining_cat.everyday.settings.views.components

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.preference.Preference
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelper
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleSpinnersDurationAndConfirm
import fr.shining_cat.everyday.settings.R

class PrefBottomDialogCountdownLengthPicker(
    context: Context,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val fragmentManager: FragmentManager,
    private val logger: Logger
) : Preference(context) {

    private val LOG_TAG = PrefBottomDialogCountdownLengthPicker::class.java.name

    init {
        title = context.getString(R.string.startCountDownLengthPreference_title)
        isIconSpaceReserved = false
        updateSummary()
    }

    private fun updateSummary() {
        summary = context.getString(R.string.startCountDownLengthPreference_explanation) + ": " +
                context.getString(R.string.startCountDownLengthPreference_value_display).format(
                    sharedPrefsHelper.getCountDownLength().toInt() / 1000
                )
    }

    override fun onClick() {
        openDialog()
    }

    private fun openDialog() {
        val setCountDownLengthBottomSheetDialog =
            BottomDialogDismissibleSpinnersDurationAndConfirm.newInstance(
                title = context.getString(R.string.startCountDownLengthPreference_title),
                showHours = false,
                showMinutes = false,
                showSeconds = true,
                explanationMessage = context.getString(R.string.startCountDownLengthPreference_explanation),
                confirmButtonLabel = context.getString(R.string.generic_string_OK),
                initialLengthMs = sharedPrefsHelper.getCountDownLength()
            )
        setCountDownLengthBottomSheetDialog.setBottomDialogDismissibleSpinnerSecondsAndConfirmListener(
            object :
                BottomDialogDismissibleSpinnersDurationAndConfirm.BottomDialogDismissibleSpinnerSecondsAndConfirmListener {
                override fun onDismissed() {
                    //nothing to do here
                }

                override fun onConfirmButtonClicked(lengthMs: Long) {
                    sharedPrefsHelper.setCountDownLength(lengthMs)
                    updateSummary()
                }
            })
        setCountDownLengthBottomSheetDialog.show(fragmentManager, "openCountDownLengthPickerDialog")
    }
}
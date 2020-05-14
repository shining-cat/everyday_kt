package fr.shining_cat.everyday.commons.ui.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.shining_cat.everyday.commons.R

class BottomDialogDismissableEditTextAndConfirm : BottomSheetDialogFragment() {

    private val TITLE_ARG = "title_argument"
    private val HINT_ARG = "hint_argument"
    private val CONFIRM_BUTTON_LABEL_ARG = "confirm_button_label_argument"
    private var bottomDialogDismissableEditTextAndConfirmListenerListener: BottomDialogDismissableEditTextAndConfirmListener? =
        null

    interface BottomDialogDismissableEditTextAndConfirmListener {
        fun onDismissed()
        fun onValidateInputText(inputText: String)
    }

    fun setBottomDialogDismissableMessageAndConfirmListener(listener: BottomDialogDismissableEditTextAndConfirmListener) {
        this.bottomDialogDismissableEditTextAndConfirmListenerListener = listener
    }

    companion object {
        fun newInstance(title: String, editTextHint: String, confirmButtonLabel: String): BottomDialogDismissableEditTextAndConfirm =
            BottomDialogDismissableEditTextAndConfirm()
                .apply {
                arguments = Bundle().apply {
                    putString(TITLE_ARG, title)
                    putString(HINT_ARG, editTextHint)
                    putString(CONFIRM_BUTTON_LABEL_ARG, confirmButtonLabel)
                }
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_edit_text_and_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString(TITLE_ARG, "")?:""
        val titleField = view.findViewById<TextView>(R.id.dialog_bottom_title)
        titleField.text = title
        //
        val dismissButton = view.findViewById<ImageView>(R.id.dialog_bottom_dismiss_button)
        dismissButton.setOnClickListener {
            bottomDialogDismissableEditTextAndConfirmListenerListener?.onDismissed()
            dismiss()
        }
        //
        val editTextHint = arguments?.getString(HINT_ARG, "")?:""
        val editText = view.findViewById<EditText>(R.id.dialog_bottom_edit_text)
        editText.setText(editTextHint)
        //
        val confirmButtonLabel = arguments?.getString(CONFIRM_BUTTON_LABEL_ARG, "")?:""
        val confirmButton = view.findViewById<Button>(R.id.dialog_bottom_confirm_button)
        confirmButton.text = confirmButtonLabel
        confirmButton.setOnClickListener { transmitInputText(editText)}
    }

    private fun transmitInputText(editText: EditText){
        val inputText = editText.text.toString()
        bottomDialogDismissableEditTextAndConfirmListenerListener?.onValidateInputText(inputText)
        dismiss()
    }

}
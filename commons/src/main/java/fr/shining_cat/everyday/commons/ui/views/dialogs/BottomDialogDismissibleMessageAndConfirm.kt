package fr.shining_cat.everyday.commons.ui.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.shining_cat.everyday.commons.R

class BottomDialogDismissibleMessageAndConfirm : BottomSheetDialogFragment() {

    private val TITLE_ARG = "title_argument"
    private val MESSAGE_ARG = "message_argument"
    private val CONFIRM_BUTTON_LABEL_ARG = "confirm_button_label_argument"
    private var bottomDialogDismissibleMessageAndConfirmListenerListener: BottomDialogDismissibleMessageAndConfirmListener? =
        null

    interface BottomDialogDismissibleMessageAndConfirmListener {
        fun onDismissed()
        fun onConfirmButtonClicked()
    }

    fun setBottomDialogDismissibleMessageAndConfirmListener(listener: BottomDialogDismissibleMessageAndConfirmListener) {
        this.bottomDialogDismissibleMessageAndConfirmListenerListener = listener
    }

    companion object {
        fun newInstance(title: String, message: String, confirmButtonLabel: String): BottomDialogDismissibleMessageAndConfirm =
            BottomDialogDismissibleMessageAndConfirm()
                .apply {
                arguments = Bundle().apply {
                    putString(TITLE_ARG, title)
                    putString(MESSAGE_ARG, message)
                    putString(CONFIRM_BUTTON_LABEL_ARG, confirmButtonLabel)
                }
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_message_and_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString(TITLE_ARG, "")?:""
        val titleField = view.findViewById<TextView>(R.id.dialog_bottom_title)
        titleField.text = title
        //
        val dismissButton = view.findViewById<ImageView>(R.id.dialog_bottom_dismiss_button)
        dismissButton.setOnClickListener {
            bottomDialogDismissibleMessageAndConfirmListenerListener?.onDismissed()
            dismiss()
        }
        //
        val message = arguments?.getString(MESSAGE_ARG, "")?:""
        val messageField = view.findViewById<TextView>(R.id.dialog_bottom_message)
        messageField.text = message
        //
        val confirmButtonLabel = arguments?.getString(CONFIRM_BUTTON_LABEL_ARG, "")?:""
        val confirmButton = view.findViewById<Button>(R.id.dialog_bottom_confirm_button)
        confirmButton.text = confirmButtonLabel
        confirmButton.setOnClickListener { bottomDialogDismissibleMessageAndConfirmListenerListener?.onConfirmButtonClicked() }
    }

}
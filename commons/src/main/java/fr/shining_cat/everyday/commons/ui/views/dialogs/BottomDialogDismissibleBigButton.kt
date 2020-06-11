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

class BottomDialogDismissibleBigButton : BottomSheetDialogFragment() {

    private val TITLE_ARG = "title_argument"
    private val BIG_BUTTON_LABEL_ARG = "big_button_label_argument"
    private var bottomDialogDismissableBigButtonListener: BottomDialogDismissableBigButtonListener? =
        null

    interface BottomDialogDismissableBigButtonListener {
        fun onDismissed()
        fun onBigButtonClicked()
    }

    fun setBottomDialogDismissableBigButtonListener(listener: BottomDialogDismissableBigButtonListener) {
        this.bottomDialogDismissableBigButtonListener = listener
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
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_big_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString(TITLE_ARG, "")?:""
        val titleField = view.findViewById<TextView>(R.id.dialog_bottom_title)
        titleField.text = title
        //
        val dismissButton = view.findViewById<ImageView>(R.id.dialog_bottom_dismiss_button)
        dismissButton.setOnClickListener {
            bottomDialogDismissableBigButtonListener?.onDismissed()
            dismiss()
        }
        //
        val bigButtonLabel = arguments?.getString(BIG_BUTTON_LABEL_ARG, "")?:""
        val bigButton = view.findViewById<Button>(R.id.dialog_bottom_big_button)
        bigButton.text = bigButtonLabel
        bigButton.setOnClickListener { bottomDialogDismissableBigButtonListener?.onBigButtonClicked() }
    }

}
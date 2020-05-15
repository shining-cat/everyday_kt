package fr.shining_cat.everyday.commons.ui.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.R
import org.koin.android.ext.android.get

class BottomDialogDismissableSelectListAndConfirm : BottomSheetDialogFragment() {

    private val LOG_TAG = BottomDialogDismissableSelectListAndConfirm::class.java.name
    private val logger: Logger = get()

    private val TITLE_ARG = "title_argument"
    private val OPTIONS_ARG = "options_argument"
    private val CONFIRM_BUTTON_LABEL_ARG = "confirm_button_label_argument"
    private val INITIAL_SELECTED_INDEX_ARG = "initial_selected_index_argument"
    private var bottomDialogDismissableSelectListAndConfirmListenerListener: BottomDialogDismissableSelectListAndConfirmListener? =
        null

    interface BottomDialogDismissableSelectListAndConfirmListener {
        fun onDismissed()
        fun onValidateSelection(optionSelectedIndex: Int)
    }

    fun setBottomDialogDismissableSelectListAndConfirmListener(listener: BottomDialogDismissableSelectListAndConfirmListener) {
        this.bottomDialogDismissableSelectListAndConfirmListenerListener = listener
    }

    companion object {
        fun newInstance(
            title: String,
            optionsLabels: List<String>,
            confirmButtonLabel: String,
            initialSelectedIndex: Int = -1
        ): BottomDialogDismissableSelectListAndConfirm =
            BottomDialogDismissableSelectListAndConfirm()
                .apply {
                    arguments = Bundle().apply {
                        putString(TITLE_ARG, title)
                        putStringArrayList(OPTIONS_ARG, ArrayList(optionsLabels))
                        putString(CONFIRM_BUTTON_LABEL_ARG, confirmButtonLabel)
                        putInt(INITIAL_SELECTED_INDEX_ARG, initialSelectedIndex)
                    }
                }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_select_list_and_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString(TITLE_ARG, "") ?: ""
        val titleField = view.findViewById<TextView>(R.id.dialog_bottom_title)
        titleField.text = title
        //
        val dismissButton = view.findViewById<ImageView>(R.id.dialog_bottom_dismiss_button)
        dismissButton.setOnClickListener {
            bottomDialogDismissableSelectListAndConfirmListenerListener?.onDismissed()
            dismiss()
        }
        //
        val selectListRecycler = view.findViewById<RecyclerView>(R.id.dialog_bottom_recycler)
        val selectListAdapter = SelectListAdapter(logger)
        val optionsLabels = arguments?.getStringArrayList(OPTIONS_ARG)?.toList() ?: listOf()
        val initialSelectedIndex = arguments?.getInt(INITIAL_SELECTED_INDEX_ARG)?: -1
        selectListAdapter.optionsLabels = optionsLabels
        if(initialSelectedIndex != -1) selectListAdapter.forceInitialSelectedPosition(initialSelectedIndex)
        selectListRecycler.adapter = selectListAdapter
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        selectListRecycler.layoutManager = layoutManager
        //
        val confirmButtonLabel = arguments?.getString(CONFIRM_BUTTON_LABEL_ARG, "") ?: ""
        val confirmButton = view.findViewById<Button>(R.id.dialog_bottom_confirm_button)
        confirmButton.text = confirmButtonLabel
        confirmButton.setOnClickListener { transmitChosenOption(selectListAdapter.selectedPosition) }
    }

    private fun transmitChosenOption(optionSelectedIndex: Int) {
        bottomDialogDismissableSelectListAndConfirmListenerListener?.onValidateSelection(
            optionSelectedIndex
        )
        dismiss()
    }
}
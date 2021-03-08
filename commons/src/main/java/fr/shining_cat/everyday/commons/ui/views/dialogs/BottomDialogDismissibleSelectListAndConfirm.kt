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
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.R
import fr.shining_cat.everyday.commons.databinding.DialogBottomSelectListAndConfirmBinding
import org.koin.android.ext.android.get

class BottomDialogDismissibleSelectListAndConfirm : BottomSheetDialogFragment() {

    private val LOG_TAG = BottomDialogDismissibleSelectListAndConfirm::class.java.name
    private val logger: Logger = get()

    private val TITLE_ARG = "title_argument"
    private val OPTIONS_ARG = "options_argument"
    private val CONFIRM_BUTTON_LABEL_ARG = "confirm_button_label_argument"
    private val INITIAL_SELECTED_INDEX_ARG = "initial_selected_index_argument"
    private var listener: BottomDialogDismissibleSelectListAndConfirmListener? = null

    interface BottomDialogDismissibleSelectListAndConfirmListener {

        fun onDismissed()
        fun onValidateSelection(optionSelectedIndex: Int)
    }

    fun setBottomDialogDismissibleSelectListAndConfirmListener(
        listener: BottomDialogDismissibleSelectListAndConfirmListener
    ) {
        this.listener = listener
    }

    companion object {

        fun newInstance(
            title: String,
            optionsLabels: List<String>,
            confirmButtonLabel: String,
            initialSelectedIndex: Int = -1
        ): BottomDialogDismissibleSelectListAndConfirm = BottomDialogDismissibleSelectListAndConfirm().apply {
            arguments = Bundle().apply {
                putString(
                    TITLE_ARG,
                    title
                )
                putStringArrayList(
                    OPTIONS_ARG,
                    ArrayList(optionsLabels)
                )
                putString(
                    CONFIRM_BUTTON_LABEL_ARG,
                    confirmButtonLabel
                )
                putInt(
                    INITIAL_SELECTED_INDEX_ARG,
                    initialSelectedIndex
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val uiBindings = DialogBottomSelectListAndConfirmBinding.inflate(layoutInflater)
        initUi(uiBindings)
        return uiBindings.root
    }

    private fun initUi(uiBindings: DialogBottomSelectListAndConfirmBinding) {
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
        val selectListRecycler = uiBindings.dialogBottomRecycler
        val selectListAdapter = SelectListAdapter(logger)
        val optionsLabels = arguments?.getStringArrayList(OPTIONS_ARG)?.toList() ?: listOf()
        val initialSelectedIndex = arguments?.getInt(INITIAL_SELECTED_INDEX_ARG) ?: -1
        selectListAdapter.optionsLabels = optionsLabels
        if (initialSelectedIndex != -1) selectListAdapter.forceInitialSelectedOption(
            initialSelectedIndex
        )
        selectListRecycler.adapter = selectListAdapter
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        selectListRecycler.layoutManager = layoutManager
        //
        val confirmButtonLabel = arguments?.getString(
            CONFIRM_BUTTON_LABEL_ARG,
            ""
        ) ?: ""
        val confirmButton = uiBindings.dialogBottomConfirmButton
        confirmButton.text = confirmButtonLabel
        confirmButton.setOnClickListener {
            transmitChosenOption(selectListAdapter.selectedPosition)
        }
        // prevent disturbing dialog size-changes when scrolling list
        // by setting peek height to expanded (full) height
        this.dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.peekHeight = bottomSheet.height
        }
    }

    private fun transmitChosenOption(optionSelectedIndex: Int) {
        listener?.onValidateSelection(
            optionSelectedIndex
        )
        dismiss()
    }
}
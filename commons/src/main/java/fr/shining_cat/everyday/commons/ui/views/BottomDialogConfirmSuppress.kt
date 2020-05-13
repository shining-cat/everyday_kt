package fr.shining_cat.everyday.commons.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.shining_cat.everyday.commons.R

class BottomDialogConfirmSuppress: BottomSheetDialogFragment() {

    companion object {
        fun newInstance(): BottomDialogConfirmSuppress =
            BottomDialogConfirmSuppress().apply {
                /*
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_COUNT, itemCount)
                }
                 */
            }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_bottom_confirm_suppress, container, false)
    }

    //TODO: implement actions
}
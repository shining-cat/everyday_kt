package fr.shining_cat.everyday.screens.views.home.sessionpresets

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window.FEATURE_NO_TITLE
import androidx.fragment.app.DialogFragment
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.screens.databinding.DialogCreateSessionPresetBinding
import org.koin.android.ext.android.get

class CreateSessionPresetDialog : DialogFragment() {

    private val LOG_TAG = CreateSessionPresetDialog::class.java.name

    private val logger: Logger = get()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val createSessionPresetDialogBinding = DialogCreateSessionPresetBinding.inflate(LayoutInflater.from(context))
        return createSessionPresetDialogBinding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(FEATURE_NO_TITLE)
        return dialog
    }

}
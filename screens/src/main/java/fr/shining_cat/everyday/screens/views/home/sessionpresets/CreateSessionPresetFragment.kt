package fr.shining_cat.everyday.screens.views.home.sessionpresets

import androidx.fragment.app.Fragment
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.screens.viewmodels.CreateSessionPresetViewModel
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class CreateSessionPresetFragment : Fragment() {

    private val LOG_TAG = CreateSessionPresetFragment::class.java.name

    private val logger: Logger = get()
    private val createPresetViewModel: CreateSessionPresetViewModel by viewModel()


}
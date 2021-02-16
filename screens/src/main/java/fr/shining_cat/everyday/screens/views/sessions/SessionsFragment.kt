package fr.shining_cat.everyday.screens.views.sessions

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.databinding.SessionsFragmentBinding
import fr.shining_cat.everyday.screens.views.ScreenActivity
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class SessionsFragment : Fragment() {

    private val LOG_TAG = SessionsFragment::class.java.simpleName

    private val logger: Logger = get()
    private val sessionsViewModel: SessionsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sessionsFragmentBinding = SessionsFragmentBinding.inflate(layoutInflater)
        //
        setupToolbar(sessionsFragmentBinding)
        //
        val textView: TextView = sessionsFragmentBinding.fakeFragmentText
        sessionsViewModel.initReadyLiveData.observe(viewLifecycleOwner, Observer {
            textView.text = "This is ${LOG_TAG}\n $it loaded!"
        })
        sessionsViewModel.initViewModel()
        return sessionsFragmentBinding.root
    }

    private fun setupToolbar(sessionsFragmentBinding: SessionsFragmentBinding) {
        logger.d(LOG_TAG, "setupToolbar")
        val toolbar: Toolbar = sessionsFragmentBinding.toolbar
        (activity as ScreenActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        logger.d(LOG_TAG, "onCreateOptionsMenu")
        inflater.inflate(R.menu.toolbar_menu_sessions, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }
}


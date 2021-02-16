package fr.shining_cat.everyday.screens.views.home

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.navigation.Actions
import fr.shining_cat.everyday.navigation.Destination
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.databinding.HomeFragmentBinding
import fr.shining_cat.everyday.screens.views.ScreenActivity
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val LOG_TAG = HomeFragment::class.java.simpleName

    private val logger: Logger = get()
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeFragmentBinding = HomeFragmentBinding.inflate(layoutInflater)
        logger.d(LOG_TAG, "onCreateView")
        //
        setupToolbar(homeFragmentBinding)
        //
        val textView: TextView = homeFragmentBinding.fakeFragmentText
        homeViewModel.initReadyLiveData.observe(viewLifecycleOwner, Observer {
            textView.text = "This is ${LOG_TAG}\n $it loaded!"
        })
        homeViewModel.initViewModel()
        return homeFragmentBinding.root
    }


    private fun setupToolbar(homeFragmentBinding: HomeFragmentBinding) {
        logger.d(LOG_TAG, "setupToolbar")
        val toolbar: Toolbar = homeFragmentBinding.toolbar
        (activity as ScreenActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        logger.d(LOG_TAG, "onCreateOptionsMenu")
        inflater.inflate(R.menu.toolbar_menu_home, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionbar_settings -> {
                startActivity(context?.let {
                    Actions.openDestination(
                        it,
                        Destination.SettingsDestination()
                    )
                })
                return true
            }
            R.id.actionbar_about -> {
                showAboutDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAboutDialog() {
        logger.d(LOG_TAG, "todo: showAboutDialog")
        //TODO("not implemented")
    }
}

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

package fr.shining_cat.everyday.screens.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.navigation.Actions
import fr.shining_cat.everyday.navigation.Destination
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.databinding.FragmentHomeBinding
import fr.shining_cat.everyday.screens.viewmodels.HomeViewModel
import fr.shining_cat.everyday.screens.views.ScreenActivity
import fr.shining_cat.everyday.screens.views.home.sessionpresets.CreateSessionPresetDialog
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
        val homeFragmentBinding = FragmentHomeBinding.inflate(layoutInflater)
        logger.d(
            LOG_TAG,
            "onCreateView"
        )
        //
        setupToolbar(homeFragmentBinding)
        //
        setupAddSessionPresetFab(homeFragmentBinding)
        //
        homeViewModel.initViewModel()
        return homeFragmentBinding.root
    }

    ////////////////////////
    // TOOLBAR
    private fun setupToolbar(homeFragmentBinding: FragmentHomeBinding) {
        logger.d(
            LOG_TAG,
            "setupToolbar"
        )
        val toolbar: Toolbar = homeFragmentBinding.toolbar
        (activity as ScreenActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        logger.d(
            LOG_TAG,
            "onCreateOptionsMenu"
        )
        inflater.inflate(
            R.menu.toolbar_menu_home,
            menu
        )
        return super.onCreateOptionsMenu(
            menu,
            inflater
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionbar_settings -> {
                startActivity(
                    context?.let {
                        Actions.openDestination(
                            it,
                            Destination.SettingsDestination()
                        )
                    }
                )
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
        val aboutDialog = AboutDialog.newInstance()
        aboutDialog.show(parentFragmentManager, "openAboutDialog")
    }
    ////////////////////
    // FAB

    private fun setupAddSessionPresetFab(homeFragmentBinding: FragmentHomeBinding) {
        homeFragmentBinding.addSessionPresetFab.setOnClickListener { showCreateSessionPresetDialog() }
    }

    private fun showCreateSessionPresetDialog() {
        val dialogFragment = CreateSessionPresetDialog()
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(android.R.id.content, dialogFragment).addToBackStack(null).commit()
    }
}
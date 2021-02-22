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

package fr.shining_cat.everyday.screens.views.rewards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.databinding.RewardsFragmentBinding
import fr.shining_cat.everyday.screens.viewmodels.RewardsViewModel
import fr.shining_cat.everyday.screens.views.ScreenActivity
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class RewardsFragment : Fragment() {

    private val LOG_TAG = RewardsFragment::class.java.simpleName

    private val logger: Logger = get()
    private val rewardsViewModel: RewardsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rewardsFragmentBinding = RewardsFragmentBinding.inflate(layoutInflater)
        //
        setupToolbar(rewardsFragmentBinding)
        //
        val textView: TextView = rewardsFragmentBinding.fakeFragmentText
        rewardsViewModel.initReadyLiveData.observe(
            viewLifecycleOwner,
            Observer {
                textView.text = "This is ${LOG_TAG}\n $it loaded!"
            }
        )
        rewardsViewModel.initViewModel()
        return rewardsFragmentBinding.root
    }

    private fun setupToolbar(rewardsFragmentBinding: RewardsFragmentBinding) {
        logger.d(LOG_TAG, "setupToolbar")
        val toolbar: Toolbar = rewardsFragmentBinding.toolbar
        (activity as ScreenActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        logger.d(LOG_TAG, "onCreateOptionsMenu")
        inflater.inflate(R.menu.toolbar_menu_rewards, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }
}

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

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.ui.SwipeInRecyclerViewCallback
import fr.shining_cat.everyday.commons.ui.views.dialogs.BottomDialogDismissibleErrorMessage
import fr.shining_cat.everyday.commons.ui.views.widgets.fabspeeddial.FabSpeedDialItem
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.navigation.Actions
import fr.shining_cat.everyday.navigation.Destination
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.databinding.FragmentHomeBinding
import fr.shining_cat.everyday.screens.viewmodels.HomeViewModel
import fr.shining_cat.everyday.screens.views.ScreenActivity
import fr.shining_cat.everyday.screens.views.home.sessionpresetdialogs.SessionPresetDialog
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment: Fragment() {

    private val LOG_TAG = HomeFragment::class.java.name

    private val logger: Logger = get()
    private val homeViewModel: HomeViewModel by viewModel()
    private val sessionPresetsAdapter = SessionPresetsAdapter(logger)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeFragmentBinding = FragmentHomeBinding.inflate(layoutInflater)
        //
        setupToolbar(homeFragmentBinding)
        //
        setupAddSessionPresetFab(homeFragmentBinding)
        //
        setUpSessionPresetsRecyclerView(homeFragmentBinding)
        //
        setupObservers(homeFragmentBinding)
        //
        homeViewModel.initViewModel(resources)
        return homeFragmentBinding.root
    }

    // //////////////////////
    // OBSERVERS
    private fun setupObservers(homeFragmentBinding: FragmentHomeBinding) {
        homeViewModel.errorLiveData.observe(viewLifecycleOwner,
            {
                logger.e(
                    LOG_TAG,
                    "homeViewModel.errorLiveData::$it"
                )
                showErrorDialog(it)
            })
        homeViewModel.sessionPresetsLiveData.observe(viewLifecycleOwner,
            {
                logger.d(
                    LOG_TAG,
                    "homeViewModel.sessionPresetsLiveData::${it.size}"
                )
                if (it.isEmpty()) {
                    homeFragmentBinding.emptyListMessage.visibility = VISIBLE
                    sessionPresetsAdapter.submitList(it)
                }
                else {
                    homeFragmentBinding.emptyListMessage.visibility = GONE
                    sessionPresetsAdapter.submitList(it)
                }
            })
    }

    // //////////////////////
    // ERROR DISPLAY
    private fun showErrorDialog(errorMessage: String) {
        val errorDialog = BottomDialogDismissibleErrorMessage.newInstance(
            title = getString(R.string.generic_string_ERROR),
            message = errorMessage
        )
        errorDialog.show(
            childFragmentManager,
            "openAboutDialog"
        )
    }

    // //////////////////////
    // TOOLBAR
    private fun setupToolbar(homeFragmentBinding: FragmentHomeBinding) {
        val toolbar: Toolbar = homeFragmentBinding.toolbar
        (activity as ScreenActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
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
        val aboutDialog = AboutDialog.newInstance()
        aboutDialog.show(
            childFragmentManager,
            "openAboutDialog"
        )
    }

    ////////////////////
    // FAB
    private fun setupAddSessionPresetFab(homeFragmentBinding: FragmentHomeBinding) {
        homeFragmentBinding.addSessionPresetFabSpeedDial.setSpeedDialItems(
            buildHomeFabSpeedDials(),
            logger
        )
    }

    private fun buildHomeFabSpeedDials(): List<FabSpeedDialItem> {
        val createFreeTimedSession = FabSpeedDialItem(
            R.drawable.ic_timer,
            {openCreateFreeTimedSession()},
            getString(R.string.free_timed_session)
        )
        //
        val createTimedSession = FabSpeedDialItem(
            R.drawable.ic_hourglass,
            {openCreateTimedSession()},
            getString(R.string.timed_session)
        )
        //
        val createAudioSession = FabSpeedDialItem(
            R.drawable.ic_audio_file,
            {openCreateAudioSession()},
            getString(R.string.audio_session)
        )
        //
        val createFreeAudioSession = FabSpeedDialItem(
            R.drawable.ic_audio_search,
            {openCreateFreeAudioSession()},
            getString(R.string.free_audio_session)
        )
        //
        return listOf(
            createFreeTimedSession,
            createTimedSession,
            createFreeAudioSession,
            createAudioSession
        )
    }

    private fun openCreateFreeAudioSession() {
        TODO("not implemented")
    }

    private fun openCreateAudioSession() {
        TODO("not implemented")
    }

    private fun openCreateTimedSession() {
        TODO("not implemented")
    }

    private fun openCreateFreeTimedSession() {
        TODO("not implemented")
    }

    private fun showSessionPresetDialog(preset: SessionPreset? = null) {
        val dialogFragment = SessionPresetDialog.newInstance(preset)
        dialogFragment.setSessionPresetDialogListener(sessionPresetDialogListener)
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(
            android.R.id.content,
            dialogFragment
        ).addToBackStack(null).commit()
    }

    private val sessionPresetDialogListener = object: SessionPresetDialog.SessionPresetDialogListener {
        override fun onConfirmButtonClicked(sessionPreset: SessionPreset) {
            homeViewModel.saveSessionPreset(
                sessionPreset,
                resources
            )
        }

        override fun onDeletePresetConfirmed(sessionPreset: SessionPreset) {
            homeViewModel.deleteSessionPreset(
                sessionPreset,
                resources
            )
        }

        override fun onDismissButtonClicked() {
            // force list refresh to get the swiped preset back
            sessionPresetsAdapter.notifyDataSetChanged()
        }
    }

    // //////////////////
    // SESSION PRESETS LIST
    private fun setUpSessionPresetsRecyclerView(homeFragmentBinding: FragmentHomeBinding) {
        val sessionPresetsLayoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = RecyclerView.VERTICAL
        }
        homeFragmentBinding.sessionPresetRecyclerView.apply {
            layoutManager = sessionPresetsLayoutManager
            adapter = sessionPresetsAdapter
            while (itemDecorationCount > 0) {
                removeItemDecorationAt(0)
            }
            addItemDecoration(SessionPresetItemDecoration(resources.getDimensionPixelSize(R.dimen.three_quarter_margin)))
            // listener on scroll state to hide/show the FAB, allowing for more legibility of underlying items
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(
                    recyclerView: RecyclerView,
                    newState: Int
                ) {
                    super.onScrollStateChanged(
                        recyclerView,
                        newState
                    )
                    when (newState) {
                        SCROLL_STATE_IDLE -> {
                            showHideAddSessionPresetButton(
                                homeFragmentBinding,
                                true
                            )
                        }

                        SCROLL_STATE_DRAGGING -> {
                            showHideAddSessionPresetButton(
                                homeFragmentBinding,
                                false
                            )
                        }
                    }
                }
            })
        }
        // swipe left/right behaviour
        context?.let {context ->
            val itemTouchHelper = ItemTouchHelper(getSwipeHandler(context))
            itemTouchHelper.attachToRecyclerView(homeFragmentBinding.sessionPresetRecyclerView)
        }
        // scroll to top of modified range in adapter
        sessionPresetsAdapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(
                positionStart: Int,
                itemCount: Int
            ) {
                sessionPresetsLayoutManager.scrollToPositionWithOffset(
                    positionStart,
                    0
                )
            }
        })
    }

    private fun getSwipeHandler(context: Context): SwipeInRecyclerViewCallback {
        val editIcon = ContextCompat.getDrawable(
            context,
            R.drawable.ic_button_edit
        )
        val moveToTopIcon = ContextCompat.getDrawable(
            context,
            R.drawable.ic_move_to_top
        )
        return object: SwipeInRecyclerViewCallback(
            rightIcon = editIcon,
            leftIcon = moveToTopIcon,
            backgroundColor = null,
            logger = logger
        ) {
            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val position = viewHolder.adapterPosition
                val swipedPreset = homeViewModel.sessionPresetsLiveData.value?.getOrNull(position)
                if (swipedPreset == null) {
                    logger.e(
                        LOG_TAG,
                        "onSwiped:: could not retrieve SessionPreset for position $position"
                    )
                }
                else {
                    when (direction) {
                        ItemTouchHelper.LEFT -> showSessionPresetDialog(swipedPreset)
                        ItemTouchHelper.RIGHT -> homeViewModel.moveSessionPresetToTop(
                            swipedPreset,
                            resources
                        )
                    }
                }
            }
        }
    }

    private fun showHideAddSessionPresetButton(
        homeFragmentBinding: FragmentHomeBinding,
        showIt: Boolean
    ) {
        if (showIt) {
            homeFragmentBinding.addSessionPresetFabSpeedDial.appear()
        }
        else {
            homeFragmentBinding.addSessionPresetFabSpeedDial.disappear()
        }
    }
}
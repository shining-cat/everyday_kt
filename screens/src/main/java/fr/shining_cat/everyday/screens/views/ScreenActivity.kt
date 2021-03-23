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

package fr.shining_cat.everyday.screens.views

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.ui.views.AbstractActivity
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.databinding.ActivityScreenBinding
import org.koin.android.ext.android.get

class ScreenActivity : AbstractActivity() {

    private val LOG_TAG = ScreenActivity::class.java.name

    private val logger: Logger = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screenActivityBinding = ActivityScreenBinding.inflate(layoutInflater)
        setContentView(screenActivityBinding.root)
        setupBottomNavigation(screenActivityBinding)
        hideLoadingView(screenActivityBinding.loadingLayout.loadingView)
    }

    private fun setupBottomNavigation(screenActivityBinding: ActivityScreenBinding) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.screens_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        screenActivityBinding.bottomNavView.setupWithNavController(navController)
    }
}
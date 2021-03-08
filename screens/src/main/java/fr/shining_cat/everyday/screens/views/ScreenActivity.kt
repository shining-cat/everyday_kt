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

import android.content.res.Resources
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.ui.views.AbstractActivity
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.databinding.ActivityScreenBinding
import org.koin.android.ext.android.get

class ScreenActivity : AbstractActivity() {

    private val LOG_TAG = ScreenActivity::class.java.simpleName

    private val logger: Logger = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screenActivityBinding = ActivityScreenBinding.inflate(layoutInflater)
        setContentView(screenActivityBinding.root)
        logger.d(
            LOG_TAG,
            "onCreate"
        )
        val navController = findNavController(R.id.screens_nav_host_fragment)
        setupBottomNavigation(
            screenActivityBinding,
            navController
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            }
            catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }
            logger.d(
                LOG_TAG,
                "Navigated to $dest"
            )
        }
        hideLoadingView(screenActivityBinding.loadingLayout.loadingView)
    }

    private fun setupBottomNavigation(
        screenActivityBinding: ActivityScreenBinding,
        navController: NavController
    ) {
        screenActivityBinding.bottomNavView.setupWithNavController(navController)
    }
}
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

package fr.shining_cat.everyday.commons.ui.views

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelper
import org.koin.android.ext.android.get

abstract class AbstractActivity : AppCompatActivity() {

    private val LOG_TAG = AbstractActivity::class.java.name

    private val logger: Logger = get()
    private val sharedPrefsHelper: SharedPrefsHelper = get()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        // apply theme setting from prefs before calling super.onCreate to prevent a double activity instantiation on app launch
        AppCompatDelegate.setDefaultNightMode(sharedPrefsHelper.getDefaultNightMode())
        super.onCreate(savedInstanceState)
        // we will ignore the orientation lock warning, as we only plan to display the app in portrait mode for now
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

// /////////////////////////////////
// LOADING VIEW
// /////////////////////////////////

    fun showLoadingView(loadingView: View) {
        loadingView.visibility = View.VISIBLE
    }

    fun hideLoadingView(loadingView: View) {
        loadingView.visibility = View.GONE
    }
}
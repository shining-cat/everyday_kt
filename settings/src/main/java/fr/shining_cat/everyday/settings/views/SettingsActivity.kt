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

package fr.shining_cat.everyday.settings.views

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.ui.views.AbstractActivity
import fr.shining_cat.everyday.settings.R
import fr.shining_cat.everyday.settings.databinding.ActivitySettingsBinding
import org.koin.android.ext.android.get

class SettingsActivity: AbstractActivity() {

    private val LOG_TAG = SettingsActivity::class.java.simpleName

    private val logger: Logger = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val settingsActivityBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(settingsActivityBinding.root)
        logger.d(
            LOG_TAG,
            "onCreate"
        )
        setToolBar(settingsActivityBinding)
        hideLoadingView(settingsActivityBinding.loadingLayout.loadingView)
        loadPreferencesFragment()
    }

    private fun loadPreferencesFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.settings_fragment_container,
            SettingsFragment()
        ).commit()
    }

    private fun setToolBar(settingsActivityBinding: ActivitySettingsBinding) {
        val toolbar: Toolbar = settingsActivityBinding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.title_settings)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
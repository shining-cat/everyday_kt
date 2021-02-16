package fr.shining_cat.everyday.settings.views

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.ui.views.AbstractActivity
import fr.shining_cat.everyday.settings.R
import fr.shining_cat.everyday.settings.databinding.ActivitySettingsBinding
import org.koin.android.ext.android.get

class SettingsActivity : AbstractActivity() {

    private val LOG_TAG = SettingsActivity::class.java.simpleName

    private val logger: Logger = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val settingsActivityBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(settingsActivityBinding.root)
        logger.d(LOG_TAG, "onCreate")
        setToolBar(settingsActivityBinding)
        hideLoadingView(settingsActivityBinding.loadingLayout.loadingView)
        loadPreferencesFragment()
    }

    private fun loadPreferencesFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_fragment_container, SettingsFragment())
            .commit()
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
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

    private val LOG_TAG = AbstractActivity::class.java.simpleName

    private val logger: Logger = get()
    private val sharedPrefsHelper: SharedPrefsHelper = get()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //we will ignore the orientation lock warning for now, as we only plan to display the app in portrait mode for now
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onResume() {
        super.onResume()
        //apply theme setting from prefs
        AppCompatDelegate.setDefaultNightMode(sharedPrefsHelper.getDefaultNightMode())
    }

///////////////////////////////////
// LOADING VIEW
///////////////////////////////////

    fun showLoadingView(loadingView: View) {
        loadingView.visibility = View.VISIBLE
    }

    fun hideLoadingView(loadingView: View) {
        loadingView.visibility = View.GONE
    }
}
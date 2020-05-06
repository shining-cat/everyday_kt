package fr.shining_cat.everyday.commons.ui.views

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.R
import fr.shining_cat.everyday.commons.extensions.bind
import org.koin.android.ext.android.get

abstract class AbstractActivity : AppCompatActivity() {

    private val LOG_TAG = AbstractActivity::class.java.simpleName

    private val logger: Logger = get()

    private val loadingView: View by bind(R.id.loading_view)

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //we will ignore the orientation lock warning for now, as we only plan to display the app in portrait mode for now
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    ///////////////////////////////////
    // LOADING VIEW
    ///////////////////////////////////

    fun showLoadingView() {
        loadingView.visibility = View.VISIBLE
        loadingView.setOnClickListener { hideLoadingView() }
    }

    fun hideLoadingView() {
        loadingView.visibility = View.GONE
    }
}
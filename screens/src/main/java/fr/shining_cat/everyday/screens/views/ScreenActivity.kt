package fr.shining_cat.everyday.screens.views

import android.os.Bundle
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.ui.views.AbstractActivity
import fr.shining_cat.everyday.screens.R
import org.koin.android.ext.android.get

class ScreenActivity : AbstractActivity() {

    private val LOG_TAG = ScreenActivity::class.java.name

    private val logger: Logger = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen)
        logger.d(LOG_TAG, "onCreate")
        hideLoadingView()
    }
}
package fr.shining_cat.everyday.views

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import fr.shining_cat.everyday.R
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.ui.views.AbstractActivity
import fr.shining_cat.everyday.viewmodels.SplashViewModel
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class SplashScreenActivity : AbstractActivity() {

    private val LOG_TAG = SplashScreenActivity::class.java.simpleName

    private val splashViewModel: SplashViewModel by viewModel()
    private val logger: Logger = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        logger.d(LOG_TAG, "onCreate")
        showLoadingView()
        splashViewModel.initReadyLiveData.observe(this, Observer {
            logger.d(LOG_TAG, "initReadyLiveData: $it")
            if (it) {
                startActivity(Intent("fr.shining_cat.everyday.screens.views.ScreenActivity"))
                finish()
            }
        })
        splashViewModel.loadConfInit()
    }
}

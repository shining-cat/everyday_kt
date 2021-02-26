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

package fr.shining_cat.everyday.views

import android.os.Bundle
import androidx.lifecycle.Observer
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.ui.views.AbstractActivity
import fr.shining_cat.everyday.databinding.ActivitySplashscreenBinding
import fr.shining_cat.everyday.navigation.Actions
import fr.shining_cat.everyday.navigation.Destination
import fr.shining_cat.everyday.viewmodels.SplashViewModel
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class SplashScreenActivity : AbstractActivity() {

    private val LOG_TAG = SplashScreenActivity::class.java.simpleName

    private val splashViewModel: SplashViewModel by viewModel()
    private val logger: Logger = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activitySplashscreenBinding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(activitySplashscreenBinding.root)
        logger.d(LOG_TAG, "onCreate")
        showLoadingView(activitySplashscreenBinding.loadingLayout.loadingView)
        splashViewModel.initReadyLiveData.observe(
            this,
            Observer {
                logger.d(LOG_TAG, "initReadyLiveData: $it")
                if (it) {
                    startActivity(Actions.openDestination(this, Destination.HomeDestination()))
                    finish()
                } else {
                    logger.d(LOG_TAG, "tralala sdfklj zef lkjzef1zeF ZEN F FKSDJFIFSDLKJF    FGJSDFGLKJ  sdmkljfdsfj  ks flksdj esd kf zelf  selkf  sdf  sd fnsdf jksd f hzesjkfh ezjkfhsd  kjhsefl  hzesf  kse jh dfkjgh erkj  gergh ekjg  erjkghesrl ")
                }
            }
        )
        try {
            logger.d(LOG_TAG, "tralala sdfklj zef lkjzef1zeF ZEN Ff  selkf  sdf")
            if (splashViewModel != null) { logger.d(LOG_TAG, "not nul!") } else { logger.d(LOG_TAG, "null!") }
        } catch (e: Exception) { logger.d(LOG_TAG, "exception!") }
        splashViewModel.loadConfInit()
    }
}
/*
        splashViewModel.initReadyLiveData.observe(this,Observer {logger.d(LOG_TAG, "initReadyLiveData: $it")
                if (it) {
                    startActivity(Actions.openDestination(this, Destination.HomeDestination()))
                    finish()
                } else {
                    logger.d(LOG_TAG, "tralala sdfklj zef lkjzef1zeF ZEN F FKSDJFIFSDLKJF    FGJSDFGLKJ  sdmkljfdsfj  ks flksdj esd kf zelf  selkf  sdf  sd fnsdf jksd f hzesjkfh ezjkfhsd  kjhsefl  hzesf  kse jh dfkjgh erkj  gergh ekjg  erjkghesrl ") }
            }
        )
        try {
            logger.d(LOG_TAG, "tralala sdfklj zef lkjzef1zeF ZEN Ff  selkf  sdf")
            if(splashViewModel != null){logger.d(LOG_TAG, "not nul!")}else{logger.d(LOG_TAG, "null!")}
        }catch (e:Exception){logger.d(LOG_TAG, "exception!")}
 */
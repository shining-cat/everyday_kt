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

package fr.shining_cat.everyday.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.viewmodels.AbstractViewModels
import fr.shining_cat.everyday.commons.viewmodels.AppDispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    appDispatchers: AppDispatchers,
    private val logger: Logger
) : AbstractViewModels(appDispatchers) {

    private val LOG_TAG = SplashViewModel::class.java.simpleName

    private val _initReadyLiveData = MutableLiveData(false)
    val initReadyLiveData: LiveData<Boolean> = _initReadyLiveData

    fun loadConfInit() {
        logger.d(LOG_TAG, "loadConfInit")
        mainScope.launch {
            // Add a minimum delay for the splash screen duration to
            // avoid a brutal transition
            logger.d(LOG_TAG, "loadConfInit:delayDeferred...")
            val delayDeferred = ioScope.async {
                delay(Constants.SPLASH_MIN_DURATION_MILLIS)
            }
            //TODO: load user settings from SharedPrefs and apply, then pursue

            delayDeferred.await()
            logger.d(LOG_TAG, "loadConfInit:delayDeferred:DONE WAITING")
            _initReadyLiveData.value = true
        }
    }

}
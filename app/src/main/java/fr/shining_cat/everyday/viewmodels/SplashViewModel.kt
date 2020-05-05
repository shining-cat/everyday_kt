package fr.shining_cat.everyday.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.shining_cat.everyday.commons.AppDispatchers
import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.viewmodels.AbstractViewModels
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    appDispatchers: AppDispatchers,
    private val logger: Logger
) : AbstractViewModels(appDispatchers) {

    private val LOG_TAG = SplashViewModel::class.java.name

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
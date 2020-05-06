package fr.shining_cat.everyday.screens.views.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.shining_cat.everyday.commons.AppDispatchers
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.viewmodels.AbstractViewModels
import kotlinx.coroutines.launch

class HomeViewModel(
    appDispatchers: AppDispatchers,
    private val logger: Logger
) : AbstractViewModels(appDispatchers) {

    private val LOG_TAG = HomeViewModel::class.java.simpleName

    private val _initReadyLiveData = MutableLiveData<String>()
    val initReadyLiveData: LiveData<String> = _initReadyLiveData

    fun initViewModel() {
        logger.d(LOG_TAG, "initViewModel")
        mainScope.launch {
            _initReadyLiveData.value = LOG_TAG
        }
    }

}

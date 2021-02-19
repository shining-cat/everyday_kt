package fr.shining_cat.everyday.commons.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class AbstractViewModels(protected val appDispatchers: AppDispatchers) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    protected val mainScope = CoroutineScope(appDispatchers.main + viewModelJob)
    protected val ioScope = CoroutineScope(appDispatchers.io + viewModelJob)

    override fun onCleared() {
        super.onCleared()

        mainScope.cancel()
        ioScope.cancel()
    }

}
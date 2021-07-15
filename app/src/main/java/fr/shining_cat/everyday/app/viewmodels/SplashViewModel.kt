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

package fr.shining_cat.everyday.app.viewmodels

import android.content.Context
import android.media.RingtoneManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.viewmodels.AbstractViewModels
import fr.shining_cat.everyday.domain.InitDefaultPrefsValuesUseCase
import fr.shining_cat.everyday.navigation.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(
    private val initDefaultPrefsValuesUseCase: InitDefaultPrefsValuesUseCase,
    private val logger: Logger
) : AbstractViewModels() {

    private val LOG_TAG = SplashViewModel::class.java.name

    private val _initLiveData = MutableLiveData<Destination>()
    val initLiveData: LiveData<Destination> = _initLiveData

    fun loadConfInit(context: Context) {
        logger.d(
            LOG_TAG,
            "loadConfInit"
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val deviceDefaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION)
                val deviceDefaultRingtoneTitle = RingtoneManager.getRingtone(context, deviceDefaultRingtoneUri).getTitle(context)
                initDefaultPrefsValuesUseCase.execute(context, deviceDefaultRingtoneUri, deviceDefaultRingtoneTitle)
            }
            // TODO: returning only HomeDestination for now, this is where we will plug deeplinks
            _initLiveData.value = Destination.HomeDestination()
        }
    }
}
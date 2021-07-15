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

package fr.shining_cat.everyday.screens.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.viewmodels.AbstractViewModels
import kotlinx.coroutines.launch

class StatisticsViewModel(
    private val logger: Logger
) : AbstractViewModels() {

    private val LOG_TAG = StatisticsViewModel::class.java.name

    private val _initReadyLiveData = MutableLiveData<String>()
    val initReadyLiveData: LiveData<String> = _initReadyLiveData

    fun initViewModel() {
        logger.d(
            LOG_TAG,
            "initViewModel"
        )
        viewModelScope.launch {
            _initReadyLiveData.value = LOG_TAG
        }
    }
}
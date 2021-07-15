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
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_CODE_NO_RESULT
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.viewmodels.AbstractViewModels
import fr.shining_cat.everyday.domain.Result
import fr.shining_cat.everyday.domain.sessionspresets.LoadSessionPresetsUseCase
import fr.shining_cat.everyday.domain.sessionspresets.UpdateSessionPresetUseCase
import fr.shining_cat.everyday.models.SessionPreset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val loadSessionPresetsUseCase: LoadSessionPresetsUseCase,
    private val updateSessionPresetUseCase: UpdateSessionPresetUseCase,
    private val logger: Logger
) : AbstractViewModels() {

    private val LOG_TAG = HomeViewModel::class.java.name

    private val _sessionPresetsLiveData = MutableLiveData<List<SessionPreset>>()
    val sessionPresetsLiveData: LiveData<List<SessionPreset>> = _sessionPresetsLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    fun fetchSessionPresets(nothingFoundMessage: String) {
        loadSessionPresets(nothingFoundMessage)
    }

    private fun loadSessionPresets(nothingFoundMessage: String) {
        viewModelScope.launch {
            val sessionPresetsResult = withContext(Dispatchers.IO) {
                loadSessionPresetsUseCase.execute()
            }
            if (sessionPresetsResult is Result.Success) {
                val sessionsList = sessionPresetsResult.result
                logger.d(
                    LOG_TAG,
                    "loadSessionPresets::success::sessionsList.size = ${sessionsList.size} => setting new value to _sessionPresetsLiveData"
                )
                _sessionPresetsLiveData.value = sessionsList
            }
            else {
                sessionPresetsResult as Result.Error
                // reset list
                _sessionPresetsLiveData.value = listOf()
                if (sessionPresetsResult.errorCode == ERROR_CODE_NO_RESULT) {
                    _errorLiveData.value = nothingFoundMessage
                }
                else {
                    _errorLiveData.value = sessionPresetsResult.errorResponse
                }
            }
        }
    }

    fun moveSessionPresetToTop(
        sessionPreset: SessionPreset,
        nothingFoundMessage: String
    ) {
        logger.d(
            LOG_TAG,
            "moveSessionPresetToTop"
        )
        val sessionPresetOut = when (sessionPreset) {
            is SessionPreset.AudioSessionPreset -> sessionPreset.copy(lastEditTime = System.currentTimeMillis())
            is SessionPreset.AudioFreeSessionPreset -> sessionPreset.copy(lastEditTime = System.currentTimeMillis())
            is SessionPreset.TimedSessionPreset -> sessionPreset.copy(lastEditTime = System.currentTimeMillis())
            is SessionPreset.TimedFreeSessionPreset -> sessionPreset.copy(lastEditTime = System.currentTimeMillis())

            else -> {
                logger.e(
                    LOG_TAG,
                    "moveSessionPresetToTop::UNKNOWN SESSION PRESET TYPE"
                )
                SessionPreset.UnknownSessionPreset()
            }
        }
        updateSessionPreset(
            sessionPresetOut,
            nothingFoundMessage
        )
    }

    //
    private fun updateSessionPreset(
        sessionPreset: SessionPreset,
        nothingFoundMessage: String
    ) {
        viewModelScope.launch {
            val recordSessionPresetResult = withContext(Dispatchers.IO) {
                updateSessionPresetUseCase.execute(sessionPreset)
            }
            if (recordSessionPresetResult is Result.Success) {
                // reload complete session presets list, will trigger list update on UI side
                loadSessionPresets(nothingFoundMessage)
            }
            else {
                logger.e(
                    LOG_TAG,
                    "saveSessionPreset::ERROR}"
                )
                recordSessionPresetResult as Result.Error
                _errorLiveData.value = recordSessionPresetResult.errorResponse
            }
        }
    }
}
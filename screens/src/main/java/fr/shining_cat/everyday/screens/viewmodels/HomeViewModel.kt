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


    init {
        System.out.println("init HomeViewModel")
    }

    fun fetchSessionPresets(nothingFoundMessage: String) {
        loadSessionPresets(nothingFoundMessage)
    }

    private fun loadSessionPresets(nothingFoundMessage: String) {
        System.out.println("loadSessionPresets")
        viewModelScope.launch {
            val sessionPresetsResult = withContext(Dispatchers.IO) {
                System.out.println("calling loadSessionPresetsUseCase.execute()")
                loadSessionPresetsUseCase.execute()
            }

            System.out.println("sessionPresetsResult= $sessionPresetsResult")
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
        System.out.println("updateSessionPreset")
        viewModelScope.launch {
            val updateSessionPresetResult = withContext(Dispatchers.IO) {
                System.out.println("calling updateSessionPresetUseCase.execute")
                updateSessionPresetUseCase.execute(sessionPreset)
            }
            System.out.println("updateSessionPresetResult = $updateSessionPresetResult")
            if (updateSessionPresetResult is Result.Success) {
                // reload complete session presets list, will trigger list update on UI side
                System.out.println("calling loadSessionPresets")
                loadSessionPresets(nothingFoundMessage)
            }
            else {
                logger.e(
                    LOG_TAG,
                    "saveSessionPreset::ERROR}"
                )
                System.out.println("NOT calling loadSessionPresets because update result was an error")
                updateSessionPresetResult as Result.Error
                _errorLiveData.value = updateSessionPresetResult.errorResponse
            }
        }
    }
}
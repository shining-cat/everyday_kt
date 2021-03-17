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

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_CODE_NO_RESULT
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.viewmodels.AbstractViewModels
import fr.shining_cat.everyday.commons.viewmodels.AppDispatchers
import fr.shining_cat.everyday.domain.Result
import fr.shining_cat.everyday.domain.sessionspresets.CreateSessionPresetUseCase
import fr.shining_cat.everyday.domain.sessionspresets.DeleteSessionPresetUseCase
import fr.shining_cat.everyday.domain.sessionspresets.LoadSessionPresetsUseCase
import fr.shining_cat.everyday.domain.sessionspresets.UpdateSessionPresetUseCase
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.screens.R
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel(
    appDispatchers: AppDispatchers,
    private val loadSessionPresetUseCase: LoadSessionPresetsUseCase,
    private val createSessionPresetUseCase: CreateSessionPresetUseCase,
    private val updateSessionPresetUseCase: UpdateSessionPresetUseCase,
    private val deleteSessionPresetUseCase: DeleteSessionPresetUseCase,
    private val logger: Logger
) : AbstractViewModels(appDispatchers) {

    private val LOG_TAG = HomeViewModel::class.java.simpleName

    private val _sessionPresetsLiveData = MutableLiveData<List<SessionPreset>>()
    val sessionPresetsLiveData: LiveData<List<SessionPreset>> = _sessionPresetsLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    fun initViewModel(resources: Resources) {
        loadSessionPresets(resources)
    }

    private fun loadSessionPresets(resources: Resources) {
        mainScope.launch {
            val sessionPresetsResult = ioScope.async {
                loadSessionPresetUseCase.execute()
            }.await()
            if (sessionPresetsResult is Result.Success) {
                val sessionsList = sessionPresetsResult.result
                _sessionPresetsLiveData.value = sessionsList
            }
            else {
                sessionPresetsResult as Result.Error
                //reset list
                _sessionPresetsLiveData.value = listOf()
                if (sessionPresetsResult.errorCode == ERROR_CODE_NO_RESULT) {
                    _errorLiveData.value = resources.getString(R.string.no_session_preset_found)
                }
                else {
                    _errorLiveData.value = sessionPresetsResult.errorResponse
                }
            }
        }
    }

    fun saveSessionPreset(sessionPreset: SessionPreset, resources: Resources) {
        mainScope.launch {
            val recordSessionPresetResult = ioScope.async {
                if (sessionPreset.id == -1L) {
                    createSessionPresetUseCase.execute(sessionPreset)
                }
                else {
                    updateSessionPresetUseCase.execute(sessionPreset)
                }
            }.await()
            if (recordSessionPresetResult is Result.Success) {
                //reload complete session presets list, will trigger list update on UI side
                loadSessionPresets(resources)
            }
            else {
                recordSessionPresetResult as Result.Error
                _errorLiveData.value = recordSessionPresetResult.errorResponse
            }
        }
    }

    fun deleteSessionPreset(sessionPreset: SessionPreset, resources: Resources) {
        mainScope.launch {
            val deleteSessionPresetResult = ioScope.async {
                deleteSessionPresetUseCase.execute(sessionPreset)
            }.await()
            if (deleteSessionPresetResult is Result.Success) {
                //reload complete session presets list, will trigger list update on UI side
                loadSessionPresets(resources)
            }
            else {
                deleteSessionPresetResult as Result.Error
                _errorLiveData.value = deleteSessionPresetResult.errorResponse
            }
        }
    }

    fun moveSessionPresetToTop(sessionPreset: SessionPreset, resources: Resources) {
        val sessionPreset = sessionPreset.copy(lastEditTime = System.currentTimeMillis())
        saveSessionPreset(sessionPreset, resources)
    }
}
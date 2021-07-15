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

package fr.shining_cat.everyday.screens.viewmodels.sessionpresets

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.viewmodels.AbstractViewModels
import fr.shining_cat.everyday.commons.viewmodels.AppDispatchers
import fr.shining_cat.everyday.domain.Result
import fr.shining_cat.everyday.domain.sessionspresets.CreateSessionPresetUseCase
import fr.shining_cat.everyday.domain.sessionspresets.DeleteSessionPresetUseCase
import fr.shining_cat.everyday.domain.sessionspresets.UpdateSessionPresetUseCase
import fr.shining_cat.everyday.models.SessionPreset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class AbstractSessionPresetViewModel(
    appDispatchers: AppDispatchers,
    private val createSessionPresetUseCase: CreateSessionPresetUseCase,
    private val updateSessionPresetUseCase: UpdateSessionPresetUseCase,
    private val deleteSessionPresetUseCase: DeleteSessionPresetUseCase,
    private val logger: Logger
) : AbstractViewModels(appDispatchers) {

    private val LOG_TAG = AbstractSessionPresetViewModel::class.java.name

    protected val _sessionPresetUpdatedLiveData = MutableLiveData<SessionPreset>()
    val sessionPresetUpdatedLiveData: LiveData<SessionPreset> = _sessionPresetUpdatedLiveData

    private val _successLiveData = MutableLiveData<Boolean>()
    val successLiveData: LiveData<Boolean> = _successLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    abstract fun isSessionPresetValid(): Boolean

    abstract fun init(
        context: Context,
        presetInput: SessionPreset?,
        deviceDefaultRingtoneUriString: String,
        deviceDefaultRingtoneName: String
    )

    abstract fun updatePresetDuration(inputDuration: Long)

    abstract fun updatePresetStartAndEndSoundUriString(inputStartAndEndSoundUriString: String)

    abstract fun updatePresetStartAndEndSoundName(inputStartAndEndSoundName: String)

    abstract fun updatePresetIntermediateIntervalLength(inputIntermediateIntervalLength: Long)

    abstract fun updatePresetStartCountdownLength(inputStartCountdownLength: Long)

    abstract fun updatePresetIntermediateIntervalRandom(inputIntermediateIntervalRandom: Boolean)

    abstract fun updatePresetIntermediateIntervalSoundUriString(inputIntermediateIntervalSoundUriString: String)

    abstract fun updatePresetIntermediateIntervalSoundName(inputIntermediateIntervalSoundName: String)

    abstract fun updatePresetVibration(inputVibration: Boolean)

    abstract fun updatePresetSessionTypeId(inputSessionTypeId: Int)

    fun saveSessionPreset(sessionPreset: SessionPreset) {
        viewModelScope.launch {
            val recordSessionPresetResult = withContext(Dispatchers.IO){
                if (sessionPreset.id == -1L) {
                    createSessionPresetUseCase.execute(sessionPreset)
                } else {
                    updateSessionPresetUseCase.execute(sessionPreset)
                }
            }
            if (recordSessionPresetResult is Result.Success) {
                _successLiveData.value = true
            } else {
                recordSessionPresetResult as Result.Error
                _errorLiveData.value = recordSessionPresetResult.errorResponse
            }
        }
    }

    fun deleteSessionPreset(sessionPreset: SessionPreset) {
        viewModelScope.launch {
            val deleteSessionPresetResult =  withContext(Dispatchers.IO){
                deleteSessionPresetUseCase.execute(sessionPreset)
            }
            if (deleteSessionPresetResult is Result.Success) {
                _successLiveData.value = true
            } else {
                deleteSessionPresetResult as Result.Error
                _errorLiveData.value = deleteSessionPresetResult.errorResponse
            }
        }
    }
}
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
import androidx.lifecycle.MutableLiveData
import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.domain.sessionspresets.CreateSessionPresetUseCase
import fr.shining_cat.everyday.domain.sessionspresets.DeleteSessionPresetUseCase
import fr.shining_cat.everyday.domain.sessionspresets.UpdateSessionPresetUseCase
import fr.shining_cat.everyday.models.SessionPreset

class AudioFreeSessionPresetViewModel(
    createSessionPresetUseCase: CreateSessionPresetUseCase,
    updateSessionPresetUseCase: UpdateSessionPresetUseCase,
    deleteSessionPresetUseCase: DeleteSessionPresetUseCase,
    private val logger: Logger
) : AbstractSessionPresetViewModel(
    createSessionPresetUseCase,
    updateSessionPresetUseCase,
    deleteSessionPresetUseCase,
    logger
) {

    private val LOG_TAG = AudioFreeSessionPresetViewModel::class.java.name

    override fun init(
        context: Context,
        presetInput: SessionPreset?,
        deviceDefaultRingtoneUriString: String,
        deviceDefaultRingtoneName: String
    ) {
        if (presetInput != null) {
            initForEdition(presetInput)
        }
        else {
            initForCreation(
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
    }

    private fun initForCreation(
        deviceDefaultRingtoneUriString: String,
        deviceDefaultRingtoneName: String
    ) {
        _sessionPresetUpdatedLiveData.value = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
    }

    private fun initForEdition(presetInput: SessionPreset) {
        _sessionPresetUpdatedLiveData.value = (presetInput as SessionPreset.AudioFreeSessionPreset).copy()
    }

    override fun verifyPresetValidity(): Boolean {
        return true // audio free session preset has nothing that could be invalid
    }

    override fun updatePresetStartAndEndSoundUriString(inputStartAndEndSoundUriString: String) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioFreeSessionPreset>
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(startAndEndSoundUriString = inputStartAndEndSoundUriString)
    }

    override fun updatePresetStartAndEndSoundName(inputStartAndEndSoundName: String) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioFreeSessionPreset>
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(startAndEndSoundName = inputStartAndEndSoundName)
    }

    override fun updatePresetStartCountdownLength(inputStartCountdownLength: Long) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioFreeSessionPreset>
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(startCountdownLength = inputStartCountdownLength)
    }

    override fun updatePresetVibration(inputVibration: Boolean) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioFreeSessionPreset>
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(vibration = inputVibration)
    }

    override fun updatePresetSessionTypeId(inputSessionTypeId: Long) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioFreeSessionPreset>
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(sessionTypeId = inputSessionTypeId)
    }

    override fun updatePresetIntermediateIntervalRandom(inputIntermediateIntervalRandom: Boolean) {}
    override fun updatePresetDuration(inputDuration: Long) {}
    override fun updatePresetIntermediateIntervalLength(inputIntermediateIntervalLength: Long) {}
    override fun updatePresetIntermediateIntervalSoundUriString(inputIntermediateIntervalSoundUriString: String) {}
    override fun updatePresetIntermediateIntervalSoundName(inputIntermediateIntervalSoundName: String) {}
}
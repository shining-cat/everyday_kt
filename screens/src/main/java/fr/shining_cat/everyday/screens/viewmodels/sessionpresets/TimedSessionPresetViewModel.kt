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
import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.domain.sessionspresets.CreateSessionPresetUseCase
import fr.shining_cat.everyday.domain.sessionspresets.DeleteSessionPresetUseCase
import fr.shining_cat.everyday.domain.sessionspresets.UpdateSessionPresetUseCase
import fr.shining_cat.everyday.models.SessionPreset

class TimedSessionPresetViewModel(
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

    private val LOG_TAG = TimedSessionPresetViewModel::class.java.name

    private val _validDurationLiveData = MutableLiveData<Boolean>()
    val invalidDurationLiveData: LiveData<Boolean> = _validDurationLiveData

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
        _sessionPresetUpdatedLiveData.value = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
    }

    private fun initForEdition(presetInput: SessionPreset) {
        _sessionPresetUpdatedLiveData.value = (presetInput as SessionPreset.TimedSessionPreset).copy()
    }

    override fun verifyPresetValidity(): Boolean {
        val preset = (_sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.TimedSessionPreset>).value
        when {
            preset == null -> {
                logger.e(
                    LOG_TAG,
                    "isSessionPresetValid::preset should not be null at this point"
                )
            }

            preset.duration <= 0L -> {
                _validDurationLiveData.value = false
            }

            else -> {
                return true
            }
        }
        return false
    }

    override fun updatePresetStartAndEndSoundUriString(inputStartAndEndSoundUriString: String) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.TimedSessionPreset>
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(startAndEndSoundUriString = inputStartAndEndSoundUriString)
    }

    override fun updatePresetStartAndEndSoundName(inputStartAndEndSoundName: String) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.TimedSessionPreset>
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(startAndEndSoundName = inputStartAndEndSoundName)
    }

    override fun updatePresetStartCountdownLength(inputStartCountdownLength: Long) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.TimedSessionPreset>
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(startCountdownLength = inputStartCountdownLength)
    }

    override fun updatePresetVibration(inputVibration: Boolean) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.TimedSessionPreset>
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(vibration = inputVibration)
    }

    override fun updatePresetSessionTypeId(inputSessionTypeId: Int) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.TimedSessionPreset>
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(sessionTypeId = inputSessionTypeId)
    }

    override fun updatePresetIntermediateIntervalRandom(inputIntermediateIntervalRandom: Boolean) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.TimedSessionPreset>
        _sessionPresetUpdatedLiveData.value =
            tempSessionPresetUpdatedLiveData.value?.copy(intermediateIntervalRandom = inputIntermediateIntervalRandom)
    }

    override fun updatePresetDuration(inputDuration: Long) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.TimedSessionPreset>
        _validDurationLiveData.value = inputDuration > 0L
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(duration = inputDuration)
    }

    override fun updatePresetIntermediateIntervalLength(inputIntermediateIntervalLength: Long) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.TimedSessionPreset>
        _sessionPresetUpdatedLiveData.value =
            tempSessionPresetUpdatedLiveData.value?.copy(intermediateIntervalLength = inputIntermediateIntervalLength)
    }

    override fun updatePresetIntermediateIntervalSoundUriString(inputIntermediateIntervalSoundUriString: String) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.TimedSessionPreset>
        _sessionPresetUpdatedLiveData.value =
            tempSessionPresetUpdatedLiveData.value?.copy(intermediateIntervalSoundUriString = inputIntermediateIntervalSoundUriString)
    }

    override fun updatePresetIntermediateIntervalSoundName(inputIntermediateIntervalSoundName: String) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.TimedSessionPreset>
        _sessionPresetUpdatedLiveData.value =
            tempSessionPresetUpdatedLiveData.value?.copy(intermediateIntervalSoundName = inputIntermediateIntervalSoundName)
    }
}
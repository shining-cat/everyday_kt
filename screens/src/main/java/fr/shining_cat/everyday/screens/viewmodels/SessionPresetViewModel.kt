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

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.commons.Constants.Companion.INTERVAL_LENGTH_IS_NONE_FOR_AUDIO_SESSION
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.viewmodels.AbstractViewModels
import fr.shining_cat.everyday.commons.viewmodels.AppDispatchers
import fr.shining_cat.everyday.domain.FileMetadataRetrieveUseCase
import fr.shining_cat.everyday.models.SessionPreset

class SessionPresetViewModel(
    appDispatchers: AppDispatchers,
    private val metadataRetrieveUseCase: FileMetadataRetrieveUseCase,
    private val logger: Logger
): AbstractViewModels(appDispatchers) {

    private val LOG_TAG = SessionPresetViewModel::class.java.name

    private val _sessionPresetUpdatedLiveData = MutableLiveData<SessionPreset>()
    val sessionPresetUpdatedLiveData: LiveData<SessionPreset> = _sessionPresetUpdatedLiveData

    fun isSessionPresetValid(): Boolean {
        return (_sessionPresetUpdatedLiveData.value != null && _sessionPresetUpdatedLiveData.value?.duration != -1L)
    }

    fun init(
        context: Context,
        presetInput: SessionPreset?
    ) {
        val deviceDefaultRingtoneUriString = RingtoneManager.getActualDefaultRingtoneUri(
            context,
            RingtoneManager.TYPE_NOTIFICATION
        ).toString()
        val deviceDefaultRingtoneName = RingtoneManager.getRingtone(
            context,
            Uri.parse(deviceDefaultRingtoneUriString)
        ).getTitle(context)
        //
        val isAnAudioSession = (presetInput != null && presetInput.audioGuideSoundUriString.isNotBlank())
        //attempt to retrieve fresh audio file metadata
        val audioFileMetadata = if (isAnAudioSession) {
            presetInput as SessionPreset
            logger.d(LOG_TAG, "init::presetInput.audioGuideSoundUriString = ${presetInput.audioGuideSoundUriString}")
            val audioGuideSoundUri = Uri.parse(presetInput.audioGuideSoundUriString)
            logger.d(LOG_TAG, "init::audioGuideSoundUri = $audioGuideSoundUri")
            metadataRetrieveUseCase.execute(
                context,
                audioGuideSoundUri
            )
        }
        else null

        logger.d(LOG_TAG, "init::audioFileMetadata?.artistName = ${audioFileMetadata?.artistName}")
        logger.d(LOG_TAG, "init::audioFileMetadata?.albumName = ${audioFileMetadata?.albumName}")
        logger.d(LOG_TAG, "init::audioFileMetadata?.fileName = ${audioFileMetadata?.fileName}")
        //
        val duration = if (audioFileMetadata != null) {
            if (audioFileMetadata.durationMs != -1L) {
                audioFileMetadata.durationMs
            }
            else {
                -1L //=> audio file duration could not be retrieved => we need the user to input it manually
            }
        }
        else presetInput?.duration ?: Constants.DEFAULT_SESSION_DURATION_MILLIS
        //
        _sessionPresetUpdatedLiveData.value = SessionPreset(
            id = presetInput?.id ?: -1L,
            startCountdownLength = presetInput?.startCountdownLength ?: Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = presetInput?.startAndEndSoundUriString ?: deviceDefaultRingtoneUriString,
            startAndEndSoundName = presetInput?.startAndEndSoundName ?: deviceDefaultRingtoneName,
            intermediateIntervalLength = if (isAnAudioSession) {
                INTERVAL_LENGTH_IS_NONE_FOR_AUDIO_SESSION //audio session  => no intermediate interval
            }
            else {
                presetInput?.intermediateIntervalLength ?: 0L
            },
            intermediateIntervalRandom = if (isAnAudioSession) {
                false //audio session  => no intermediate interval
            }
            else {
                presetInput?.intermediateIntervalRandom ?: false
            },
            intermediateIntervalSoundUriString = if (isAnAudioSession) {
                "" //audio session  => no intermediate interval "" is equivalent for "silence" here
            }
            else {
                presetInput?.intermediateIntervalSoundUriString ?: deviceDefaultRingtoneUriString
            },
            intermediateIntervalSoundName = if (isAnAudioSession) {
                "" //audio session  => no intermediate interval "" is not equivalent for "silence" here
            }
            else {
                presetInput?.intermediateIntervalSoundUriString ?: deviceDefaultRingtoneName
            },
            duration = duration,
            audioGuideSoundUriString = presetInput?.audioGuideSoundUriString ?: "",
            audioGuideSoundArtistName = audioFileMetadata?.artistName ?: "",
            audioGuideSoundAlbumName = audioFileMetadata?.albumName ?: "",
            audioGuideSoundTitle = audioFileMetadata?.fileName ?: "",
            vibration = false,
            sessionTypeId = -1L,
            lastEditTime = -1L,
        )
    }

    fun updatePresetDuration(inputDuration: Long) {
        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(duration = inputDuration)
    }

    fun updatePresetStartAndEndSoundUriString(inputStartAndEndSoundUriString: String) {
        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(startAndEndSoundUriString = inputStartAndEndSoundUriString)
    }

    fun updatePresetStartAndEndSoundName(inputStartAndEndSoundName: String) {
        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(startAndEndSoundName = inputStartAndEndSoundName)
    }

    fun updatePresetIntermediateIntervalLength(inputIntermediateIntervalLength: Long) {
        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(intermediateIntervalLength = inputIntermediateIntervalLength)
    }

    fun updatePresetStartCountdownLength(inputStartCountdownLength: Long) {
        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(startCountdownLength = inputStartCountdownLength)
    }

    fun updatePresetIntermediateIntervalRandom(inputIntermediateIntervalRandom: Boolean) {
        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(intermediateIntervalRandom = inputIntermediateIntervalRandom)
    }

    fun updatePresetIntermediateIntervalSoundUriString(inputIntermediateIntervalSoundUriString: String) {
        _sessionPresetUpdatedLiveData.value =
            _sessionPresetUpdatedLiveData.value?.copy(intermediateIntervalSoundUriString = inputIntermediateIntervalSoundUriString)
    }

    fun updatePresetIntermediateIntervalSoundName(inputIntermediateIntervalSoundName: String) {
        _sessionPresetUpdatedLiveData.value =
            _sessionPresetUpdatedLiveData.value?.copy(intermediateIntervalSoundName = inputIntermediateIntervalSoundName)
    }

    fun updatePresetAudioGuideSoundUriString(
        context: Context,
        inputAudioGuideSoundUriString: String
    ) {
        val audioFileMetadata = if (inputAudioGuideSoundUriString.isNotBlank()) {
            val audioGuideSoundUri = Uri.parse(inputAudioGuideSoundUriString)
            metadataRetrieveUseCase.execute(
                context,
                audioGuideSoundUri
            )
        }
        else null
        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(
            audioGuideSoundUriString = inputAudioGuideSoundUriString,
            audioGuideSoundArtistName = audioFileMetadata?.artistName ?: "",
            audioGuideSoundAlbumName = audioFileMetadata?.albumName ?: "",
            audioGuideSoundTitle = audioFileMetadata?.fileName ?: "",
            intermediateIntervalLength = INTERVAL_LENGTH_IS_NONE_FOR_AUDIO_SESSION,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = "",
            intermediateIntervalSoundName = "",
            duration = audioFileMetadata?.durationMs ?: -1L
        )
    }

    fun updatePresetVibration(inputVibration: Boolean) {
        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(vibration = inputVibration)
    }

    fun updatePresetSessionTypeId(inputSessionTypeId: Long) {
        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(sessionTypeId = inputSessionTypeId)
    }
}
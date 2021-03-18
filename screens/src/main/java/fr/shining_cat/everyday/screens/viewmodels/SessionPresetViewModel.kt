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

    private val LOG_TAG = SessionPresetViewModel::class.java.simpleName

    private val _sessionPresetUpdatedLiveData = MutableLiveData<SessionPreset>()
    val sessionPresetUpdatedLiveData: LiveData<SessionPreset> = _sessionPresetUpdatedLiveData

    fun init(
        context: Context,
        presetInput: SessionPreset?
    ) {
        val deviceDefaultRingtoneUriString = RingtoneManager.getActualDefaultRingtoneUri(
            context,
            RingtoneManager.TYPE_NOTIFICATION
        ).toString()
        //
        var audioFileMetadata = if (presetInput != null && presetInput.audioGuideSoundUriString.isNotBlank()) {
            val audioGuideSoundUri = Uri.parse(presetInput.audioGuideSoundUriString)
            metadataRetrieveUseCase.execute(
                context,
                audioGuideSoundUri
            )
        }
        else null
        _sessionPresetUpdatedLiveData.value = SessionPreset(
            id = presetInput?.id ?: -1L,
            startCountdownLength = presetInput?.startCountdownLength ?: Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = if (presetInput?.startAndEndSoundUriString == null) deviceDefaultRingtoneUriString else presetInput.startAndEndSoundUriString,
            intermediateIntervalLength = presetInput?.intermediateIntervalLength ?: 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = if (presetInput?.intermediateIntervalSoundUriString == null) deviceDefaultRingtoneUriString else presetInput.intermediateIntervalSoundUriString,
            duration = presetInput?.duration ?: Constants.DEFAULT_SESSION_DURATION_MILLIS,
            audioGuideSoundUriString = presetInput?.audioGuideSoundUriString ?: "",
            audioGuideSoundArtistName = audioFileMetadata?.artistName ?: "",
            audioGuideSoundAlbumName = audioFileMetadata?.albumName ?: "",
            audioGuideSoundFileName = audioFileMetadata?.fileName ?: "",
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
            audioGuideSoundFileName = audioFileMetadata?.fileName ?: "",
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
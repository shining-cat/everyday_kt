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
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.viewmodels.AppDispatchers
import fr.shining_cat.everyday.domain.FileMetadataRetrieveUseCase
import fr.shining_cat.everyday.models.SessionPreset

class AudioSessionPresetViewModel(
    appDispatchers: AppDispatchers,
    private val metadataRetrieveUseCase: FileMetadataRetrieveUseCase,
    private val logger: Logger
): AbstractSessionPresetViewModel(
    appDispatchers,
    logger
) {

    private val LOG_TAG = AudioSessionPresetViewModel::class.java.name

    override fun init(
        context: Context,
        presetInput: SessionPreset?,
        deviceDefaultRingtoneUriString: String,
        deviceDefaultRingtoneName: String
    ) {
        if (presetInput != null) {
            initForEdition(
                context,
                presetInput
            )
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
        _sessionPresetUpdatedLiveData.value = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
    }

    private fun initForEdition(
        context: Context,
        presetInput: SessionPreset
    ) {
        val audioGuideSoundUriString = presetInput.audioGuideSoundUriString // should never be blank at this stage
        var audioGuideSoundArtistName = presetInput.audioGuideSoundArtistName// if empty, try to retrieve the info from the file metadata
        var audioGuideSoundAlbumName = presetInput.audioGuideSoundAlbumName// if empty, try to retrieve the info from the file metadata
        var audioGuideSoundTitle = presetInput.audioGuideSoundTitle// if empty, try to retrieve the info from the file metadata
        var duration = presetInput.duration// if empty, try to retrieve the info from the file metadata
        if (audioGuideSoundUriString.isNotBlank() && (audioGuideSoundArtistName.isBlank() || audioGuideSoundAlbumName.isBlank() || audioGuideSoundTitle.isBlank() || duration == -1L)) {
            val audioGuideSoundUri = Uri.parse(presetInput.audioGuideSoundUriString)
            val audioFileMetadata = metadataRetrieveUseCase.execute(
                context,
                audioGuideSoundUri
            )
            audioGuideSoundArtistName = audioFileMetadata.artistName
            audioGuideSoundAlbumName = audioFileMetadata.albumName
            audioGuideSoundTitle = audioFileMetadata.fileName
            duration =
                audioFileMetadata.durationMs // == -1L is duration could not be retrieved, this case will cause a request for a user input duration
        }
        //
        _sessionPresetUpdatedLiveData.value = (presetInput as SessionPreset.AudioSessionPreset).copy(
            duration = duration,
            audioGuideSoundUriString = audioGuideSoundUriString,
            audioGuideSoundArtistName = audioGuideSoundArtistName,
            audioGuideSoundAlbumName = audioGuideSoundAlbumName,
            audioGuideSoundTitle = audioGuideSoundTitle
        )
    }

    override fun isSessionPresetValid(): Boolean {
        val preset = (_sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>).value
        return (preset != null && preset.audioGuideSoundUriString.isNotBlank() && preset.duration != -1L)
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
        _sessionPresetUpdatedLiveData.value = (_sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>).value?.copy(
            audioGuideSoundUriString = inputAudioGuideSoundUriString,
            audioGuideSoundArtistName = audioFileMetadata?.artistName ?: "",
            audioGuideSoundAlbumName = audioFileMetadata?.albumName ?: "",
            audioGuideSoundTitle = audioFileMetadata?.fileName ?: "",
            duration = audioFileMetadata?.durationMs ?: -1L
        )
    }

    override fun updatePresetDuration(inputDuration: Long) {
        _sessionPresetUpdatedLiveData.value =
            (_sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>).value?.copy(duration = inputDuration)
    }

    override fun updatePresetStartAndEndSoundUriString(inputStartAndEndSoundUriString: String) {
        _sessionPresetUpdatedLiveData.value =
            (_sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>).value?.copy(startAndEndSoundUriString = inputStartAndEndSoundUriString)
    }

    override fun updatePresetStartAndEndSoundName(inputStartAndEndSoundName: String) {
        _sessionPresetUpdatedLiveData.value =
            (_sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>).value?.copy(startAndEndSoundName = inputStartAndEndSoundName)
    }

    override fun updatePresetStartCountdownLength(inputStartCountdownLength: Long) {
        _sessionPresetUpdatedLiveData.value =
            (_sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>).value?.copy(startCountdownLength = inputStartCountdownLength)
    }

    override fun updatePresetVibration(inputVibration: Boolean) {
        _sessionPresetUpdatedLiveData.value =
            (_sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>).value?.copy(vibration = inputVibration)
    }

    override fun updatePresetSessionTypeId(inputSessionTypeId: Int) {
        _sessionPresetUpdatedLiveData.value =
            (_sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>).value?.copy(sessionTypeId = inputSessionTypeId)
    }

    override fun updatePresetIntermediateIntervalRandom(inputIntermediateIntervalRandom: Boolean) {}
    override fun updatePresetIntermediateIntervalLength(inputIntermediateIntervalLength: Long) {}
    override fun updatePresetIntermediateIntervalSoundUriString(inputIntermediateIntervalSoundUriString: String) {}
    override fun updatePresetIntermediateIntervalSoundName(inputIntermediateIntervalSoundName: String) {}
}
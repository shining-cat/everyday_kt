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
) : AbstractSessionPresetViewModel(appDispatchers, logger) {

    private val LOG_TAG = AudioSessionPresetViewModel::class.java.name

    override fun init(context: Context, presetInput: SessionPreset?, deviceDefaultRingtoneUriString: String, deviceDefaultRingtoneName: String) {
        super.init(context, presetInput, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName)
        if (presetInput != null) {
            initForEdition(context, presetInput)
        }
        else {
            initForCreation(deviceDefaultRingtoneUriString, deviceDefaultRingtoneName)
        }
    }

    private fun initForCreation(deviceDefaultRingtoneUriString: String, deviceDefaultRingtoneName: String) {
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
        val audioGuideSoundUri = Uri.parse(presetInput.audioGuideSoundUriString)
        logger.d(
            LOG_TAG,
            "init::audioGuideSoundUri = $audioGuideSoundUri"
        )
        val audioFileMetadata = metadataRetrieveUseCase.execute(
            context,
            audioGuideSoundUri
        )
        //
        val duration: Long = when {
            audioFileMetadata.durationMs != -1L -> {
                audioFileMetadata.durationMs
            }

            presetInput.duration != -1L -> {
                presetInput.duration // => audio file duration could not be retrieved, but a duration was already saved (probably by the user) => we will treat this duration as if it came from the file metadata
            }

            else -> {
                -1L // => audio file duration could not be retrieved => we will need the user to input it manually
            }
        }
        //
        _sessionPresetUpdatedLiveData.value = SessionPreset.AudioSessionPreset(
            id = presetInput.id,
            startCountdownLength = presetInput.startCountdownLength,
            startAndEndSoundUriString = presetInput.startAndEndSoundUriString,
            startAndEndSoundName = presetInput.startAndEndSoundName,
            duration = duration,
            audioGuideSoundUriString = presetInput.audioGuideSoundUriString,
            audioGuideSoundArtistName = audioFileMetadata.artistName,
            audioGuideSoundAlbumName = audioFileMetadata.albumName,
            audioGuideSoundTitle = audioFileMetadata.fileName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
    }

    override fun isSessionPresetValid(): Boolean {
        val preset = (_sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>).value
        return (preset != null &&
            preset.audioGuideSoundUriString.isNotBlank() &&
            preset.duration != -1L
            )
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
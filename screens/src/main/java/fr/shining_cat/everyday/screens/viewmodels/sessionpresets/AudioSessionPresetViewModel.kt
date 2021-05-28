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
import androidx.lifecycle.LiveData
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

    private val _validAudioGuideLiveData = MutableLiveData<Boolean>()
    val invalidAudioGuideLiveData: LiveData<Boolean> = _validAudioGuideLiveData

    private val _validDurationLiveData = MutableLiveData<Boolean>()
    val invalidDurationLiveData: LiveData<Boolean> = _validDurationLiveData

    override fun init(
        context: Context,
        presetInput: SessionPreset?,
        deviceDefaultRingtoneUriString: String,
        deviceDefaultRingtoneName: String
    ) {
        if (presetInput != null) {
            initForEdition(
                context,
                presetInput,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
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
        presetInput: SessionPreset,
        deviceDefaultRingtoneUriString: String,
        deviceDefaultRingtoneName: String
    ) {
        val audioGuideSoundUriString = presetInput.audioGuideSoundUriString // should never be blank at this stage
        if (audioGuideSoundUriString.isNotBlank()) {
            val audioGuideSoundUri = Uri.parse(presetInput.audioGuideSoundUriString)
            val audioFileMetadata = metadataRetrieveUseCase.execute(
                context,
                audioGuideSoundUri
            )
            //if one of these field is empty, try to retrieve it from the file metadata
            val audioGuideSoundArtistName =
                if (presetInput.audioGuideSoundArtistName.isBlank()) audioFileMetadata.artistName else presetInput.audioGuideSoundArtistName
            val audioGuideSoundAlbumName =
                if (presetInput.audioGuideSoundAlbumName.isBlank()) audioFileMetadata.albumName else presetInput.audioGuideSoundAlbumName
            val audioGuideSoundTitle =
                if (presetInput.audioGuideSoundTitle.isBlank()) audioFileMetadata.fileName else presetInput.audioGuideSoundTitle
            val duration = if (presetInput.duration == -1L) audioFileMetadata.durationMs else presetInput.duration
            //
            _sessionPresetUpdatedLiveData.value = (presetInput as SessionPreset.AudioSessionPreset).copy(
                duration = duration,
                audioGuideSoundUriString = audioGuideSoundUriString,
                audioGuideSoundArtistName = audioGuideSoundArtistName,
                audioGuideSoundAlbumName = audioGuideSoundAlbumName,
                audioGuideSoundTitle = audioGuideSoundTitle
            )
        }
        else {
            logger.e(
                LOG_TAG,
                "initForEdition::should never get called with an empty audioGuideSoundUriString => creating NEW sessionPreset instead"
            )
            initForCreation(
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
    }

    override fun isSessionPresetValid(): Boolean {
        val preset = (_sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>).value
        when {
            preset == null -> {
                logger.e(
                    LOG_TAG,
                    "isSessionPresetValid::preset should not be null at this point"
                )
            }

            preset.audioGuideSoundUriString.isBlank() -> {
                _validAudioGuideLiveData.value = false
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

    fun updatePresetAudioGuideSoundUriString(
        context: Context,
        inputAudioGuideSoundUriString: String
    ) {
        _validAudioGuideLiveData.value = inputAudioGuideSoundUriString.isNotBlank()
        val audioFileMetadata = if (inputAudioGuideSoundUriString.isNotBlank()) {
            val audioGuideSoundUri = Uri.parse(inputAudioGuideSoundUriString)
            metadataRetrieveUseCase.execute(
                context,
                audioGuideSoundUri
            )
        }
        else null
        //
        _sessionPresetUpdatedLiveData.value = (_sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>).value?.copy(
            audioGuideSoundUriString = inputAudioGuideSoundUriString,
            audioGuideSoundArtistName = audioFileMetadata?.artistName ?: "",
            audioGuideSoundAlbumName = audioFileMetadata?.albumName ?: "",
            audioGuideSoundTitle = audioFileMetadata?.fileName ?: "",
            duration = audioFileMetadata?.durationMs ?: -1L
        )
    }

    override fun updatePresetDuration(inputDuration: Long) {
        _validDurationLiveData.value = inputDuration > 0L
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
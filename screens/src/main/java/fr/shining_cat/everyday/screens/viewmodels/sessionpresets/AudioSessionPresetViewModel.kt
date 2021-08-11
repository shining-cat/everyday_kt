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
import fr.shining_cat.everyday.domain.FileMetadataRetrieveUseCase
import fr.shining_cat.everyday.domain.sessionspresets.CreateSessionPresetUseCase
import fr.shining_cat.everyday.domain.sessionspresets.DeleteSessionPresetUseCase
import fr.shining_cat.everyday.domain.sessionspresets.UpdateSessionPresetUseCase
import fr.shining_cat.everyday.models.SessionPreset

class AudioSessionPresetViewModel(
    createSessionPresetUseCase: CreateSessionPresetUseCase,
    updateSessionPresetUseCase: UpdateSessionPresetUseCase,
    deleteSessionPresetUseCase: DeleteSessionPresetUseCase,
    private val metadataRetrieveUseCase: FileMetadataRetrieveUseCase,
    private val logger: Logger
) : AbstractSessionPresetViewModel(
    createSessionPresetUseCase,
    updateSessionPresetUseCase,
    deleteSessionPresetUseCase,
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
        if (audioGuideSoundUriString.isNotBlank()) {
            val audioGuideSoundUri = Uri.parse(presetInput.audioGuideSoundUriString)
            val audioFileMetadata = metadataRetrieveUseCase.execute(
                context,
                audioGuideSoundUri
            )
            // if one of these field is empty, try to retrieve it from the file metadata
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
                "initForEdition::should never get called with an empty audioGuideSoundUriString => keeping the same ID but resetting info related to audio, since preset seems corrupted"
            )
            _sessionPresetUpdatedLiveData.value = (presetInput as SessionPreset.AudioSessionPreset).copy(
                duration = -1L,
                audioGuideSoundUriString = "",
                audioGuideSoundArtistName = "",
                audioGuideSoundAlbumName = "",
                audioGuideSoundTitle = ""
            )
        }
    }

    override fun verifyPresetValidity(): Boolean {
        val preset = (_sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>).value
        return when {
            preset == null -> {
                logger.e(
                    LOG_TAG,
                    "isSessionPresetValid::preset should not be null at this point"
                )
                false
            }

            preset.audioGuideSoundUriString.isBlank() -> {
                _validAudioGuideLiveData.value = false
                false
            }

            preset.duration <= 0L -> {
                _validDurationLiveData.value = false
                false
            }

            else -> {
                true
            }
        }
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
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(
            audioGuideSoundUriString = inputAudioGuideSoundUriString,
            audioGuideSoundArtistName = audioFileMetadata?.artistName ?: "",
            audioGuideSoundAlbumName = audioFileMetadata?.albumName ?: "",
            audioGuideSoundTitle = audioFileMetadata?.fileName ?: "",
            duration = audioFileMetadata?.durationMs ?: -1L
        )
    }

    override fun updatePresetDuration(inputDuration: Long) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>
        _validDurationLiveData.value = inputDuration > 0L
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(duration = inputDuration)
    }

    override fun updatePresetStartAndEndSoundUriString(inputStartAndEndSoundUriString: String) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(startAndEndSoundUriString = inputStartAndEndSoundUriString)
    }

    override fun updatePresetStartAndEndSoundName(inputStartAndEndSoundName: String) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(startAndEndSoundName = inputStartAndEndSoundName)
    }

    override fun updatePresetStartCountdownLength(inputStartCountdownLength: Long) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(startCountdownLength = inputStartCountdownLength)
    }

    override fun updatePresetVibration(inputVibration: Boolean) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(vibration = inputVibration)
    }

    override fun updatePresetSessionTypeId(inputSessionTypeId: Long) {
        val tempSessionPresetUpdatedLiveData = _sessionPresetUpdatedLiveData as MutableLiveData<SessionPreset.AudioSessionPreset>
        _sessionPresetUpdatedLiveData.value = tempSessionPresetUpdatedLiveData.value?.copy(sessionTypeId = inputSessionTypeId)
    }

    override fun updatePresetIntermediateIntervalRandom(inputIntermediateIntervalRandom: Boolean) {}
    override fun updatePresetIntermediateIntervalLength(inputIntermediateIntervalLength: Long) {}
    override fun updatePresetIntermediateIntervalSoundUriString(inputIntermediateIntervalSoundUriString: String) {}
    override fun updatePresetIntermediateIntervalSoundName(inputIntermediateIntervalSoundName: String) {}
}
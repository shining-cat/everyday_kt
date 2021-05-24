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

abstract class AbstractSessionPresetViewModel(
    appDispatchers: AppDispatchers,
    private val logger: Logger
) : AbstractViewModels(appDispatchers) {

    private val LOG_TAG = AbstractSessionPresetViewModel::class.java.name

    protected val _sessionPresetUpdatedLiveData = MutableLiveData<SessionPreset>()
    val sessionPresetUpdatedLiveData: LiveData<SessionPreset> = _sessionPresetUpdatedLiveData //getChildSessionPresetUpdatedLiveData()
//    abstract fun getChildSessionPresetUpdatedLiveData(): LiveData<SessionPreset>

    abstract fun isSessionPresetValid(): Boolean

    open fun init(
        context: Context,
        presetInput: SessionPreset?,
        deviceDefaultRingtoneUriString: String,
        deviceDefaultRingtoneName: String
    ) {
         val duration = presetInput?.duration ?: Constants.DEFAULT_SESSION_DURATION_MILLIS
        //
//        _sessionPresetUpdatedLiveData.value = SessionPreset(
//            id = presetInput?.id ?: -1L,
//            startCountdownLength = presetInput?.startCountdownLength ?: Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
//            startAndEndSoundUriString = presetInput?.startAndEndSoundUriString ?: deviceDefaultRingtoneUriString,
//            startAndEndSoundName = presetInput?.startAndEndSoundName ?: deviceDefaultRingtoneName,
//            intermediateIntervalLength = if (isAnAudioSession) {
//                INTERVAL_LENGTH_IS_NONE_FOR_AUDIO_SESSION // audio session  => no intermediate interval
//            }
//            else {
//                presetInput?.intermediateIntervalLength ?: 0L
//            },
//            intermediateIntervalRandom = if (isAnAudioSession) {
//                false // audio session  => no intermediate interval
//            }
//            else {
//                presetInput?.intermediateIntervalRandom ?: false
//            },
//            intermediateIntervalSoundUriString = if (isAnAudioSession) {
//                "" // audio session  => no intermediate interval "" is equivalent for "silence" here
//            }
//            else {
//                presetInput?.intermediateIntervalSoundUriString ?: deviceDefaultRingtoneUriString
//            },
//            intermediateIntervalSoundName = if (isAnAudioSession) {
//                "" // audio session  => no intermediate interval "" is not equivalent for "silence" here
//            }
//            else {
//                presetInput?.intermediateIntervalSoundName ?: deviceDefaultRingtoneName
//            },
//            duration = duration,
//            audioGuideSoundUriString = presetInput?.audioGuideSoundUriString ?: "",
//            audioGuideSoundArtistName = audioFileMetadata?.artistName ?: "",
//            audioGuideSoundAlbumName = audioFileMetadata?.albumName ?: "",
//            audioGuideSoundTitle = audioFileMetadata?.fileName ?: "",
//            vibration = false,
//            sessionTypeId = -1L,
//            lastEditTime = -1L,
//        )
    }

    abstract fun updatePresetDuration(inputDuration: Long)/* {
        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(duration = inputDuration)
    }*/

    abstract fun updatePresetStartAndEndSoundUriString(inputStartAndEndSoundUriString: String) /*{
//        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(startAndEndSoundUriString = inputStartAndEndSoundUriString)
    }*/

    abstract fun updatePresetStartAndEndSoundName(inputStartAndEndSoundName: String)/* {
//        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(startAndEndSoundName = inputStartAndEndSoundName)
    }*/

    abstract fun updatePresetIntermediateIntervalLength(inputIntermediateIntervalLength: Long) /*{
//        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(intermediateIntervalLength = inputIntermediateIntervalLength)
    }*/

    abstract fun updatePresetStartCountdownLength(inputStartCountdownLength: Long)/* {
//        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(startCountdownLength = inputStartCountdownLength)
    }*/

    abstract fun updatePresetIntermediateIntervalRandom(inputIntermediateIntervalRandom: Boolean)/* {
//        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(intermediateIntervalRandom = inputIntermediateIntervalRandom)
    }
*/
    abstract fun updatePresetIntermediateIntervalSoundUriString(inputIntermediateIntervalSoundUriString: String)/* {
//        _sessionPresetUpdatedLiveData.value =
//            _sessionPresetUpdatedLiveData.value?.copy(intermediateIntervalSoundUriString = inputIntermediateIntervalSoundUriString)
    }*/

    abstract fun updatePresetIntermediateIntervalSoundName(inputIntermediateIntervalSoundName: String)/* {
//        _sessionPresetUpdatedLiveData.value =
//            _sessionPresetUpdatedLiveData.value?.copy(intermediateIntervalSoundName = inputIntermediateIntervalSoundName)
    }*/

    abstract fun updatePresetVibration(inputVibration: Boolean) /*{
//        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(vibration = inputVibration)
    }*/

    abstract fun updatePresetSessionTypeId(inputSessionTypeId: Int) /*{
//        _sessionPresetUpdatedLiveData.value = _sessionPresetUpdatedLiveData.value?.copy(sessionTypeId = inputSessionTypeId)
    }*/
}
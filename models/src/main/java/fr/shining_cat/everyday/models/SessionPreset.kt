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

package fr.shining_cat.everyday.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class SessionPreset(
    open val id: Long,
    open val startCountdownLength: Long,
    open val startAndEndSoundUriString: String,
    open val startAndEndSoundName: String,
    open val intermediateIntervalLength: Long,
    open val intermediateIntervalRandom: Boolean,
    open val intermediateIntervalSoundUriString: String,
    open val intermediateIntervalSoundName: String,
    open val duration: Long,
    open val audioGuideSoundUriString: String,
    open val audioGuideSoundArtistName: String,
    open val audioGuideSoundAlbumName: String,
    open val audioGuideSoundTitle: String,
    open val vibration: Boolean,
    open val sessionTypeId: Int,
    open val lastEditTime: Long
) : Parcelable {

    @Parcelize
    data class AudioSessionPreset(
        override val id: Long,
        override val startCountdownLength: Long,
        override val startAndEndSoundUriString: String,
        override val startAndEndSoundName: String,
        override val duration: Long,
        override val audioGuideSoundUriString: String,
        override val audioGuideSoundArtistName: String,
        override val audioGuideSoundAlbumName: String,
        override val audioGuideSoundTitle: String,
        override val vibration: Boolean,
        override val sessionTypeId: Int,
        override val lastEditTime: Long
    ) : SessionPreset(
        id = id,
        startCountdownLength = startCountdownLength,
        startAndEndSoundUriString = startAndEndSoundUriString,
        startAndEndSoundName = startAndEndSoundName,
        intermediateIntervalLength = -1L,
        intermediateIntervalRandom = false,
        intermediateIntervalSoundUriString = "",
        intermediateIntervalSoundName = "",
        duration = duration,
        audioGuideSoundUriString = audioGuideSoundUriString,
        audioGuideSoundArtistName = audioGuideSoundArtistName,
        audioGuideSoundAlbumName = audioGuideSoundAlbumName,
        audioGuideSoundTitle = audioGuideSoundTitle,
        vibration = vibration,
        sessionTypeId = sessionTypeId,
        lastEditTime = lastEditTime
    )

    @Parcelize
    data class AudioFreeSessionPreset(
        override val id: Long,
        override val startCountdownLength: Long,
        override val startAndEndSoundUriString: String,
        override val startAndEndSoundName: String,
        override val duration: Long,
        override val vibration: Boolean,
        override val sessionTypeId: Int,
        override val lastEditTime: Long
    ) : SessionPreset(
        id = id,
        startCountdownLength = startCountdownLength,
        startAndEndSoundUriString = startAndEndSoundUriString,
        startAndEndSoundName = startAndEndSoundName,
        intermediateIntervalLength = -1L,
        intermediateIntervalRandom = false,
        intermediateIntervalSoundUriString = "",
        intermediateIntervalSoundName = "",
        duration = duration,
        audioGuideSoundUriString = "",
        audioGuideSoundArtistName = "",
        audioGuideSoundAlbumName = "",
        audioGuideSoundTitle = "",
        vibration = vibration,
        sessionTypeId = sessionTypeId,
        lastEditTime = lastEditTime
    )

    @Parcelize
    data class TimedSessionPreset(
        override val id: Long,
        override val startCountdownLength: Long,
        override val startAndEndSoundUriString: String,
        override val startAndEndSoundName: String,
        override val intermediateIntervalLength: Long,
        override val intermediateIntervalRandom: Boolean,
        override val intermediateIntervalSoundUriString: String,
        override val intermediateIntervalSoundName: String,
        override val duration: Long,
        override val vibration: Boolean,
        override val sessionTypeId: Int,
        override val lastEditTime: Long
    ) : SessionPreset(
        id = id,
        startCountdownLength = startCountdownLength,
        startAndEndSoundUriString = startAndEndSoundUriString,
        startAndEndSoundName = startAndEndSoundName,
        intermediateIntervalLength = intermediateIntervalLength,
        intermediateIntervalRandom = intermediateIntervalRandom,
        intermediateIntervalSoundUriString = intermediateIntervalSoundUriString,
        intermediateIntervalSoundName = intermediateIntervalSoundName,
        duration = duration,
        audioGuideSoundUriString = "",
        audioGuideSoundArtistName = "",
        audioGuideSoundAlbumName = "",
        audioGuideSoundTitle = "",
        vibration = vibration,
        sessionTypeId = sessionTypeId,
        lastEditTime = lastEditTime
    )

    @Parcelize
    data class TimedFreeSessionPreset(
        override val id: Long,
        override val startCountdownLength: Long,
        override val startAndEndSoundUriString: String,
        override val startAndEndSoundName: String,
        override val intermediateIntervalLength: Long,
        override val intermediateIntervalRandom: Boolean,
        override val intermediateIntervalSoundUriString: String,
        override val intermediateIntervalSoundName: String,
        override val vibration: Boolean,
        override val sessionTypeId: Int,
        override val lastEditTime: Long
    ) : SessionPreset(
        id = id,
        startCountdownLength = startCountdownLength,
        startAndEndSoundUriString = startAndEndSoundUriString,
        startAndEndSoundName = startAndEndSoundName,
        intermediateIntervalLength = intermediateIntervalLength,
        intermediateIntervalRandom = intermediateIntervalRandom,
        intermediateIntervalSoundUriString = intermediateIntervalSoundUriString,
        intermediateIntervalSoundName = intermediateIntervalSoundName,
        duration = -1L,
        audioGuideSoundUriString = "",
        audioGuideSoundArtistName = "",
        audioGuideSoundAlbumName = "",
        audioGuideSoundTitle = "",
        vibration = vibration,
        sessionTypeId = sessionTypeId,
        lastEditTime = lastEditTime
    )

    @Parcelize
    class UnknownSessionPreset : SessionPreset(
        id = -1L,
        startCountdownLength = -1L,
        startAndEndSoundUriString = "",
        startAndEndSoundName = "",
        intermediateIntervalLength = -1L,
        intermediateIntervalRandom = false,
        intermediateIntervalSoundUriString = "",
        intermediateIntervalSoundName = "",
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
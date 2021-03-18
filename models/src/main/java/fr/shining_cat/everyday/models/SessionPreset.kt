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

@Parcelize
data class SessionPreset(
    val id: Long,
    val startCountdownLength: Long,
    val startAndEndSoundUriString: String,
    val intermediateIntervalLength: Long,
    val intermediateIntervalRandom: Boolean,
    val intermediateIntervalSoundUriString: String,
    val duration: Long,
    val audioGuideSoundUriString: String,
    val audioGuideSoundArtistName: String,
    val audioGuideSoundAlbumName: String,
    val audioGuideSoundFileName: String,
    val vibration: Boolean,
    val sessionTypeId: Long,
    val lastEditTime: Long
) : Parcelable
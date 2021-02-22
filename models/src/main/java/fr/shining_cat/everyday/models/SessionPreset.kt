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

data class SessionPreset(
    var id: Long,
    var duration: Long,
    var startAndEndSoundUri: String,
    var intermediateIntervalLength: Long,
    var startCountdownLength: Long,
    var intermediateIntervalRandom: Boolean,
    var intermediateIntervalSoundUri: String,
    var audioGuideSoundUri: String,
    var vibration: Boolean,
    var lastEditTime: Long,
    var sessionTypeId: Long
)
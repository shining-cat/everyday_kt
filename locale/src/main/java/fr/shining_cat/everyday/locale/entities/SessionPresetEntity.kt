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

package fr.shining_cat.everyday.locale.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.AUDIO_GUIDE
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.AUDIO_GUIDE_ALBUM
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.AUDIO_GUIDE_ARTIST
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.AUDIO_GUIDE_TITLE
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.DURATION
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.INTERMEDIATE_INTERVAL_LENGTH
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.INTERMEDIATE_INTERVAL_RANDOM
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.INTERMEDIATE_SOUND_NAME
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.INTERMEDIATE_SOUND_URI
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.LAST_EDIT_DATE
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.SESSION_PRESET_ID
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.SESSION_PRESET_TYPE
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.START_AND_END_SOUND_NAME
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.START_AND_END_SOUND_URI
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.START_COUNTDOWN_LENGTH
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.SESSION_TYPE
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.VIBRATION
import fr.shining_cat.everyday.locale.entities.SessionPresetTable.SESSION_PRESET_TABLE_NAME

@Entity(tableName = SESSION_PRESET_TABLE_NAME)
data class SessionPresetEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = SESSION_PRESET_ID) val id: Long? = null,
    //
    @ColumnInfo(name = START_COUNTDOWN_LENGTH) val startCountdownLength: Long,
    @ColumnInfo(name = START_AND_END_SOUND_URI) val startAndEndSoundUri: String,
    @ColumnInfo(name = START_AND_END_SOUND_NAME) val startAndEndSoundName: String,
    @ColumnInfo(name = INTERMEDIATE_INTERVAL_LENGTH) val intermediateIntervalLength: Long,
    @ColumnInfo(name = INTERMEDIATE_INTERVAL_RANDOM) val intermediateIntervalRandom: Boolean,
    @ColumnInfo(name = INTERMEDIATE_SOUND_URI) val intermediateIntervalSoundUri: String,
    @ColumnInfo(name = INTERMEDIATE_SOUND_NAME) val intermediateIntervalSoundName: String,
    @ColumnInfo(name = DURATION) val duration: Long,
    @ColumnInfo(name = AUDIO_GUIDE) val audioGuideSoundUri: String,
    @ColumnInfo(name = AUDIO_GUIDE_ARTIST) val audioGuideSoundArtistName: String,
    @ColumnInfo(name = AUDIO_GUIDE_ALBUM) val audioGuideSoundAlbumName: String,
    @ColumnInfo(name = AUDIO_GUIDE_TITLE) val audioGuideSoundTitle: String,
    @ColumnInfo(name = VIBRATION) val vibration: Boolean,
    @ColumnInfo(name = SESSION_TYPE) val sessionTypeId: Int,
    @ColumnInfo(name = LAST_EDIT_DATE) val lastEditTime: Long,
    @ColumnInfo(name = SESSION_PRESET_TYPE) val sessionPresetType: String
)

object SessionPresetTable {

    const val SESSION_PRESET_TABLE_NAME = "sessions_presets"
}

object SessionPresetEntityColumnNames {

    const val SESSION_PRESET_ID = "sessionPresetId"
    const val START_COUNTDOWN_LENGTH = "startCountdownLength"
    const val START_AND_END_SOUND_URI = "startAndEndSoundUri"
    const val START_AND_END_SOUND_NAME = "startAndEndSoundName"
    const val INTERMEDIATE_INTERVAL_LENGTH = "intermediateIntervalLength"
    const val INTERMEDIATE_INTERVAL_RANDOM = "intermediateIntervalRandom"
    const val INTERMEDIATE_SOUND_URI = "intermediateIntervalSoundUri"
    const val INTERMEDIATE_SOUND_NAME = "intermediateIntervalSoundName"
    const val DURATION = "duration"
    const val AUDIO_GUIDE = "audioGuide"
    const val AUDIO_GUIDE_ARTIST = "audioGuideArtist"
    const val AUDIO_GUIDE_ALBUM = "audioGuideAlbum"
    const val AUDIO_GUIDE_TITLE = "audioGuideTitle"
    const val VIBRATION = "vibration"
    const val LAST_EDIT_DATE = "lastEditDate"
    const val SESSION_TYPE = "sessionType"
    const val SESSION_PRESET_TYPE = "sessionPresetType"
}
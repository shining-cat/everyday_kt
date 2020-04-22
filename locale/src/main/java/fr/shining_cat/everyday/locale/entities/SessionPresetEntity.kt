package fr.shining_cat.everyday.locale.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.AUDIO_GUIDE
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.DURATION
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.INTERMEDIATE_INTERVAL_LENGTH
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.INTERMEDIATE_INTERVAL_RANDOM
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.INTERMEDIATE_SOUND
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.LAST_EDIT_DATE
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.SESSION_PRESET_ID
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.START_AND_END_SOUND
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.START_COUNTDOWN_LENGTH
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.VIBRATION
import fr.shining_cat.everyday.locale.entities.SessionPresetTable.SESSION_PRESET_TABLE_NAME

@Entity(tableName = SESSION_PRESET_TABLE_NAME)
data class SessionPresetEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = SESSION_PRESET_ID)
    var id: Long = 0L,
    //
    @ColumnInfo(name = DURATION)
    var duration: Long,
    @ColumnInfo(name = START_AND_END_SOUND)
    var startAndEndSoundUri: String,
    @ColumnInfo(name = INTERMEDIATE_INTERVAL_LENGTH)
    var intermediateIntervalLength: Long,
    @ColumnInfo(name = START_COUNTDOWN_LENGTH)
    var startCountdownLength: Long,
    @ColumnInfo(name = INTERMEDIATE_INTERVAL_RANDOM)
    var intermediateIntervalRandom: Boolean,
    @ColumnInfo(name = INTERMEDIATE_SOUND)
    var intermediateIntervalSoundUri: String,
    @ColumnInfo(name = AUDIO_GUIDE)
    var audioGuideSoundUri: String,
    @ColumnInfo(name = VIBRATION)
    var vibration: Boolean,
    @ColumnInfo(name = LAST_EDIT_DATE)
    var lastEditTime: Long
)

object SessionPresetTable {
    const val SESSION_PRESET_TABLE_NAME = "sessions_presets"
}

object SessionPresetEntityColumnNames {
    const val SESSION_PRESET_ID = "sessionPresetId"
    const val START_COUNTDOWN_LENGTH = "startCountdownLength"
    const val DURATION = "duration"
    const val START_AND_END_SOUND = "startAndEndSound"
    const val INTERMEDIATE_INTERVAL_LENGTH = "intermediateIntervalLength"
    const val INTERMEDIATE_INTERVAL_RANDOM = "intermediateIntervalRandom"
    const val INTERMEDIATE_SOUND = "intermediateIntervalSound"
    const val AUDIO_GUIDE = "audioGuide"
    const val VIBRATION = "vibration"
    const val LAST_EDIT_DATE = "lastEditDate"
}
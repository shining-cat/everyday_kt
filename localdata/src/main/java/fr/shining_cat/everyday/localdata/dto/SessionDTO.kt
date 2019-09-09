package fr.shining_cat.everyday.localdata.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions_table")
data class SessionDTO(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    //
    @ColumnInfo(name = SessionColumnNames.START_TIME_OF_RECORD_COLUMN_NAME)
    var startTimeOfRecord: Long,
    @ColumnInfo(name = SessionColumnNames.START_BODY_VALUE_COLUMN_NAME)
    var startBodyValue: Int,
    @ColumnInfo(name = SessionColumnNames.START_THOUGHTS_VALUE_COLUMN_NAME)
    var startThoughtsValue: Int,
    @ColumnInfo(name = SessionColumnNames.START_FEELINGS_VALUE_COLUMN_NAME)
    var startFeelingsValue: Int,
    @ColumnInfo(name = SessionColumnNames.START_GLOBAL_VALUE_COLUMN_NAME)
    var startGlobalValue: Int,
    //
    @ColumnInfo(name = SessionColumnNames.END_TIME_OF_RECORD_COLUMN_NAME)
    var endTimeOfRecord: Long,
    @ColumnInfo(name = SessionColumnNames.END_BODY_VALUE_COLUMN_NAME)
    var endBodyValue: Int,
    @ColumnInfo(name = SessionColumnNames.END_THOUGHTS_VALUE_COLUMN_NAME)
    var endThoughtsValue: Int,
    @ColumnInfo(name = SessionColumnNames.END_FEELINGS_VALUE_COLUMN_NAME)
    var endFeelingsValue: Int,
    @ColumnInfo(name = SessionColumnNames.END_GLOBAL_VALUE_COLUMN_NAME)
    var endGlobalValue: Int,
    //
    @ColumnInfo(name = SessionColumnNames.NOTES_COLUMN_NAME)
    var notes: String,
    @ColumnInfo(name = SessionColumnNames.SESSION_REAL_DURATION_COLUMN_NAME)
    var realDuration: Long,
    @ColumnInfo(name = SessionColumnNames.PAUSES_COUNT_COLUMN_NAME)
    var pausesCount: Int,
    @ColumnInfo(name = SessionColumnNames.REAL_DURATION_VS_PLANNED_COLUMN_NAME)
    var realDurationVsPlanned: Int, //<0 if real < planned; =0 if real = planned; >0 if real > planned  (obtained via Long.compare(real, planned)
    @ColumnInfo(name = SessionColumnNames.MP3_GUIDE_COLUMN_NAME)
    var guideMp3: String)

object SessionColumnNames{
    const val START_TIME_OF_RECORD_COLUMN_NAME = "startTimeOfRecord"
    const val START_BODY_VALUE_COLUMN_NAME = "startBodyValue"
    const val START_THOUGHTS_VALUE_COLUMN_NAME = "startThoughtsValue"
    const val START_FEELINGS_VALUE_COLUMN_NAME = "startFeelingsValue"
    const val START_GLOBAL_VALUE_COLUMN_NAME = "startGlobalValue"

    const val END_TIME_OF_RECORD_COLUMN_NAME = "endTimeOfRecord"
    const val END_BODY_VALUE_COLUMN_NAME = "endBodyValue"
    const val END_THOUGHTS_VALUE_COLUMN_NAME = "endThoughtsValue"
    const val END_FEELINGS_VALUE_COLUMN_NAME = "endFeelingsValue"
    const val END_GLOBAL_VALUE_COLUMN_NAME = "endGlobalValue"

    const val NOTES_COLUMN_NAME = "notes"
    const val SESSION_REAL_DURATION_COLUMN_NAME = "realDuration"
    const val PAUSES_COUNT_COLUMN_NAME = "pausesCount"
    const val REAL_DURATION_VS_PLANNED_COLUMN_NAME = "realDurationVsPlanned"
    const val MP3_GUIDE_COLUMN_NAME = "guideMp3"
}

fun getSessionRecordHeaders(): Array<String> {
    return arrayOf(
        SessionColumnNames.START_TIME_OF_RECORD_COLUMN_NAME,
        SessionColumnNames.END_TIME_OF_RECORD_COLUMN_NAME,
        SessionColumnNames.SESSION_REAL_DURATION_COLUMN_NAME,
        SessionColumnNames.NOTES_COLUMN_NAME,
        SessionColumnNames.START_BODY_VALUE_COLUMN_NAME,
        SessionColumnNames.START_THOUGHTS_VALUE_COLUMN_NAME,
        SessionColumnNames.START_FEELINGS_VALUE_COLUMN_NAME,
        SessionColumnNames.START_GLOBAL_VALUE_COLUMN_NAME,
        SessionColumnNames.END_BODY_VALUE_COLUMN_NAME,
        SessionColumnNames.END_THOUGHTS_VALUE_COLUMN_NAME,
        SessionColumnNames.END_FEELINGS_VALUE_COLUMN_NAME,
        SessionColumnNames.END_GLOBAL_VALUE_COLUMN_NAME,
        SessionColumnNames.PAUSES_COUNT_COLUMN_NAME,
        SessionColumnNames.REAL_DURATION_VS_PLANNED_COLUMN_NAME,
        SessionColumnNames.MP3_GUIDE_COLUMN_NAME
    )
}
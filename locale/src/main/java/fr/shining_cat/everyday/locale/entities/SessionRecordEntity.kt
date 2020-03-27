package fr.shining_cat.everyday.locale.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions_table")
data class SessionRecordEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    //
    @ColumnInfo(name = SessionEntityColumnNames.START_TIME_OF_RECORD_COLUMN_NAME)
    var startTimeOfRecord: Long,
    @ColumnInfo(name = SessionEntityColumnNames.START_BODY_VALUE_COLUMN_NAME)
    var startBodyValue: Int,
    @ColumnInfo(name = SessionEntityColumnNames.START_THOUGHTS_VALUE_COLUMN_NAME)
    var startThoughtsValue: Int,
    @ColumnInfo(name = SessionEntityColumnNames.START_FEELINGS_VALUE_COLUMN_NAME)
    var startFeelingsValue: Int,
    @ColumnInfo(name = SessionEntityColumnNames.START_GLOBAL_VALUE_COLUMN_NAME)
    var startGlobalValue: Int,
    //
    @ColumnInfo(name = SessionEntityColumnNames.END_TIME_OF_RECORD_COLUMN_NAME)
    var endTimeOfRecord: Long,
    @ColumnInfo(name = SessionEntityColumnNames.END_BODY_VALUE_COLUMN_NAME)
    var endBodyValue: Int,
    @ColumnInfo(name = SessionEntityColumnNames.END_THOUGHTS_VALUE_COLUMN_NAME)
    var endThoughtsValue: Int,
    @ColumnInfo(name = SessionEntityColumnNames.END_FEELINGS_VALUE_COLUMN_NAME)
    var endFeelingsValue: Int,
    @ColumnInfo(name = SessionEntityColumnNames.END_GLOBAL_VALUE_COLUMN_NAME)
    var endGlobalValue: Int,
    //
    @ColumnInfo(name = SessionEntityColumnNames.NOTES_COLUMN_NAME)
    var notes: String,
    @ColumnInfo(name = SessionEntityColumnNames.SESSION_REAL_DURATION_COLUMN_NAME)
    var realDuration: Long,
    @ColumnInfo(name = SessionEntityColumnNames.PAUSES_COUNT_COLUMN_NAME)
    var pausesCount: Int,
    @ColumnInfo(name = SessionEntityColumnNames.REAL_DURATION_VS_PLANNED_COLUMN_NAME)
    var realDurationVsPlanned: Int, //<0 if real < planned; =0 if real = planned; >0 if real > planned  (obtained via Long.compare(real, planned)
    @ColumnInfo(name = SessionEntityColumnNames.MP3_GUIDE_COLUMN_NAME)
    var guideMp3: String)

object SessionEntityColumnNames{
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
        SessionEntityColumnNames.START_TIME_OF_RECORD_COLUMN_NAME,
        SessionEntityColumnNames.END_TIME_OF_RECORD_COLUMN_NAME,
        SessionEntityColumnNames.SESSION_REAL_DURATION_COLUMN_NAME,
        SessionEntityColumnNames.NOTES_COLUMN_NAME,
        SessionEntityColumnNames.START_BODY_VALUE_COLUMN_NAME,
        SessionEntityColumnNames.START_THOUGHTS_VALUE_COLUMN_NAME,
        SessionEntityColumnNames.START_FEELINGS_VALUE_COLUMN_NAME,
        SessionEntityColumnNames.START_GLOBAL_VALUE_COLUMN_NAME,
        SessionEntityColumnNames.END_BODY_VALUE_COLUMN_NAME,
        SessionEntityColumnNames.END_THOUGHTS_VALUE_COLUMN_NAME,
        SessionEntityColumnNames.END_FEELINGS_VALUE_COLUMN_NAME,
        SessionEntityColumnNames.END_GLOBAL_VALUE_COLUMN_NAME,
        SessionEntityColumnNames.PAUSES_COUNT_COLUMN_NAME,
        SessionEntityColumnNames.REAL_DURATION_VS_PLANNED_COLUMN_NAME,
        SessionEntityColumnNames.MP3_GUIDE_COLUMN_NAME
    )
}
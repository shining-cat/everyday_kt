package fr.shining_cat.everyday.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import java.text.SimpleDateFormat
import java.util.*


@Entity(tableName = "sessions_table")
data class SessionRecord(
    @PrimaryKey(autoGenerate = true)
    private var id: Long = 0L,
    @ColumnInfo(name = SessionColumnNames.START_TIME_OF_RECORD_COLUMN_NAME)
    private var startTimeOfRecord: Long,
    @ColumnInfo(name = SessionColumnNames.START_BODY_VALUE_COLUMN_NAME)
    private var startBodyValue: Int,
    @ColumnInfo(name = SessionColumnNames.START_THOUGHTS_VALUE_COLUMN_NAME)
    private var startThoughtsValue: Int,
    @ColumnInfo(name = SessionColumnNames.START_FEELINGS_VALUE_COLUMN_NAME)
    private var startFeelingsValue: Int,
    @ColumnInfo(name = SessionColumnNames.START_GLOBAL_VALUE_COLUMN_NAME)
    private var startGlobalValue: Int,
    //
    @ColumnInfo(name = SessionColumnNames.END_TIME_OF_RECORD_COLUMN_NAME)
    private var endTimeOfRecord: Long,
    @ColumnInfo(name = SessionColumnNames.END_BODY_VALUE_COLUMN_NAME)
    private var endBodyValue: Int,
    @ColumnInfo(name = SessionColumnNames.END_THOUGHTS_VALUE_COLUMN_NAME)
    private var endThoughtsValue: Int,
    @ColumnInfo(name = SessionColumnNames.END_FEELINGS_VALUE_COLUMN_NAME)
    private var endFeelingsValue: Int,
    @ColumnInfo(name = SessionColumnNames.END_GLOBAL_VALUE_COLUMN_NAME)
    private var endGlobalValue: Int,
    //
    @ColumnInfo(name = SessionColumnNames.NOTES_COLUMN_NAME)
    private var notes: String,
    @ColumnInfo(name = SessionColumnNames.SESSION_REAL_DURATION_COLUMN_NAME)
    private var sessionRealDuration: Long,
    @ColumnInfo(name = SessionColumnNames.PAUSES_COUNT_COLUMN_NAME)
    private var pausesCount: Int,
    @ColumnInfo(name = SessionColumnNames.REAL_DURATION_VS_PLANNED_COLUMN_NAME)
    private var realDurationVsPlanned: Int, //<0 if real < planned; =0 if real = planned; >0 if real > planned  (obtained via Long.compare(real, planned)
    @ColumnInfo(name = SessionColumnNames.MP3_GUIDE_COLUMN_NAME)
    private var guideMp3: String){

    ////////////////////////////////////////
    //convenience getters
    fun getStartMood(): MoodRecord {
        return MoodRecord(
            startTimeOfRecord,
            startBodyValue,
            startThoughtsValue,
            startFeelingsValue,
            startGlobalValue
        )
    }
    //
    fun getEndMood(): MoodRecord {
        val endMood = MoodRecord(
            endTimeOfRecord,
            endBodyValue,
            endThoughtsValue,
            endFeelingsValue,
            endGlobalValue
        )
        endMood.pausesCount = pausesCount
        endMood.notes = notes
        endMood.realDurationVsPlanned = realDurationVsPlanned
        endMood.guideMp3 = guideMp3
        return endMood
    }
    //getSessionRecordHeaders, getSessionRecordArray
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
//
    fun getSessionRecordArray(): Array<String> {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        var realDurationVsPlannedString = "EQUAL"
        if (realDurationVsPlanned < 0) {
            realDurationVsPlannedString = "LESS"
        } else if (realDurationVsPlanned > 0) {
            realDurationVsPlannedString = "MORE"
        }
        //0 is for NOT SET so export it as such
        val startBodyValue = if (startBodyValue == 0) "NOT SET" else startBodyValue.toString()
        val startThoughtsValue = if (startThoughtsValue == 0) "NOT SET" else startThoughtsValue.toString()
        val startFeelingsValue = if (startFeelingsValue== 0) "NOT SET" else startFeelingsValue.toString()
        val startGlobalValue = if (startGlobalValue == 0) "NOT SET" else startGlobalValue.toString()
        val endBodyValue = if (endBodyValue == 0) "NOT SET" else endBodyValue.toString()
        val endThoughtsValue = if (endThoughtsValue == 0) "NOT SET" else endThoughtsValue.toString()
        val endFeelingsValue = if (endFeelingsValue == 0) "NOT SET" else endFeelingsValue.toString()
        val endGlobalValue = if (endGlobalValue == 0) "NOT SET" else endGlobalValue.toString()
        //
        return arrayOf(
            sdf.format(startTimeOfRecord),
            sdf.format(endTimeOfRecord),
            (sessionRealDuration / 60000).toString(), //duration is converted to minutes for csv export (same as import)
            notes,
            startBodyValue,
            startThoughtsValue,
            startFeelingsValue,
            startGlobalValue,
            endBodyValue,
            endThoughtsValue,
            endFeelingsValue,
            endGlobalValue,
            pausesCount.toString(),
            realDurationVsPlannedString,
            guideMp3
        )
    }
}

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
    const val SESSION_REAL_DURATION_COLUMN_NAME = "sessionRealDuration"
    const val PAUSES_COUNT_COLUMN_NAME = "pausesCount"
    const val REAL_DURATION_VS_PLANNED_COLUMN_NAME = "realDurationVsPlanned"
    const val MP3_GUIDE_COLUMN_NAME = "guideMp3"
}
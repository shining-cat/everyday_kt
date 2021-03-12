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
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.END_BODY_VALUE
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.END_FEELINGS_VALUE
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.END_GLOBAL_VALUE
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.END_THOUGHTS_VALUE
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.END_TIME_OF_RECORD
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.MP3_GUIDE
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.NOTES
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.PAUSES_COUNT
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.REAL_DURATION_VS_PLANNED
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.SESSION_REAL_DURATION
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.SESSION_RECORD_ID
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.START_BODY_VALUE
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.START_FEELINGS_VALUE
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.START_GLOBAL_VALUE
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.START_THOUGHTS_VALUE
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.START_TIME_OF_RECORD
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.TYPE
import fr.shining_cat.everyday.locale.entities.SessionRecordTable.SESSION_RECORD_TABLE

@Entity(tableName = SESSION_RECORD_TABLE)
data class SessionRecordEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = SESSION_RECORD_ID) val id: Long = 0L,
    //
    @ColumnInfo(name = START_TIME_OF_RECORD) val startTimeOfRecord: Long,
    @ColumnInfo(name = START_BODY_VALUE) val startBodyValue: Int,
    @ColumnInfo(name = START_THOUGHTS_VALUE) val startThoughtsValue: Int,
    @ColumnInfo(name = START_FEELINGS_VALUE) val startFeelingsValue: Int,
    @ColumnInfo(name = START_GLOBAL_VALUE) val startGlobalValue: Int,
    //
    @ColumnInfo(name = END_TIME_OF_RECORD) val endTimeOfRecord: Long,
    @ColumnInfo(name = END_BODY_VALUE) val endBodyValue: Int,
    @ColumnInfo(name = END_THOUGHTS_VALUE) val endThoughtsValue: Int,
    @ColumnInfo(name = END_FEELINGS_VALUE) val endFeelingsValue: Int,
    @ColumnInfo(name = END_GLOBAL_VALUE) val endGlobalValue: Int,
    //
    @ColumnInfo(name = NOTES) val notes: String,
    @ColumnInfo(name = SESSION_REAL_DURATION) val realDuration: Long,
    @ColumnInfo(name = PAUSES_COUNT) val pausesCount: Int,
    // realDurationVsPlanned <0 if real < planned; =0 if real = planned; >0 if real > planned  (obtained via Long.compare(real, planned)
    @ColumnInfo(name = REAL_DURATION_VS_PLANNED) val realDurationVsPlanned: Int,
    @ColumnInfo(name = MP3_GUIDE) val guideMp3: String,
    @ColumnInfo(name = TYPE) val sessionTypeId: Long
)

object SessionRecordTable {

    const val SESSION_RECORD_TABLE = "session_record_table"
}

object SessionRecordEntityColumnNames {

    const val SESSION_RECORD_ID = "sessionRecordId"
    const val START_TIME_OF_RECORD = "startTimeOfRecord"
    const val START_BODY_VALUE = "startBodyValue"
    const val START_THOUGHTS_VALUE = "startThoughtsValue"
    const val START_FEELINGS_VALUE = "startFeelingsValue"
    const val START_GLOBAL_VALUE = "startGlobalValue"

    const val END_TIME_OF_RECORD = "endTimeOfRecord"
    const val END_BODY_VALUE = "endBodyValue"
    const val END_THOUGHTS_VALUE = "endThoughtsValue"
    const val END_FEELINGS_VALUE = "endFeelingsValue"
    const val END_GLOBAL_VALUE = "endGlobalValue"

    const val NOTES = "notes"
    const val SESSION_REAL_DURATION = "realDuration"
    const val PAUSES_COUNT = "pausesCount"
    const val REAL_DURATION_VS_PLANNED = "realDurationVsPlanned"
    const val MP3_GUIDE = "guideMp3"
    const val TYPE = "type"
}

fun getSessionRecordHeaders(): Array<String> {
    return arrayOf(
        START_TIME_OF_RECORD,
        END_TIME_OF_RECORD,
        SESSION_REAL_DURATION,
        NOTES,
        START_BODY_VALUE,
        START_THOUGHTS_VALUE,
        START_FEELINGS_VALUE,
        START_GLOBAL_VALUE,
        END_BODY_VALUE,
        END_THOUGHTS_VALUE,
        END_FEELINGS_VALUE,
        END_GLOBAL_VALUE,
        PAUSES_COUNT,
        REAL_DURATION_VS_PLANNED,
        MP3_GUIDE
    )
}
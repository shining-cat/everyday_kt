package fr.shining_cat.everyday.locale.dao

import androidx.room.*
import fr.shining_cat.everyday.locale.entities.SessionRecordEntity
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.MP3_GUIDE
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.NOTES
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.SESSION_REAL_DURATION
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.SESSION_RECORD_ID
import fr.shining_cat.everyday.locale.entities.SessionRecordEntityColumnNames.START_TIME_OF_RECORD
import fr.shining_cat.everyday.locale.entities.SessionRecordTable.SESSION_RECORD_TABLE


@Dao
abstract class SessionRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(sessionRecords: List<SessionRecordEntity>): Array<Long>

    @Update
    abstract suspend fun update(sessionRecord: SessionRecordEntity): Int

    @Delete
    abstract suspend fun delete(sessionRecord: SessionRecordEntity): Int

    @Query("DELETE FROM $SESSION_RECORD_TABLE")
    abstract suspend fun deleteAllSessions(): Int

    @Query("SELECT * from $SESSION_RECORD_TABLE WHERE $SESSION_RECORD_ID =:sessionId")
    abstract suspend fun getSession(sessionId: Long): SessionRecordEntity?

    //ROOM does not allow parameters for the ORDER BY clause to prevent injection
    @Query("SELECT * from $SESSION_RECORD_TABLE ORDER BY $START_TIME_OF_RECORD ASC")
    abstract suspend fun getAllSessionsStartTimeAsc(): List<SessionRecordEntity>

    @Query("SELECT * from $SESSION_RECORD_TABLE ORDER BY $START_TIME_OF_RECORD DESC")
    abstract suspend fun getAllSessionsStartTimeDesc(): List<SessionRecordEntity>

    @Query("SELECT * from $SESSION_RECORD_TABLE ORDER BY $SESSION_REAL_DURATION ASC")
    abstract suspend fun getAllSessionsDurationAsc(): List<SessionRecordEntity>

    @Query("SELECT * from $SESSION_RECORD_TABLE ORDER BY $SESSION_REAL_DURATION DESC")
    abstract suspend fun getAllSessionsDurationDesc(): List<SessionRecordEntity>

    //Sessions WITH audio file guideMp3
    @Query("SELECT * from $SESSION_RECORD_TABLE WHERE $MP3_GUIDE != '' ORDER BY $START_TIME_OF_RECORD DESC")
    abstract suspend fun getAllSessionsWithMp3(): List<SessionRecordEntity>

    //Sessions WITHOUT audio file guideMp3
    @Query("SELECT * from $SESSION_RECORD_TABLE WHERE $MP3_GUIDE = '' ORDER BY $START_TIME_OF_RECORD DESC")
    abstract suspend fun getAllSessionsWithoutMp3(): List<SessionRecordEntity>

    //SEARCH on guideMp3 and notes - concatenating params with '%' in SQL
    @Query("SELECT * from $SESSION_RECORD_TABLE WHERE $MP3_GUIDE LIKE '%' || :searchRequest || '%' OR $NOTES LIKE '%' || :searchRequest || '%' ORDER BY $START_TIME_OF_RECORD DESC")
    abstract suspend fun getSessionsSearch(searchRequest: String): List<SessionRecordEntity>

    //LIST of all sessions as unobservable request, only for export
    @Query("SELECT * from $SESSION_RECORD_TABLE ORDER BY $START_TIME_OF_RECORD ASC")
    abstract suspend fun asyncGetAllSessionsStartTimeAsc(): List<SessionRecordEntity>

    //last session start timestamp
    @Query("SELECT max($START_TIME_OF_RECORD) from $SESSION_RECORD_TABLE")
    abstract suspend fun getMostRecentSessionRecordDate(): Long?

    //Count
    @Query("SELECT COUNT($SESSION_RECORD_ID) FROM $SESSION_RECORD_TABLE")
    abstract suspend fun getNumberOfRows(): Int
}
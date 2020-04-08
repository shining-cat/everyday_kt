package fr.shining_cat.everyday.locale.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.shining_cat.everyday.locale.entities.SessionRecordEntity


@Dao
abstract class SessionRecordDao{
    @Insert
    abstract suspend fun insert(sessionRecord: SessionRecordEntity): Long

    @Insert
    abstract suspend fun insertMultiple(sessionRecords: List<SessionRecordEntity>): Array<Long>

    @Update
    abstract suspend fun updateSession(sessionRecord: SessionRecordEntity): Int

    @Delete
    abstract suspend fun deleteSession(sessionRecord: SessionRecordEntity): Int

    @Query("DELETE FROM sessions_table")
    abstract suspend fun deleteAllSessions(): Int

    @Query("SELECT * from sessions_table WHERE id =:sessionId")
    abstract fun getSessionLive(sessionId: Long): LiveData<SessionRecordEntity?>

    //ROOM does not allow parameters for the ORDER BY clause to prevent injection
    @Query("SELECT * from sessions_table ORDER BY startTimeOfRecord ASC")
    abstract fun getAllSessionsStartTimeAsc(): LiveData<List<SessionRecordEntity>?>

    @Query("SELECT * from sessions_table ORDER BY startTimeOfRecord DESC")
    abstract fun getAllSessionsStartTimeDesc(): LiveData<List<SessionRecordEntity>?>

    @Query("SELECT * from sessions_table ORDER BY realDuration ASC")
    abstract fun getAllSessionsDurationAsc(): LiveData<List<SessionRecordEntity>?>

    @Query("SELECT * from sessions_table ORDER BY realDuration DESC")
    abstract fun getAllSessionsDurationDesc(): LiveData<List<SessionRecordEntity>?>

    //Sessions WITH audio file guideMp3
    @Query("SELECT * from sessions_table WHERE guidemp3 != '' ORDER BY startTimeOfRecord DESC")
    abstract fun getAllSessionsWithMp3(): LiveData<List<SessionRecordEntity>?>

    //Sessions WITHOUT audio file guideMp3
    @Query("SELECT * from sessions_table WHERE guidemp3 = '' ORDER BY startTimeOfRecord DESC")
    abstract fun getAllSessionsWithoutMp3(): LiveData<List<SessionRecordEntity>?>

    //SEARCH on guideMp3 and notes - concatenating params with '%' in SQL
    @Query("SELECT * from sessions_table WHERE guidemp3 LIKE '%' || :searchRequest || '%' OR notes LIKE '%' || :searchRequest || '%' ORDER BY startTimeOfRecord DESC")
    abstract fun getSessionsSearch(searchRequest: String): LiveData<List<SessionRecordEntity>?>

    //LIST of all sessions as unobservable request, only for export
    @Query("SELECT * from sessions_table ORDER BY startTimeOfRecord ASC")
    abstract suspend fun getAllSessionsNotLiveStartTimeAsc(): List<SessionRecordEntity>?

    //last session start timestamp
    @Query("SELECT max(startTimeOfRecord) from sessions_table")
    abstract suspend fun getMostRecentSessionRecordDate(): Long?

    //Count
    @Query("SELECT COUNT(id) FROM sessions_table")
    abstract suspend fun getNumberOfRows(): Int
}
package fr.shining_cat.everyday.localdata.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.shining_cat.everyday.localdata.dto.SessionDTO


@Dao
abstract class SessionDao{
//    @Insert
//    abstract suspend fun insert(sessionRecord: SessionDTO): Long
//
//    @Insert
//    abstract suspend fun insertMultiple(sessionRecords: List<SessionDTO>): Array<Long>
//
//    @Update
//    abstract suspend fun updateSession(sessionRecord: SessionDTO): Int
//
//    @Delete
//    abstract suspend fun deleteSession(sessionRecord: SessionDTO): Int
//
//    @Query("DELETE FROM sessions_table")
//    abstract suspend fun deleteAllSessions(): Int
//
//    //ROOM does not allow parameters for the ORDER BY clause to prevent injection
//    @Query("SELECT * from sessions_table ORDER BY startTimeOfRecord ASC")
//    abstract suspend fun getAllSessionsStartTimeAsc(): LiveData<List<SessionDTO>>
//
//    @Query("SELECT * from sessions_table ORDER BY startTimeOfRecord DESC")
//    abstract suspend fun getAllSessionsStartTimeDesc(): LiveData<List<SessionDTO>>
//
//    @Query("SELECT * from sessions_table ORDER BY realDuration ASC")
//    abstract suspend fun getAllSessionsDurationAsc(): LiveData<List<SessionDTO>>
//
//    @Query("SELECT * from sessions_table ORDER BY realDuration DESC")
//    abstract suspend fun getAllSessionsDurationDesc(): LiveData<List<SessionDTO>>
//
//    //Sessions WITH audio file guideMp3
//    @Query("SELECT * from sessions_table WHERE guidemp3 != '' ORDER BY startTimeOfRecord DESC")
//    abstract suspend fun getAllSessionsWithMp3(): LiveData<List<SessionDTO>>
//
//    //Sessions WITHOUT audio file guideMp3
//    @Query("SELECT * from sessions_table WHERE guidemp3 = '' ORDER BY startTimeOfRecord DESC")
//    abstract suspend fun getAllSessionsWithoutMp3(): LiveData<List<SessionDTO>>
//
//    //SEARCH on guideMp3 and notes
//    @Query("SELECT * from sessions_table WHERE guidemp3 LIKE :searchRequest OR notes LIKE :searchRequest ORDER BY startTimeOfRecord DESC")
//    abstract suspend fun getSessionsSearch(searchRequest: String): LiveData<List<SessionDTO>>
//
//    //LIST of all sessions as unobservable request, only for export
//    @Query("SELECT * from sessions_table ORDER BY startTimeOfRecord ASC")
//    abstract suspend fun getAllSessionsNotLiveStartTimeAsc(): List<SessionDTO>
//
//    //last session start timestamp
//    @Query("SELECT max(startTimeOfRecord) from sessions_table")
//    abstract suspend fun getLatestRecordedSessionDate(): Long
}
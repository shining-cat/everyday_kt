package fr.shining_cat.everyday.localdata.dao

import androidx.lifecycle.LiveData
import androidx.room.*

import fr.shining_cat.everyday.model.SessionRecord

@Dao
abstract class SessionRecordDao{
    @Insert
    abstract fun insert(sessionRecord: SessionRecord): Long

    @Insert
    abstract fun insertMultiple(vararg sessionRecords: SessionRecord): Array<Long>

    @Update
    abstract fun updateSessionRecord(sessionRecord: SessionRecord): Int

    @Delete
    abstract fun deleteSessionRecord(sessionRecord: SessionRecord): Int

    @Query("DELETE FROM sessions_table")
    abstract fun deleteAllSessions(): Int

    //ROOM does not allow parameters for the ORDER BY clause to prevent injection
    @Query("SELECT * from sessions_table ORDER BY startTimeOfRecord ASC")
    abstract fun getAllSessionsStartTimeAsc(): LiveData<List<SessionRecord>>

    @Query("SELECT * from sessions_table ORDER BY startTimeOfRecord DESC")
    abstract fun getAllSessionsStartTimeDesc(): LiveData<List<SessionRecord>>

    @Query("SELECT * from sessions_table ORDER BY sessionRealDuration ASC")
    abstract fun getAllSessionsDurationAsc(): LiveData<List<SessionRecord>>

    @Query("SELECT * from sessions_table ORDER BY sessionRealDuration DESC")
    abstract fun getAllSessionsDurationDesc(): LiveData<List<SessionRecord>>

    //
    @Query("SELECT * from sessions_table ORDER BY startTimeOfRecord ASC")
    abstract fun getAllSessionsNotLiveStartTimeAsc(): List<SessionRecord>

    //
    @Query("SELECT * from sessions_table WHERE guidemp3 != '' ORDER BY startTimeOfRecord DESC")
    abstract fun getAllSessionsWithMp3(): LiveData<List<SessionRecord>>

    @Query("SELECT * from sessions_table WHERE guidemp3 = '' ORDER BY startTimeOfRecord DESC")
    abstract fun getAllSessionsWithoutMp3(): LiveData<List<SessionRecord>>

    //
    @Query("SELECT * from sessions_table WHERE guidemp3 LIKE :searchRequest OR notes LIKE :searchRequest ORDER BY startTimeOfRecord DESC")
    abstract fun getSessionsSearch(searchRequest: String): LiveData<List<SessionRecord>>

    //
    @Query("SELECT max(startTimeOfRecord) from sessions_table")
    abstract fun getLatestRecordedSessionDate(): Long
}
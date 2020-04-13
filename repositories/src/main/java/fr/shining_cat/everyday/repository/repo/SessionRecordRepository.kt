package fr.shining_cat.everyday.repository.repo

import androidx.lifecycle.LiveData
import fr.shining_cat.everyday.locale.dao.SessionRecordDao
import fr.shining_cat.everyday.models.SessionRecord
import fr.shining_cat.everyday.repository.converter.SessionRecordConverter

interface SessionRecordRepository {
    suspend fun insert(sessionRecords: List<SessionRecord>): Array<Long>
    suspend fun update(sessionRecord: SessionRecord): Int
    suspend fun delete(sessionRecord: SessionRecord): Int
    suspend fun deleteAllSessions(): Int
    fun getAllSessionsStartTimeAsc(): LiveData<List<SessionRecord>>
    fun getAllSessionsStartTimeDesc(): LiveData<List<SessionRecord>>
    fun getAllSessionsDurationAsc(): LiveData<List<SessionRecord>>
    fun getAllSessionsDurationDesc(): LiveData<List<SessionRecord>>
    fun getAllSessionsWithMp3(): LiveData<List<SessionRecord>>
    fun getAllSessionsWithoutMp3(): LiveData<List<SessionRecord>>
    fun getSessionsSearch(searchRequest: String): LiveData<List<SessionRecord>>
    suspend fun getAllSessionsNotLiveStartTimeAsc(): List<SessionRecord>
    suspend fun getMostRecentSessionRecordDate(): Long
}

class SessionRecordRepositoryImpl(
    private val sessionRecordDao: SessionRecordDao,
    private val sessionRecordConverter: SessionRecordConverter
) : SessionRecordRepository {

    override suspend fun insert(sessionRecords: List<SessionRecord>): Array<Long> =
        sessionRecordDao.insert(sessionRecordConverter.convertModelsToEntities(sessionRecords))

    override suspend fun update(sessionRecord: SessionRecord): Int =
        sessionRecordDao.update(sessionRecordConverter.convertModelToEntity(sessionRecord))

    override suspend fun delete(sessionRecord: SessionRecord): Int =
        sessionRecordDao.delete(sessionRecordConverter.convertModelToEntity(sessionRecord))

    override suspend fun deleteAllSessions(): Int = sessionRecordDao.deleteAllSessions()

    override fun getAllSessionsStartTimeAsc(): LiveData<List<SessionRecord>> =
        sessionRecordConverter.convertEntitiesToModels(sessionRecordDao.getAllSessionsStartTimeAsc())

    override fun getAllSessionsStartTimeDesc(): LiveData<List<SessionRecord>> =
        sessionRecordConverter.convertEntitiesToModels(sessionRecordDao.getAllSessionsStartTimeDesc())

    override fun getAllSessionsDurationAsc(): LiveData<List<SessionRecord>> =
        sessionRecordConverter.convertEntitiesToModels(sessionRecordDao.getAllSessionsDurationAsc())

    override fun getAllSessionsDurationDesc(): LiveData<List<SessionRecord>> =
        sessionRecordConverter.convertEntitiesToModels(sessionRecordDao.getAllSessionsDurationDesc())

    //Sessions WITH audio file guideMp3
    override fun getAllSessionsWithMp3(): LiveData<List<SessionRecord>> =
        sessionRecordConverter.convertEntitiesToModels(sessionRecordDao.getAllSessionsWithMp3())

    //Sessions WITHOUT audio file guideMp3
    override fun getAllSessionsWithoutMp3(): LiveData<List<SessionRecord>> =
        sessionRecordConverter.convertEntitiesToModels(sessionRecordDao.getAllSessionsWithoutMp3())

    //SEARCH on guideMp3 and notes
    override fun getSessionsSearch(searchRequest: String): LiveData<List<SessionRecord>> =
        sessionRecordConverter.convertEntitiesToModels(sessionRecordDao.getSessionsSearch(searchRequest))

    //LIST of all sessions as unobservable request, only for export
    override suspend fun getAllSessionsNotLiveStartTimeAsc(): List<SessionRecord> =
        sessionRecordConverter.convertEntitiesToModels(sessionRecordDao.asyncGetAllSessionsStartTimeAsc())

    //last session start timestamp
    override suspend fun getMostRecentSessionRecordDate(): Long =
        sessionRecordDao.getMostRecentSessionRecordDate() ?: -1
}
package fr.shining_cat.everyday.repository

import androidx.lifecycle.LiveData
import fr.shining_cat.everyday.locale.dao.SessionDao
import fr.shining_cat.everyday.models.SessionModel
import fr.shining_cat.everyday.repository.converter.SessionConverter.Companion.convertDTOsToModels
import fr.shining_cat.everyday.repository.converter.SessionConverter.Companion.convertModelToDTO
import fr.shining_cat.everyday.repository.converter.SessionConverter.Companion.convertModelsToDTOs

interface SessionRepository{
    suspend fun insert(sessionModel: SessionModel): Long
    suspend fun insertMultiple(sessionModels: List<SessionModel>): Array<Long>
    suspend fun updateSession(sessionModel: SessionModel): Int
    suspend fun deleteSession(sessionModel: SessionModel): Int
    suspend fun deleteAllSessions(): Int
    fun getAllSessionsStartTimeAsc(): LiveData<List<SessionModel>>
    fun getAllSessionsStartTimeDesc(): LiveData<List<SessionModel>>
    fun getAllSessionsDurationAsc(): LiveData<List<SessionModel>>
    fun getAllSessionsDurationDesc(): LiveData<List<SessionModel>>
    fun getAllSessionsWithMp3(): LiveData<List<SessionModel>>
    fun getAllSessionsWithoutMp3(): LiveData<List<SessionModel>>
    fun getSessionsSearch(searchRequest: String): LiveData<List<SessionModel>>
    suspend fun getAllSessionsNotLiveStartTimeAsc(): List<SessionModel>
    suspend fun getLatestRecordedSessionDate(): Long
}

class SessionRepositoryImpl(private val sessionDao: SessionDao):SessionRepository {

    override suspend fun insert(sessionModel: SessionModel): Long = sessionDao.insert(convertModelToDTO(sessionModel))
    override suspend fun insertMultiple(sessionModels: List<SessionModel>): Array<Long> = sessionDao.insertMultiple(convertModelsToDTOs(sessionModels))

    override suspend fun updateSession(sessionModel: SessionModel): Int = sessionDao.updateSession(convertModelToDTO(sessionModel))

    override suspend fun deleteSession(sessionModel: SessionModel): Int = sessionDao.deleteSession(convertModelToDTO(sessionModel))
    override suspend fun deleteAllSessions(): Int = sessionDao.deleteAllSessions()

    override fun getAllSessionsStartTimeAsc(): LiveData<List<SessionModel>> = convertDTOsToModels(sessionDao.getAllSessionsStartTimeAsc())
    override fun getAllSessionsStartTimeDesc(): LiveData<List<SessionModel>> = convertDTOsToModels(sessionDao.getAllSessionsStartTimeDesc())

    override fun getAllSessionsDurationAsc(): LiveData<List<SessionModel>> = convertDTOsToModels(sessionDao.getAllSessionsDurationAsc())
    override fun getAllSessionsDurationDesc(): LiveData<List<SessionModel>> = convertDTOsToModels(sessionDao.getAllSessionsDurationDesc())

    //Sessions WITH audio file guideMp3
    override fun getAllSessionsWithMp3(): LiveData<List<SessionModel>> = convertDTOsToModels(sessionDao.getAllSessionsWithMp3())
    //Sessions WITHOUT audio file guideMp3
    override fun getAllSessionsWithoutMp3(): LiveData<List<SessionModel>> = convertDTOsToModels(sessionDao.getAllSessionsWithoutMp3())
    //SEARCH on guideMp3 and notes
    override fun getSessionsSearch(searchRequest: String): LiveData<List<SessionModel>> = convertDTOsToModels(sessionDao.getSessionsSearch(searchRequest))
    //LIST of all sessions as unobservable request, only for export
    override suspend fun getAllSessionsNotLiveStartTimeAsc(): List<SessionModel> = convertDTOsToModels(sessionDao.getAllSessionsNotLiveStartTimeAsc())
    //last session start timestamp
    override suspend fun getLatestRecordedSessionDate(): Long = sessionDao.getLatestRecordedSessionDate() ?: -1
}
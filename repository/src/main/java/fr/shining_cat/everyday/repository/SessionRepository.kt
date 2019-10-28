package fr.shining_cat.everyday.repository

import androidx.lifecycle.LiveData
import fr.shining_cat.everyday.localdata.dao.SessionDao
import fr.shining_cat.everyday.model.SessionModel
import fr.shining_cat.everyday.repository.converter.SessionConverter.Companion.convertDTOsToModels
import fr.shining_cat.everyday.repository.converter.SessionConverter.Companion.convertModelToDTO
import fr.shining_cat.everyday.repository.converter.SessionConverter.Companion.convertModelsToDTOs

class SessionRepository(private val sessionDao: SessionDao) {

    suspend fun insert(sessionModel: SessionModel): Long = sessionDao.insert(convertModelToDTO(sessionModel))
    suspend fun insertMultiple(sessionModels: List<SessionModel>): Array<Long> = sessionDao.insertMultiple(convertModelsToDTOs(sessionModels))

    suspend fun updateSession(sessionModel: SessionModel): Int = sessionDao.updateSession(convertModelToDTO(sessionModel))

    suspend fun deleteSession(sessionModel: SessionModel): Int = sessionDao.deleteSession(convertModelToDTO(sessionModel))
    suspend fun deleteAllSessions(): Int = sessionDao.deleteAllSessions()

    fun getAllSessionsStartTimeAsc(): LiveData<List<SessionModel>> = convertDTOsToModels(sessionDao.getAllSessionsStartTimeAsc())
    fun getAllSessionsStartTimeDesc(): LiveData<List<SessionModel>> = convertDTOsToModels(sessionDao.getAllSessionsStartTimeDesc())

    fun getAllSessionsDurationAsc(): LiveData<List<SessionModel>> = convertDTOsToModels(sessionDao.getAllSessionsDurationAsc())
    fun getAllSessionsDurationDesc(): LiveData<List<SessionModel>> = convertDTOsToModels(sessionDao.getAllSessionsDurationDesc())

    //Sessions WITH audio file guideMp3
    fun getAllSessionsWithMp3(): LiveData<List<SessionModel>> = convertDTOsToModels(sessionDao.getAllSessionsWithMp3())
    //Sessions WITHOUT audio file guideMp3
    fun getAllSessionsWithoutMp3(): LiveData<List<SessionModel>> = convertDTOsToModels(sessionDao.getAllSessionsWithoutMp3())
    //SEARCH on guideMp3 and notes
    fun getSessionsSearch(searchRequest: String): LiveData<List<SessionModel>> = convertDTOsToModels(sessionDao.getSessionsSearch(searchRequest))
    //LIST of all sessions as unobservable request, only for export
    suspend fun getAllSessionsNotLiveStartTimeAsc(): List<SessionModel> = convertDTOsToModels(sessionDao.getAllSessionsNotLiveStartTimeAsc())
    //last session start timestamp
    suspend fun getLatestRecordedSessionDate(): Long = sessionDao.getLatestRecordedSessionDate() ?: -1
}
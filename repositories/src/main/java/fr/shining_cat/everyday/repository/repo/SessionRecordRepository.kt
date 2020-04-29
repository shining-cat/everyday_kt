package fr.shining_cat.everyday.repository.repo

import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.locale.dao.SessionRecordDao
import fr.shining_cat.everyday.locale.entities.SessionRecordEntity
import fr.shining_cat.everyday.models.sessionrecord.SessionRecord
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.converter.SessionRecordConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SessionRecordRepository {
    suspend fun insert(sessionRecords: List<SessionRecord>): Output<Array<Long>>
    suspend fun update(sessionRecord: SessionRecord): Output<Int>
    suspend fun delete(sessionRecord: SessionRecord): Output<Int>
    suspend fun deleteAllSessions(): Int
    suspend fun getAllSessionsStartTimeAsc(): Output<List<SessionRecord>>
    suspend fun getAllSessionsStartTimeDesc(): Output<List<SessionRecord>>
    suspend fun getAllSessionsDurationAsc(): Output<List<SessionRecord>>
    suspend fun getAllSessionsDurationDesc(): Output<List<SessionRecord>>
    suspend fun getAllSessionsWithMp3(): Output<List<SessionRecord>>
    suspend fun getAllSessionsWithoutMp3(): Output<List<SessionRecord>>
    suspend fun getSessionsSearch(searchRequest: String): Output<List<SessionRecord>>
    suspend fun asyncGetAllSessionsStartTimeAsc(): Output<List<SessionRecord>>
    suspend fun getMostRecentSessionRecordDate(): Long
}

class SessionRecordRepositoryImpl(
    private val sessionRecordDao: SessionRecordDao,
    private val sessionRecordConverter: SessionRecordConverter
) : SessionRecordRepository {

    override suspend fun insert(sessionRecords: List<SessionRecord>): Output<Array<Long>> {
        val inserted = withContext(Dispatchers.IO) {
            sessionRecordDao.insert(
                sessionRecordConverter.convertModelsToEntities(sessionRecords)
            )
        }
        return if (inserted.size == sessionRecords.size) {
            Output.Success(inserted)
        } else {
            Output.Error(
                Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                Constants.ERROR_MESSAGE_INSERT_FAILED,
                Exception(Constants.ERROR_MESSAGE_INSERT_FAILED)
            )
        }
    }

    override suspend fun update(sessionRecord: SessionRecord): Output<Int> {
        val updated = withContext(Dispatchers.IO) {
            sessionRecordDao.update(
                sessionRecordConverter.convertModelToEntity(sessionRecord)
            )
        }
        return if (updated == 1) {
            Output.Success(updated)
        } else {
            Output.Error(
                Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                Constants.ERROR_MESSAGE_UPDATE_FAILED,
                Exception(Constants.ERROR_MESSAGE_UPDATE_FAILED)
            )

        }
    }

    override suspend fun delete(sessionRecord: SessionRecord): Output<Int> {
        val deleted = withContext(Dispatchers.IO) {
            sessionRecordDao.delete(
                sessionRecordConverter.convertModelToEntity(sessionRecord)
            )
        }
        return if (deleted == 1) {
            Output.Success(deleted)
        } else {
            Output.Error(
                Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                Constants.ERROR_MESSAGE_DELETE_FAILED,
                Exception(Constants.ERROR_MESSAGE_DELETE_FAILED)
            )
        }
    }

    override suspend fun deleteAllSessions(): Int =
        withContext(Dispatchers.IO) { sessionRecordDao.deleteAllSessions() }


    private suspend fun handleQueryResult(sessionRecordEntities: List<SessionRecordEntity>): Output<List<SessionRecord>> {
        return if (sessionRecordEntities.isEmpty()) {
            Output.Error(
                Constants.ERROR_CODE_NO_RESULT,
                Constants.ERROR_MESSAGE_NO_RESULT,
                NullPointerException(Constants.ERROR_MESSAGE_NO_RESULT)
            )
        } else {
            Output.Success(
                withContext(Dispatchers.Default) {
                    sessionRecordConverter.convertEntitiesToModels(sessionRecordEntities)
                }
            )
        }
    }

    override suspend fun getAllSessionsStartTimeAsc(): Output<List<SessionRecord>> {
        val sessionRecordEntities = withContext(Dispatchers.IO) {
            sessionRecordDao.getAllSessionsStartTimeAsc()
        }
        return handleQueryResult(sessionRecordEntities)
    }

    override suspend fun getAllSessionsStartTimeDesc(): Output<List<SessionRecord>> {
        val sessionRecordEntities = withContext(Dispatchers.IO) {
            sessionRecordDao.getAllSessionsStartTimeDesc()
        }
        return handleQueryResult(sessionRecordEntities)
    }

    override suspend fun getAllSessionsDurationAsc(): Output<List<SessionRecord>> {
        val sessionRecordEntities = withContext(Dispatchers.IO) {
            sessionRecordDao.getAllSessionsDurationAsc()
        }
        return handleQueryResult(sessionRecordEntities)
    }

    override suspend fun getAllSessionsDurationDesc(): Output<List<SessionRecord>> {
        val sessionRecordEntities = withContext(Dispatchers.IO) {
            sessionRecordDao.getAllSessionsDurationDesc()
        }
        return handleQueryResult(sessionRecordEntities)
    }

    //Sessions WITH audio file guideMp3
    override suspend fun getAllSessionsWithMp3(): Output<List<SessionRecord>> {
        val sessionRecordEntities = withContext(Dispatchers.IO) {
            sessionRecordDao.getAllSessionsWithMp3()
        }
        return handleQueryResult(sessionRecordEntities)
    }

    //Sessions WITHOUT audio file guideMp3
    override suspend fun getAllSessionsWithoutMp3(): Output<List<SessionRecord>> {
        val sessionRecordEntities = withContext(Dispatchers.IO) {
            sessionRecordDao.getAllSessionsWithoutMp3()
        }
        return handleQueryResult(sessionRecordEntities)
    }

    //SEARCH on guideMp3 and notes
    override suspend fun getSessionsSearch(searchRequest: String): Output<List<SessionRecord>> {
        val sessionRecordEntities = withContext(Dispatchers.IO) {
            sessionRecordDao.getSessionsSearch(searchRequest)
        }
        return handleQueryResult(sessionRecordEntities)
    }

    //LIST of all sessions as unobservable request, only for export
    override suspend fun asyncGetAllSessionsStartTimeAsc(): Output<List<SessionRecord>> {
        val sessionRecordEntities = withContext(Dispatchers.IO) {
            sessionRecordDao.asyncGetAllSessionsStartTimeAsc()
        }
        return handleQueryResult(sessionRecordEntities)
    }

    //last session start timestamp
    override suspend fun getMostRecentSessionRecordDate(): Long = withContext(Dispatchers.IO) {
        sessionRecordDao.getMostRecentSessionRecordDate() ?: -1
    }
}

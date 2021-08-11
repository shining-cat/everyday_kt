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

package fr.shining_cat.everyday.repository.repo

import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.locale.dao.SessionRecordDao
import fr.shining_cat.everyday.locale.entities.SessionRecordEntity
import fr.shining_cat.everyday.models.sessionrecord.SessionRecord
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.converters.SessionRecordConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SessionRecordRepository {

    suspend fun insert(sessionRecords: List<SessionRecord>): Output<Array<Long>>
    suspend fun update(sessionRecord: SessionRecord): Output<Int>
    suspend fun delete(sessionRecord: SessionRecord): Output<Int>
    suspend fun deleteAllSessions(): Output<Int>
    suspend fun getAllSessionsStartTimeAsc(): Output<List<SessionRecord>>
    suspend fun getAllSessionsStartTimeDesc(): Output<List<SessionRecord>>
    suspend fun getAllSessionsDurationAsc(): Output<List<SessionRecord>>
    suspend fun getAllSessionsDurationDesc(): Output<List<SessionRecord>>
    suspend fun getAllSessionsWithMp3(): Output<List<SessionRecord>>
    suspend fun getAllSessionsWithoutMp3(): Output<List<SessionRecord>>
    suspend fun getSessionsSearch(searchRequest: String): Output<List<SessionRecord>>
    suspend fun asyncGetAllSessionsStartTimeAsc(): Output<List<SessionRecord>>
    suspend fun getMostRecentSessionRecordDate(): Output<Long>
}

class SessionRecordRepositoryImpl(
    private val sessionRecordDao: SessionRecordDao,
    private val sessionRecordConverter: SessionRecordConverter,
    private val logger: Logger
): SessionRecordRepository {

    private val LOG_TAG = SessionRecordRepositoryImpl::class.java.name

    private fun genericReadError(exception: java.lang.Exception) = Output.Error(
        Constants.ERROR_CODE_DATABASE_OPERATION_FAILED, Constants.ERROR_MESSAGE_READ_FAILED, exception
    )

    override suspend fun insert(sessionRecords: List<SessionRecord>): Output<Array<Long>> {
        return try {
            val inserted = withContext(Dispatchers.IO) {
                sessionRecordDao.insert(
                    sessionRecordConverter.convertModelsToEntities(sessionRecords)
                )
            }
            if (inserted.size == sessionRecords.size) {
                Output.Success(inserted)
            }
            else {
                Output.Error(
                    Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                    Constants.ERROR_MESSAGE_INSERT_FAILED,
                    Exception(Constants.ERROR_MESSAGE_INSERT_FAILED)
                )
            }
        } catch (exception: Exception) {
            Output.Error(
                Constants.ERROR_CODE_DATABASE_OPERATION_FAILED, Constants.ERROR_MESSAGE_INSERT_FAILED, exception
            )
        }
    }

    override suspend fun update(sessionRecord: SessionRecord): Output<Int> {
        return try {
            val updated = withContext(Dispatchers.IO) {
                sessionRecordDao.update(
                    sessionRecordConverter.convertModelToEntity(sessionRecord)
                )
            }
            if (updated == 1) {
                Output.Success(updated)
            }
            else {
                Output.Error(
                    Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                    Constants.ERROR_MESSAGE_UPDATE_FAILED,
                    Exception(Constants.ERROR_MESSAGE_UPDATE_FAILED)
                )
            }
        } catch (exception: Exception) {
            Output.Error(
                Constants.ERROR_CODE_DATABASE_OPERATION_FAILED, Constants.ERROR_MESSAGE_UPDATE_FAILED, exception
            )
        }
    }

    override suspend fun delete(sessionRecord: SessionRecord): Output<Int> {
        return try {
            val deleted = withContext(Dispatchers.IO) {
                sessionRecordDao.delete(
                    sessionRecordConverter.convertModelToEntity(sessionRecord)
                )
            }
            if (deleted == 1) {
                Output.Success(deleted)
            }
            else {
                Output.Error(
                    Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                    Constants.ERROR_MESSAGE_DELETE_FAILED,
                    Exception(Constants.ERROR_MESSAGE_DELETE_FAILED)
                )
            }
        } catch (exception: Exception) {
            Output.Error(
                Constants.ERROR_CODE_DATABASE_OPERATION_FAILED, Constants.ERROR_MESSAGE_DELETE_FAILED, exception
            )
        }
    }

    override suspend fun deleteAllSessions(): Output<Int> {
        return try {
            val deleted = withContext(Dispatchers.IO) {sessionRecordDao.deleteAllSessions()}
            Output.Success(deleted)
        } catch (exception: Exception) {
            Output.Error(
                Constants.ERROR_CODE_DATABASE_OPERATION_FAILED, Constants.ERROR_MESSAGE_DELETE_FAILED, exception
            )
        }
    }

    // GET SESSIONS
    override suspend fun getAllSessionsStartTimeAsc(): Output<List<SessionRecord>> {
        return try {
            val sessionRecordEntities = withContext(Dispatchers.IO) {
                sessionRecordDao.getAllSessionsStartTimeAsc()
            }
            handleQueryResult(sessionRecordEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    override suspend fun getAllSessionsStartTimeDesc(): Output<List<SessionRecord>> {
        return try {
            val sessionRecordEntities = withContext(Dispatchers.IO) {
                sessionRecordDao.getAllSessionsStartTimeDesc()
            }
            handleQueryResult(sessionRecordEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    override suspend fun getAllSessionsDurationAsc(): Output<List<SessionRecord>> {
        return try {
            val sessionRecordEntities = withContext(Dispatchers.IO) {
                sessionRecordDao.getAllSessionsDurationAsc()
            }
            handleQueryResult(sessionRecordEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    override suspend fun getAllSessionsDurationDesc(): Output<List<SessionRecord>> {
        return try {
            val sessionRecordEntities = withContext(Dispatchers.IO) {
                sessionRecordDao.getAllSessionsDurationDesc()
            }
            handleQueryResult(sessionRecordEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    // Sessions WITH audio file guideMp3
    override suspend fun getAllSessionsWithMp3(): Output<List<SessionRecord>> {
        return try {
            val sessionRecordEntities = withContext(Dispatchers.IO) {
                sessionRecordDao.getAllSessionsWithMp3()
            }
            handleQueryResult(sessionRecordEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    // Sessions WITHOUT audio file guideMp3
    override suspend fun getAllSessionsWithoutMp3(): Output<List<SessionRecord>> {
        return try {
            val sessionRecordEntities = withContext(Dispatchers.IO) {
                sessionRecordDao.getAllSessionsWithoutMp3()
            }
            handleQueryResult(sessionRecordEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    // SEARCH on guideMp3 and notes
    override suspend fun getSessionsSearch(searchRequest: String): Output<List<SessionRecord>> {
        return try {
            val sessionRecordEntities = withContext(Dispatchers.IO) {
                sessionRecordDao.getSessionsSearch(searchRequest)
            }
            handleQueryResult(sessionRecordEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    // LIST of all sessions as unobservable request, only for export
    override suspend fun asyncGetAllSessionsStartTimeAsc(): Output<List<SessionRecord>> {
        return try {
            val sessionRecordEntities = withContext(Dispatchers.IO) {
                sessionRecordDao.asyncGetAllSessionsStartTimeAsc()
            }
            handleQueryResult(sessionRecordEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    // last session start timestamp
    override suspend fun getMostRecentSessionRecordDate(): Output<Long> {
        return try {
            val date = withContext(Dispatchers.IO) {
                sessionRecordDao.getMostRecentSessionRecordDate()
            }
            if (date != null) {
                Output.Success(date)
            }
            else {
                Output.Error(
                    Constants.ERROR_CODE_NO_RESULT, Constants.ERROR_MESSAGE_NO_RESULT, NullPointerException(Constants.ERROR_MESSAGE_NO_RESULT)
                )
            }
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    //
    private suspend fun handleQueryResult(sessionRecordEntities: List<SessionRecordEntity>): Output<List<SessionRecord>> {
        return if (sessionRecordEntities.isEmpty()) {
            Output.Error(
                Constants.ERROR_CODE_NO_RESULT, Constants.ERROR_MESSAGE_NO_RESULT, NullPointerException(Constants.ERROR_MESSAGE_NO_RESULT)
            )
        }
        else {
            Output.Success(withContext(Dispatchers.Default) {
                sessionRecordConverter.convertEntitiesToModels(sessionRecordEntities)
            })
        }
    }
}
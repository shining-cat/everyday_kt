package fr.shining_cat.everyday.repository.repo

import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.locale.dao.SessionTypeDao
import fr.shining_cat.everyday.locale.entities.SessionTypeEntity
import fr.shining_cat.everyday.models.SessionType
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.converters.SessionTypeConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SessionTypeRepository {
    suspend fun insert(sessionTypes: List<SessionType>): Output<Array<Long>>
    suspend fun update(sessionType: SessionType): Output<Int>
    suspend fun delete(sessionType: SessionType): Output<Int>
    suspend fun getAllSessionTypesLastEditTimeDesc(): Output<List<SessionType>>
}

class SessionTypeRepositoryImpl(
    val sessionTypeDao: SessionTypeDao,
    val sessionTypeConverter: SessionTypeConverter
) : SessionTypeRepository {

    private fun genericReadError(exception: java.lang.Exception) = Output.Error(
        Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
        Constants.ERROR_MESSAGE_READ_FAILED,
        exception
    )

    override suspend fun insert(sessionTypes: List<SessionType>): Output<Array<Long>> {
        return try {
            val inserted = withContext(Dispatchers.IO) {
                sessionTypeDao.insert(
                    sessionTypeConverter.convertModelsToEntities(sessionTypes)
                )
            }
            if (inserted.size == sessionTypes.size) {
                Output.Success(inserted)
            } else {
                Output.Error(
                    Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                    Constants.ERROR_MESSAGE_INSERT_FAILED,
                    Exception(Constants.ERROR_MESSAGE_INSERT_FAILED)
                )
            }
        } catch (exception: Exception) {
            Output.Error(
                Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                Constants.ERROR_MESSAGE_INSERT_FAILED,
                exception
            )
        }
    }

    override suspend fun update(sessionType: SessionType): Output<Int> {
        return try {
            val updated = withContext(Dispatchers.IO) {
                sessionTypeDao.update(
                    sessionTypeConverter.convertModelToEntity(sessionType)
                )
            }
            if (updated == 1) {
                Output.Success(updated)
            } else {
                Output.Error(
                    Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                    Constants.ERROR_MESSAGE_UPDATE_FAILED,
                    Exception(Constants.ERROR_MESSAGE_UPDATE_FAILED)
                )
            }
        } catch (exception: Exception) {
            Output.Error(
                Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                Constants.ERROR_MESSAGE_UPDATE_FAILED,
                exception
            )
        }
    }

    override suspend fun delete(sessionType: SessionType): Output<Int> {
        return try {
            val deleted = withContext(Dispatchers.IO) {
                sessionTypeDao.delete(
                    sessionTypeConverter.convertModelToEntity(sessionType)
                )
            }
            if (deleted == 1) {
                Output.Success(deleted)
            } else {
                Output.Error(
                    Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                    Constants.ERROR_MESSAGE_DELETE_FAILED,
                    Exception(Constants.ERROR_MESSAGE_DELETE_FAILED)
                )
            }
        } catch (exception: Exception) {
            Output.Error(
                Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                Constants.ERROR_MESSAGE_DELETE_FAILED,
                exception
            )
        }
    }

    override suspend fun getAllSessionTypesLastEditTimeDesc(): Output<List<SessionType>> {
        return try {
            val sessionTypeEntities = withContext(Dispatchers.IO) {
                sessionTypeDao.getAllSessionTypesLastEditTimeDesc()
            }
            handleQueryResult(sessionTypeEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    private suspend fun handleQueryResult(sessionTypeEntities: List<SessionTypeEntity>): Output<List<SessionType>> {
        return if (sessionTypeEntities.isEmpty()) {
            Output.Error(
                Constants.ERROR_CODE_NO_RESULT,
                Constants.ERROR_MESSAGE_NO_RESULT,
                NullPointerException(Constants.ERROR_MESSAGE_NO_RESULT)
            )
        } else {
            Output.Success(
                withContext(Dispatchers.Default) {
                    sessionTypeConverter.convertEntitiesToModels(sessionTypeEntities)
                }
            )
        }
    }
}
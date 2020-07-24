package fr.shining_cat.everyday.repository.repo

import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.locale.dao.SessionTypeDao
import fr.shining_cat.everyday.locale.entities.SessionTypeEntity
import fr.shining_cat.everyday.models.SessionType
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.converter.SessionTypeConverter
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

    override suspend fun insert(sessionTypes: List<SessionType>): Output<Array<Long>> {
        val inserted = withContext(Dispatchers.IO) {
            sessionTypeDao.insert(
                sessionTypeConverter.convertModelsToEntities(sessionTypes)
            )
        }
        return if (inserted.size == sessionTypes.size) {
            Output.Success(inserted)
        } else {
            Output.Error(
                Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                Constants.ERROR_MESSAGE_INSERT_FAILED,
                Exception(Constants.ERROR_MESSAGE_INSERT_FAILED)
            )
        }
    }

    override suspend fun update(sessionType: SessionType): Output<Int> {
        val updated = withContext(Dispatchers.IO) {
            sessionTypeDao.update(
                sessionTypeConverter.convertModelToEntity(sessionType)
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

    override suspend fun delete(sessionType: SessionType): Output<Int> {
        val deleted = withContext(Dispatchers.IO) {
            sessionTypeDao.delete(
                sessionTypeConverter.convertModelToEntity(sessionType)
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

    override suspend fun getAllSessionTypesLastEditTimeDesc(): Output<List<SessionType>> {
        val sessionTypeEntities = withContext(Dispatchers.IO) {
            sessionTypeDao.getAllSessionTypesLastEditTimeDesc()
        }
        return handleQueryResult(sessionTypeEntities)
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
package fr.shining_cat.everyday.repository.repo

import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.locale.dao.SessionPresetDao
import fr.shining_cat.everyday.locale.entities.SessionPresetEntity
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.converter.SessionPresetConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SessionPresetRepository {
    suspend fun insert(sessionPresets: List<SessionPreset>): Output<Array<Long>>
    suspend fun update(sessionPreset: SessionPreset): Output<Int>
    suspend fun delete(sessionPreset: SessionPreset): Output<Int>
    suspend fun getAllSessionPresetsLastEditTimeDesc(): Output<List<SessionPreset>>
}

class SessionPresetRepositoryImpl(
    val sessionPresetDao: SessionPresetDao,
    val sessionPresetConverter: SessionPresetConverter
) : SessionPresetRepository {

    override suspend fun insert(sessionPresets: List<SessionPreset>): Output<Array<Long>> {
        val inserted = withContext(Dispatchers.IO) {
            sessionPresetDao.insert(
                sessionPresetConverter.convertModelsToEntities(sessionPresets)
            )
        }
        return if (inserted.size == sessionPresets.size) {
            Output.Success(inserted)
        } else {
            Output.Error(
                Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                Constants.ERROR_MESSAGE_INSERT_FAILED,
                Exception(Constants.ERROR_MESSAGE_INSERT_FAILED)
            )
        }
    }

    override suspend fun update(sessionPreset: SessionPreset): Output<Int> {
        val updated = withContext(Dispatchers.IO) {
            sessionPresetDao.update(
                sessionPresetConverter.convertModelToEntity(sessionPreset)
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

    override suspend fun delete(sessionPreset: SessionPreset): Output<Int> {
        val deleted = withContext(Dispatchers.IO) {
            sessionPresetDao.delete(
                sessionPresetConverter.convertModelToEntity(sessionPreset)
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

    override suspend fun getAllSessionPresetsLastEditTimeDesc(): Output<List<SessionPreset>> {
        val sessionPresetEntities = withContext(Dispatchers.IO) {
            sessionPresetDao.getAllSessionPresetsLastEditTimeDesc()
        }
        return handleQueryResult(sessionPresetEntities)
    }

    private suspend fun handleQueryResult(sessionPresetEntities: List<SessionPresetEntity>): Output<List<SessionPreset>> {
        return if (sessionPresetEntities.isEmpty()) {
            Output.Error(
                Constants.ERROR_CODE_NO_RESULT,
                Constants.ERROR_MESSAGE_NO_RESULT,
                NullPointerException(Constants.ERROR_MESSAGE_NO_RESULT)
            )
        } else {
            Output.Success(
                withContext(Dispatchers.Default) {
                    sessionPresetConverter.convertEntitiesToModels(sessionPresetEntities)
                }
            )
        }
    }
}
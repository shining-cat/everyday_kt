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
import fr.shining_cat.everyday.locale.dao.SessionPresetDao
import fr.shining_cat.everyday.locale.entities.SessionPresetEntity
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.converters.SessionPresetConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SessionPresetRepository {

    suspend fun insert(sessionPresets: List<SessionPreset>): Output<Array<Long>>
    suspend fun update(sessionPreset: SessionPreset): Output<Int>
    suspend fun delete(sessionPreset: SessionPreset): Output<Int>
    suspend fun getAllSessionPresetsLastEditTimeDesc(): Output<List<SessionPreset>>
}

class SessionPresetRepositoryImpl(
    private val sessionPresetDao: SessionPresetDao,
    private val sessionPresetConverter: SessionPresetConverter,
    private val logger: Logger
) : SessionPresetRepository {

    private val LOG_TAG = SessionPresetRepositoryImpl::class.java.name

    private fun genericReadError(exception: java.lang.Exception) = Output.Error(
        Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
        Constants.ERROR_MESSAGE_READ_FAILED,
        exception
    )

    override suspend fun insert(sessionPresets: List<SessionPreset>): Output<Array<Long>> {
        return try {
            val inserted = withContext(Dispatchers.IO) {
                sessionPresetDao.insert(
                    sessionPresetConverter.convertModelsToEntities(sessionPresets)
                )
            }
            if (inserted.size == sessionPresets.size) {
                Output.Success(inserted)
            } else {
                logger.e(LOG_TAG, "execute::result is not the right size")
                Output.Error(
                    Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                    Constants.ERROR_MESSAGE_INSERT_FAILED,
                    Exception(Constants.ERROR_MESSAGE_INSERT_FAILED)
                )
            }
        } catch (exception: Exception) {
            logger.e(LOG_TAG, "insert::encountered an exception::${exception.toString()}")
            Output.Error(
                Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                Constants.ERROR_MESSAGE_INSERT_FAILED,
                exception
            )
        }
    }

    override suspend fun update(sessionPreset: SessionPreset): Output<Int> {
        return try {
            val updated = withContext(Dispatchers.IO) {
                sessionPresetDao.update(
                    sessionPresetConverter.convertModelToEntity(sessionPreset)
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

    override suspend fun delete(sessionPreset: SessionPreset): Output<Int> {
        return try {
            val deleted = withContext(Dispatchers.IO) {
                sessionPresetDao.delete(
                    sessionPresetConverter.convertModelToEntity(sessionPreset)
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

    override suspend fun getAllSessionPresetsLastEditTimeDesc(): Output<List<SessionPreset>> {
        return try {
            val sessionPresetEntities = withContext(Dispatchers.IO) {
                sessionPresetDao.getAllSessionPresetsLastEditTimeDesc()
            }
            handleQueryResult(sessionPresetEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
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
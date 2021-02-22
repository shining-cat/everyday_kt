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
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_CODE_DATABASE_OPERATION_FAILED
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_MESSAGE_COUNT_FAILED
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_MESSAGE_DELETE_FAILED
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_MESSAGE_INSERT_FAILED
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_MESSAGE_NO_RESULT
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_MESSAGE_READ_FAILED
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_MESSAGE_UPDATE_FAILED
import fr.shining_cat.everyday.locale.dao.RewardDao
import fr.shining_cat.everyday.locale.entities.RewardEntity
import fr.shining_cat.everyday.models.reward.Reward
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.converters.RewardConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface RewardRepository {

    suspend fun insert(rewards: List<Reward>): Output<Array<Long>>
    suspend fun update(rewards: List<Reward>): Output<Int>
    suspend fun deleteAllRewards(): Output<Int>
    suspend fun getReward(rewardId: Long): Output<Reward>
    suspend fun rewardsActiveAcquisitionDateAsc(): Output<List<Reward>>
    suspend fun rewardsActiveAcquisitionDateDesc(): Output<List<Reward>>
    suspend fun rewardsActiveLevelAsc(): Output<List<Reward>>
    suspend fun rewardsActiveLevelDesc(): Output<List<Reward>>
    suspend fun rewardsNotEscapedAcquisitionDateDesc(): Output<List<Reward>>
    suspend fun rewardsEscapedAcquisitionDateDesc(): Output<List<Reward>>
    suspend fun rewardsOfSPecificLevelNotActive(level: Int): Output<List<Reward>>
    suspend fun rewardsOfSPecificLevelNotActiveOrEscaped(level: Int): Output<List<Reward>>
    suspend fun countAllRewards(): Output<Int>
    suspend fun countActiveNotEscapedRewardsForLevel(level: Int): Output<Int>
    suspend fun countEscapedRewardsForLevel(level: Int): Output<Int>
}

class RewardRepositoryImpl(
    private val rewardDao: RewardDao,
    private val rewardConverter: RewardConverter
) : RewardRepository {

    private fun genericReadError(exception: Exception) = Output.Error(
        ERROR_CODE_DATABASE_OPERATION_FAILED,
        ERROR_MESSAGE_READ_FAILED,
        exception
    )

    private fun genericCountError(exception: Exception) = Output.Error(
        ERROR_CODE_DATABASE_OPERATION_FAILED,
        ERROR_MESSAGE_COUNT_FAILED,
        exception
    )

    override suspend fun insert(rewards: List<Reward>): Output<Array<Long>> {
        return try {
            val inserted = withContext(Dispatchers.IO) {
                rewardDao.insert(
                    rewardConverter.convertModelsToEntities(rewards)
                )
            }
            if (inserted.size == rewards.size) {
                Output.Success(inserted)
            } else {
                Output.Error(
                    ERROR_CODE_DATABASE_OPERATION_FAILED,
                    ERROR_MESSAGE_INSERT_FAILED,
                    Exception(ERROR_MESSAGE_INSERT_FAILED)
                )
            }
        } catch (exception: Exception) {
            Output.Error(
                ERROR_CODE_DATABASE_OPERATION_FAILED,
                ERROR_MESSAGE_INSERT_FAILED,
                exception
            )
        }
    }

    override suspend fun update(rewards: List<Reward>): Output<Int> {
        return try {
            val updated = withContext(Dispatchers.IO) {
                rewardDao.update(
                    rewardConverter.convertModelsToEntities(rewards)
                )
            }
            if (updated == rewards.size) {
                Output.Success(updated)
            } else {
                Output.Error(
                    ERROR_CODE_DATABASE_OPERATION_FAILED,
                    ERROR_MESSAGE_UPDATE_FAILED,
                    Exception(ERROR_MESSAGE_UPDATE_FAILED)
                )
            }
        } catch (exception: Exception) {
            Output.Error(
                ERROR_CODE_DATABASE_OPERATION_FAILED,
                ERROR_MESSAGE_UPDATE_FAILED,
                exception
            )
        }
    }

    override suspend fun deleteAllRewards(): Output<Int> {
        return try {
            val deleted = withContext(Dispatchers.IO) { rewardDao.deleteAllRewards() }
            Output.Success(deleted)
        } catch (exception: Exception) {
            Output.Error(
                ERROR_CODE_DATABASE_OPERATION_FAILED,
                ERROR_MESSAGE_DELETE_FAILED,
                Exception(ERROR_MESSAGE_DELETE_FAILED)
            )
        }
    }

    override suspend fun getReward(rewardId: Long): Output<Reward> {
        return try {
            val rewardEntity = withContext(Dispatchers.IO) { rewardDao.getReward(rewardId) }
            if (rewardEntity == null) {
                Output.Error(
                    Constants.ERROR_CODE_NO_RESULT,
                    ERROR_MESSAGE_NO_RESULT,
                    NullPointerException(ERROR_MESSAGE_NO_RESULT)
                )
            } else {
                Output.Success(
                    withContext(Dispatchers.Default) {
                        rewardConverter.convertEntitytoModel(rewardEntity)
                    }
                )
            }
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    //rewards active

    override suspend fun rewardsActiveAcquisitionDateAsc(): Output<List<Reward>> {
        return try {
            val rewardEntities = withContext(Dispatchers.IO) {
                rewardDao.getAllRewardsActiveAcquisitionDateAsc()
            }
            handleQueryResult(rewardEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    override suspend fun rewardsActiveAcquisitionDateDesc(): Output<List<Reward>> {
        return try {
            val rewardEntities = withContext(Dispatchers.IO) {
                rewardDao.getAllRewardsActiveAcquisitionDateDesc()
            }
            handleQueryResult(rewardEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    override suspend fun rewardsActiveLevelAsc(): Output<List<Reward>> {
        return try {
            val rewardEntities = withContext(Dispatchers.IO) {
                rewardDao.getAllRewardsActiveLevelAsc()
            }
            handleQueryResult(rewardEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    override suspend fun rewardsActiveLevelDesc(): Output<List<Reward>> {
        return try {
            val rewardEntities = withContext(Dispatchers.IO) {
                rewardDao.getAllRewardsActiveLevelDesc()
            }
            handleQueryResult(rewardEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    //ACTIVE and NOT-LOST rewards :
    override suspend fun rewardsNotEscapedAcquisitionDateDesc(): Output<List<Reward>> {
        return try {
            val rewardEntities = withContext(Dispatchers.IO) {
                rewardDao.getAllRewardsNotEscapedAcquisitionDatDesc()
            }
            handleQueryResult(rewardEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    //ACTIVE and LOST rewards :
    override suspend fun rewardsEscapedAcquisitionDateDesc(): Output<List<Reward>> {
        return try {
            val rewardEntities = withContext(Dispatchers.IO) {
                rewardDao.getAllRewardsEscapedAcquisitionDateDesc()
            }
            handleQueryResult(rewardEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    //NON-ACTIVE rewards for specific LEVEL:
    override suspend fun rewardsOfSPecificLevelNotActive(level: Int): Output<List<Reward>> {
        return try {
            val rewardEntities = withContext(Dispatchers.IO) {
                rewardDao.getAllRewardsOfSpecificLevelNotActive(level)
            }
            handleQueryResult(rewardEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    //NON-ACTIVE or ACTIVE-and-ESCAPED rewards for specific LEVEL:
    override suspend fun rewardsOfSPecificLevelNotActiveOrEscaped(level: Int): Output<List<Reward>> {
        return try {
            val rewardEntities = withContext(Dispatchers.IO) {
                rewardDao.getAllRewardsOfSpecificLevelNotActiveOrEscaped(level)
            }
            handleQueryResult(rewardEntities)
        } catch (exception: Exception) {
            genericReadError(exception)
        }
    }

    //COUNTS :
    override suspend fun countAllRewards(): Output<Int> {
        return try {
            val count = withContext(Dispatchers.IO) {
                rewardDao.getNumberOfRows()
            }
            Output.Success(count)
        } catch (exception: Exception) {
            genericCountError(exception)
        }
    }

    override suspend fun countActiveNotEscapedRewardsForLevel(level: Int): Output<Int> {
        return try {
            val count = withContext(Dispatchers.IO) {
                rewardDao.getNumberOfActiveNotEscapedRewardsForLevel(level)
            }
            Output.Success(count)
        } catch (exception: Exception) {
            genericCountError(exception)
        }
    }

    override suspend fun countEscapedRewardsForLevel(level: Int): Output<Int> {
        return try {
            val count = withContext(Dispatchers.IO) {
                rewardDao.getNumberOfEscapedRewardsForLevel(level)
            }
            Output.Success(count)
        } catch (exception: Exception) {
            genericCountError(exception)
        }
    }

    //
    private suspend fun handleQueryResult(rewardEntities: List<RewardEntity>): Output<List<Reward>> {
        return if (rewardEntities.isEmpty()) {
            Output.Error(
                Constants.ERROR_CODE_NO_RESULT,
                ERROR_MESSAGE_NO_RESULT,
                NullPointerException(ERROR_MESSAGE_NO_RESULT)
            )
        } else {
            Output.Success(
                withContext(Dispatchers.Default) {
                    rewardConverter.convertEntitiesToModels(rewardEntities)
                }
            )
        }
    }
}
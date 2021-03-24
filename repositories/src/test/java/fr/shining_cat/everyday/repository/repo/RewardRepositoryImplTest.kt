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
import fr.shining_cat.everyday.locale.dao.RewardDao
import fr.shining_cat.everyday.locale.entities.RewardEntity
import fr.shining_cat.everyday.models.reward.Reward
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.converters.RewardConverter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RewardRepositoryImplTest {

    @MockK
    private lateinit var mockRewardDao: RewardDao

    @MockK
    private lateinit var mockRewardConverter: RewardConverter

    @MockK
    private lateinit var mockReward: Reward

    @MockK
    lateinit var mockRewardEntity: RewardEntity

    @MockK
    lateinit var mockException: Exception

    @MockK
    lateinit var mockThrowable: Throwable

    private lateinit var rewardRepo: RewardRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Assert.assertNotNull(mockRewardDao)
        Assert.assertNotNull(mockReward)
        Assert.assertNotNull(mockRewardEntity)
        Assert.assertNotNull(mockRewardConverter)
        rewardRepo = RewardRepositoryImpl(
            mockRewardDao,
            mockRewardConverter
        )
        coEvery { mockException.cause } returns mockThrowable
    }

    // /////////////////////////////
    @Test
    fun insert() {
        coEvery { mockRewardConverter.convertModelsToEntities(any()) } returns listOf(
            mockRewardEntity,
            mockRewardEntity,
            mockRewardEntity
        )
        coEvery { mockRewardDao.insert(any()) } returns arrayOf(
            1L,
            2L,
            3L
        )
        val output = runBlocking {
            rewardRepo.insert(
                listOf(
                    mockReward,
                    mockReward,
                    mockReward
                )
            )
        }
        coVerify { mockRewardConverter.convertModelsToEntities(any()) }
        coVerify { mockRewardDao.insert(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            3,
            (output as Output.Success).result.size
        )
    }

    @Test
    fun `insert failed list size does not match`() {
        coEvery { mockRewardConverter.convertModelsToEntities(any()) } returns listOf(
            mockRewardEntity,
            mockRewardEntity,
            mockRewardEntity
        )
        coEvery { mockRewardDao.insert(any()) } returns arrayOf(1L, 2L)
        val output = runBlocking {
            rewardRepo.insert(
                listOf(
                    mockReward,
                    mockReward,
                    mockReward
                )
            )
        }
        coVerify { mockRewardConverter.convertModelsToEntities(any()) }
        coVerify { mockRewardDao.insert(any()) }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_INSERT_FAILED,
            output.errorResponse
        )
        assertEquals(
            Constants.ERROR_MESSAGE_INSERT_FAILED,
            output.exception?.message
        )
    }

    @Test
    fun `insert failed with exception`() {
        coEvery { mockRewardConverter.convertModelsToEntities(any()) } returns listOf(
            mockRewardEntity,
            mockRewardEntity,
            mockRewardEntity
        )
        coEvery { mockRewardDao.insert(any()) } throws mockException
        val output = runBlocking {
            rewardRepo.insert(
                listOf(
                    mockReward,
                    mockReward,
                    mockReward
                )
            )
        }
        coVerify { mockRewardConverter.convertModelsToEntities(any()) }
        coVerify { mockRewardDao.insert(any()) }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_INSERT_FAILED,
            output.errorResponse
        )
        assertEquals(
            mockException,
            output.exception
        )
    }

    @Test
    fun updateRewards() {
        coEvery { mockRewardConverter.convertModelsToEntities(any()) } returns listOf(
            mockRewardEntity,
            mockRewardEntity,
            mockRewardEntity
        )
        coEvery { mockRewardDao.update(any()) } returns 3
        val output = runBlocking {
            rewardRepo.update(
                listOf(
                    mockReward,
                    mockReward,
                    mockReward
                )
            )
        }
        coVerify { mockRewardConverter.convertModelsToEntities(any()) }
        coVerify { mockRewardDao.update(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            3,
            (output as Output.Success).result
        )
    }

    @Test
    fun `update failed list size does not match`() {
        coEvery { mockRewardConverter.convertModelsToEntities(any()) } returns listOf(
            mockRewardEntity,
            mockRewardEntity,
            mockRewardEntity
        )
        coEvery { mockRewardDao.update(any()) } returns 2
        val output = runBlocking {
            rewardRepo.update(
                listOf(
                    mockReward,
                    mockReward,
                    mockReward
                )
            )
        }
        coVerify { mockRewardConverter.convertModelsToEntities(any()) }
        coVerify { mockRewardDao.update(any()) }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_UPDATE_FAILED,
            output.errorResponse
        )
        assertEquals(
            Constants.ERROR_MESSAGE_UPDATE_FAILED,
            output.exception?.message
        )
    }

    @Test
    fun `update failed with exception`() {
        coEvery { mockRewardConverter.convertModelsToEntities(any()) } returns listOf(
            mockRewardEntity,
            mockRewardEntity,
            mockRewardEntity
        )
        coEvery { mockRewardDao.update(any()) } throws mockException
        val output = runBlocking {
            rewardRepo.update(
                listOf(
                    mockReward,
                    mockReward,
                    mockReward
                )
            )
        }
        coVerify { mockRewardConverter.convertModelsToEntities(any()) }
        coVerify { mockRewardDao.update(any()) }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_UPDATE_FAILED,
            output.errorResponse
        )
        assertEquals(
            mockException,
            output.exception
        )
    }

    @Test
    fun deleteAllRewards() {
        coEvery { mockRewardDao.deleteAllRewards() } returns 7
        val output = runBlocking {
            rewardRepo.deleteAllRewards()
        }
        coVerify { mockRewardDao.deleteAllRewards() }
        assertTrue(output is Output.Success)
        assertEquals(
            7,
            (output as Output.Success).result
        )
    }

    @Test
    fun `delete all failed with exception`() {
        coEvery { mockRewardDao.deleteAllRewards() } throws mockException
        val output = runBlocking {
            rewardRepo.deleteAllRewards()
        }
        coVerify { mockRewardDao.deleteAllRewards() }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_DELETE_FAILED,
            output.errorResponse
        )
        assertEquals(
            mockException,
            output.exception
        )
    }

    // //////////////
    // GETTERS
    @Test
    fun getSpecificReward() {
        coEvery { mockRewardDao.getReward(8L) } returns mockRewardEntity
        coEvery { mockRewardConverter.convertEntitytoModel(any()) } returns mockReward
        val output = runBlocking {
            rewardRepo.getReward(8L)
        }
        coVerify { mockRewardDao.getReward(any()) }
        coVerify { mockRewardConverter.convertEntitytoModel(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            mockReward,
            (output as Output.Success).result
        )
    }

    @Test
    fun `getSpecificReward failed DAO returned null`() {
        coEvery { mockRewardDao.getReward(8L) } returns null
        val output = runBlocking {
            rewardRepo.getReward(8L)
        }
        coVerify { mockRewardDao.getReward(any()) }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_NO_RESULT,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.errorResponse
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.exception?.message
        )
    }

    @Test
    fun `getSpecificReward failed with exception`() {
        coEvery { mockRewardDao.getReward(8L) } throws mockException
        val output = runBlocking {
            rewardRepo.getReward(8L)
        }
        coVerify { mockRewardDao.getReward(any()) }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_READ_FAILED,
            output.errorResponse
        )
        assertEquals(
            mockException,
            output.exception
        )
    }

    //
    @Test
    fun rewardsActiveAcquisitionDateAsc() {
        coEvery { mockRewardDao.getAllRewardsActiveAcquisitionDateAsc() } returns listOf(
            mockRewardEntity
        )
        coEvery { mockRewardConverter.convertEntitiesToModels(any()) } returns listOf(mockReward)
        val output = runBlocking {
            rewardRepo.rewardsActiveAcquisitionDateAsc()
        }
        coVerify { mockRewardDao.getAllRewardsActiveAcquisitionDateAsc() }
        coVerify { mockRewardConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockReward),
            (output as Output.Success).result
        )
    }

    @Test
    fun `rewardsActiveAcquisitionDateAsc failed DAO returned empty result`() {
        coEvery { mockRewardDao.getAllRewardsActiveAcquisitionDateAsc() } returns listOf()
        val output = runBlocking {
            rewardRepo.rewardsActiveAcquisitionDateAsc()
        }
        coVerify { mockRewardDao.getAllRewardsActiveAcquisitionDateAsc() }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_NO_RESULT,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.errorResponse
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.exception?.message
        )
    }

    @Test
    fun `rewardsActiveAcquisitionDateAsc failed with exception`() {
        coEvery { mockRewardDao.getAllRewardsActiveAcquisitionDateAsc() } throws mockException
        val output = runBlocking {
            rewardRepo.rewardsActiveAcquisitionDateAsc()
        }
        coVerify { mockRewardDao.getAllRewardsActiveAcquisitionDateAsc() }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_READ_FAILED,
            output.errorResponse
        )
        assertEquals(
            mockException,
            output.exception
        )
    }

    //
    @Test
    fun rewardsActiveAcquisitionDateDesc() {
        coEvery { mockRewardDao.getAllRewardsActiveAcquisitionDateDesc() } returns listOf(
            mockRewardEntity
        )
        coEvery { mockRewardConverter.convertEntitiesToModels(any()) } returns listOf(mockReward)
        val output = runBlocking {
            rewardRepo.rewardsActiveAcquisitionDateDesc()
        }
        coVerify { mockRewardDao.getAllRewardsActiveAcquisitionDateDesc() }
        coVerify { mockRewardConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockReward),
            (output as Output.Success).result
        )
    }

    @Test
    fun `rewardsActiveAcquisitionDateDesc failed DAO returned empty result`() {
        coEvery { mockRewardDao.getAllRewardsActiveAcquisitionDateDesc() } returns listOf()
        val output = runBlocking {
            rewardRepo.rewardsActiveAcquisitionDateDesc()
        }
        coVerify { mockRewardDao.getAllRewardsActiveAcquisitionDateDesc() }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_NO_RESULT,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.errorResponse
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.exception?.message
        )
    }

    @Test
    fun `rewardsActiveAcquisitionDateDesc failed with exception`() {
        coEvery { mockRewardDao.getAllRewardsActiveAcquisitionDateDesc() } throws mockException
        val output = runBlocking {
            rewardRepo.rewardsActiveAcquisitionDateDesc()
        }
        coVerify { mockRewardDao.getAllRewardsActiveAcquisitionDateDesc() }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_READ_FAILED,
            output.errorResponse
        )
        assertEquals(
            mockException,
            output.exception
        )
    }

    //
    @Test
    fun rewardsActiveLevelAsc() {
        coEvery { mockRewardDao.getAllRewardsActiveLevelAsc() } returns listOf(
            mockRewardEntity
        )
        coEvery { mockRewardConverter.convertEntitiesToModels(any()) } returns listOf(mockReward)
        val output = runBlocking {
            rewardRepo.rewardsActiveLevelAsc()
        }
        coVerify { mockRewardDao.getAllRewardsActiveLevelAsc() }
        coVerify { mockRewardConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockReward),
            (output as Output.Success).result
        )
    }

    @Test
    fun `rewardsActiveLevelAsc failed DAO returned empty result`() {
        coEvery { mockRewardDao.getAllRewardsActiveLevelAsc() } returns listOf()
        val output = runBlocking {
            rewardRepo.rewardsActiveLevelAsc()
        }
        coVerify { mockRewardDao.getAllRewardsActiveLevelAsc() }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_NO_RESULT,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.errorResponse
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.exception?.message
        )
    }

    @Test
    fun `rewardsActiveLevelAsc failed with exception`() {
        coEvery { mockRewardDao.getAllRewardsActiveLevelAsc() } throws mockException
        val output = runBlocking {
            rewardRepo.rewardsActiveLevelAsc()
        }
        coVerify { mockRewardDao.getAllRewardsActiveLevelAsc() }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_READ_FAILED,
            output.errorResponse
        )
        assertEquals(
            mockException,
            output.exception
        )
    }

    //
    @Test
    fun rewardsActiveLevelDesc() {
        coEvery { mockRewardDao.getAllRewardsActiveLevelDesc() } returns listOf(
            mockRewardEntity
        )
        coEvery { mockRewardConverter.convertEntitiesToModels(any()) } returns listOf(mockReward)
        val output = runBlocking {
            rewardRepo.rewardsActiveLevelDesc()
        }
        coVerify { mockRewardDao.getAllRewardsActiveLevelDesc() }
        coVerify { mockRewardConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockReward),
            (output as Output.Success).result
        )
    }

    @Test
    fun `rewardsActiveLevelDesc failed DAO returned empty result`() {
        coEvery { mockRewardDao.getAllRewardsActiveLevelDesc() } returns listOf()
        val output = runBlocking {
            rewardRepo.rewardsActiveLevelDesc()
        }
        coVerify { mockRewardDao.getAllRewardsActiveLevelDesc() }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_NO_RESULT,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.errorResponse
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.exception?.message
        )
    }

    @Test
    fun `rewardsActiveLevelDesc failed with exception`() {
        coEvery { mockRewardDao.getAllRewardsActiveLevelDesc() } throws mockException
        val output = runBlocking {
            rewardRepo.rewardsActiveLevelDesc()
        }
        coVerify { mockRewardDao.getAllRewardsActiveLevelDesc() }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_READ_FAILED,
            output.errorResponse
        )
        assertEquals(
            mockException,
            output.exception
        )
    }

    //
    @Test
    fun rewardsNotEscapedAcquisitionDateDesc() {
        coEvery { mockRewardDao.getAllRewardsNotEscapedAcquisitionDatDesc() } returns listOf(
            mockRewardEntity
        )
        coEvery { mockRewardConverter.convertEntitiesToModels(any()) } returns listOf(mockReward)
        val output = runBlocking {
            rewardRepo.rewardsNotEscapedAcquisitionDateDesc()
        }
        coVerify { mockRewardDao.getAllRewardsNotEscapedAcquisitionDatDesc() }
        coVerify { mockRewardConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockReward),
            (output as Output.Success).result
        )
    }

    @Test
    fun `rewardsNotEscapedAcquisitionDateDesc failed DAO returned empty result`() {
        coEvery { mockRewardDao.getAllRewardsNotEscapedAcquisitionDatDesc() } returns listOf()
        val output = runBlocking {
            rewardRepo.rewardsNotEscapedAcquisitionDateDesc()
        }
        coVerify { mockRewardDao.getAllRewardsNotEscapedAcquisitionDatDesc() }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_NO_RESULT,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.errorResponse
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.exception?.message
        )
    }

    @Test
    fun `rewardsNotEscapedAcquisitionDateDesc failed with exception`() {
        coEvery { mockRewardDao.getAllRewardsNotEscapedAcquisitionDatDesc() } throws mockException
        val output = runBlocking {
            rewardRepo.rewardsNotEscapedAcquisitionDateDesc()
        }
        coVerify { mockRewardDao.getAllRewardsNotEscapedAcquisitionDatDesc() }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_READ_FAILED,
            output.errorResponse
        )
        assertEquals(
            mockException,
            output.exception
        )
    }

    //
    @Test
    fun rewardsEscapedAcquisitionDateDesc() {
        coEvery { mockRewardDao.getAllRewardsEscapedAcquisitionDateDesc() } returns listOf(
            mockRewardEntity
        )
        coEvery { mockRewardConverter.convertEntitiesToModels(any()) } returns listOf(mockReward)
        val output = runBlocking {
            rewardRepo.rewardsEscapedAcquisitionDateDesc()
        }
        coVerify { mockRewardDao.getAllRewardsEscapedAcquisitionDateDesc() }
        coVerify { mockRewardConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockReward),
            (output as Output.Success).result
        )
    }

    @Test
    fun `rewardsEscapedAcquisitionDateDesc failed DAO returned empty result`() {
        coEvery { mockRewardDao.getAllRewardsEscapedAcquisitionDateDesc() } returns listOf()
        val output = runBlocking {
            rewardRepo.rewardsEscapedAcquisitionDateDesc()
        }
        coVerify { mockRewardDao.getAllRewardsEscapedAcquisitionDateDesc() }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_NO_RESULT,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.errorResponse
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.exception?.message
        )
    }

    @Test
    fun `rewardsEscapedAcquisitionDateDesc failed with exception`() {
        coEvery { mockRewardDao.getAllRewardsEscapedAcquisitionDateDesc() } throws mockException
        val output = runBlocking {
            rewardRepo.rewardsEscapedAcquisitionDateDesc()
        }
        coVerify { mockRewardDao.getAllRewardsEscapedAcquisitionDateDesc() }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_READ_FAILED,
            output.errorResponse
        )
        assertEquals(
            mockException,
            output.exception
        )
    }

    //
    @Test
    fun rewardsOfSpecificLevelNotActive() {
        coEvery { mockRewardDao.getAllRewardsOfSpecificLevelNotActive(any()) } returns listOf(
            mockRewardEntity
        )
        coEvery { mockRewardConverter.convertEntitiesToModels(any()) } returns listOf(mockReward)
        val output = runBlocking {
            rewardRepo.rewardsOfSPecificLevelNotActive(3)
        }
        coVerify { mockRewardDao.getAllRewardsOfSpecificLevelNotActive(any()) }
        coVerify { mockRewardConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockReward),
            (output as Output.Success).result
        )
    }

    @Test
    fun `rewardsOfSpecificLevelNotActive failed DAO returned empty result`() {
        coEvery { mockRewardDao.getAllRewardsOfSpecificLevelNotActive(any()) } returns listOf()
        val output = runBlocking {
            rewardRepo.rewardsOfSPecificLevelNotActive(3)
        }
        coVerify { mockRewardDao.getAllRewardsOfSpecificLevelNotActive(3) }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_NO_RESULT,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.errorResponse
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.exception?.message
        )
    }

    @Test
    fun `rewardsOfSpecificLevelNotActive failed with exception`() {
        coEvery { mockRewardDao.getAllRewardsOfSpecificLevelNotActive(any()) } throws mockException
        val output = runBlocking {
            rewardRepo.rewardsOfSPecificLevelNotActive(3)
        }
        coVerify { mockRewardDao.getAllRewardsOfSpecificLevelNotActive(3) }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_READ_FAILED,
            output.errorResponse
        )
        assertEquals(
            mockException,
            output.exception
        )
    }

    //
    @Test
    fun rewardsOfSPecificLevelNotActiveOrEscaped() {
        coEvery { mockRewardDao.getAllRewardsOfSpecificLevelNotActiveOrEscaped(any()) } returns listOf(
            mockRewardEntity
        )
        coEvery { mockRewardConverter.convertEntitiesToModels(any()) } returns listOf(mockReward)
        val output = runBlocking {
            rewardRepo.rewardsOfSPecificLevelNotActiveOrEscaped(4)
        }
        coVerify { mockRewardDao.getAllRewardsOfSpecificLevelNotActiveOrEscaped(any()) }
        coVerify { mockRewardConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockReward),
            (output as Output.Success).result
        )
    }

    @Test
    fun `rewardsOfSPecificLevelNotActiveOrEscaped failed DAO returned empty result`() {
        coEvery { mockRewardDao.getAllRewardsOfSpecificLevelNotActiveOrEscaped(any()) } returns listOf()
        val output = runBlocking {
            rewardRepo.rewardsOfSPecificLevelNotActiveOrEscaped(5)
        }
        coVerify { mockRewardDao.getAllRewardsOfSpecificLevelNotActiveOrEscaped(5) }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_NO_RESULT,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.errorResponse
        )
        assertEquals(
            Constants.ERROR_MESSAGE_NO_RESULT,
            output.exception?.message
        )
    }

    @Test
    fun `rewardsOfSPecificLevelNotActiveOrEscaped failed with exception`() {
        coEvery { mockRewardDao.getAllRewardsOfSpecificLevelNotActiveOrEscaped(any()) } throws mockException
        val output = runBlocking {
            rewardRepo.rewardsOfSPecificLevelNotActiveOrEscaped(6)
        }
        coVerify { mockRewardDao.getAllRewardsOfSpecificLevelNotActiveOrEscaped(6) }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_READ_FAILED,
            output.errorResponse
        )
        assertEquals(
            mockException,
            output.exception
        )
    }

    // //////////////
    // COUNTS
    @Test
    fun allRewardsCount() {
        coEvery { mockRewardDao.getNumberOfRows() } returns 13
        val output = runBlocking {
            rewardRepo.countAllRewards()
        }
        coVerify { mockRewardDao.getNumberOfRows() }
        assertTrue(output is Output.Success)
        assertEquals(
            13,
            (output as Output.Success).result
        )
    }

    @Test
    fun `allRewardsCount failed with exception`() {
        coEvery { mockRewardDao.getNumberOfRows() } throws mockException
        val output = runBlocking {
            rewardRepo.countAllRewards()
        }
        coVerify { mockRewardDao.getNumberOfRows() }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_COUNT_FAILED,
            output.errorResponse
        )
        assertEquals(
            mockException,
            output.exception
        )
    }

    //
    @Test
    fun activeNotEscapedRewardsForLevel() {
        coEvery { mockRewardDao.getNumberOfActiveNotEscapedRewardsForLevel(4) } returns 7
        val output4 = runBlocking {
            rewardRepo.countActiveNotEscapedRewardsForLevel(4)
        }
        coVerify(exactly = 1) { mockRewardDao.getNumberOfActiveNotEscapedRewardsForLevel(any()) }
        assertTrue(output4 is Output.Success)
        assertEquals(
            7,
            (output4 as Output.Success).result
        )
    }

    @Test
    fun `activeNotEscapedRewardsForLevel failed with exception`() {
        coEvery { mockRewardDao.getNumberOfActiveNotEscapedRewardsForLevel(any()) } throws mockException
        val output = runBlocking {
            rewardRepo.countActiveNotEscapedRewardsForLevel(4)
        }
        coVerify(exactly = 1) { mockRewardDao.getNumberOfActiveNotEscapedRewardsForLevel(any()) }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_COUNT_FAILED,
            output.errorResponse
        )
        assertEquals(
            mockException,
            output.exception
        )
    }

    //
    @Test
    fun escapedRewardsForLevel() {
        coEvery { mockRewardDao.getNumberOfEscapedRewardsForLevel(3) } returns 9
        val output3 = runBlocking {
            rewardRepo.countEscapedRewardsForLevel(3)
        }
        coVerify(exactly = 1) { mockRewardDao.getNumberOfEscapedRewardsForLevel(any()) }
        assertTrue(output3 is Output.Success)
        assertEquals(
            9,
            (output3 as Output.Success).result
        )
    }

    @Test
    fun `escapedRewardsForLevel failed with exception`() {
        coEvery { mockRewardDao.getNumberOfEscapedRewardsForLevel(any()) } throws mockException
        val output = runBlocking {
            rewardRepo.countEscapedRewardsForLevel(3)
        }
        coVerify(exactly = 1) { mockRewardDao.getNumberOfEscapedRewardsForLevel(any()) }
        assertTrue(output is Output.Error)
        output as Output.Error
        assertEquals(
            Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
            output.errorCode
        )
        assertEquals(
            Constants.ERROR_MESSAGE_COUNT_FAILED,
            output.errorResponse
        )
        assertEquals(
            mockException,
            output.exception
        )
    }
}
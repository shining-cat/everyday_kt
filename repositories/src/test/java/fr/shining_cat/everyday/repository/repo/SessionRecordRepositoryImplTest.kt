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
import fr.shining_cat.everyday.locale.dao.SessionRecordDao
import fr.shining_cat.everyday.locale.entities.SessionRecordEntity
import fr.shining_cat.everyday.models.sessionrecord.SessionRecord
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.converters.SessionRecordConverter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SessionRecordRepositoryImplTest {

    @MockK
    private lateinit var mockSessionRecordDao: SessionRecordDao

    @MockK
    private lateinit var mockSessionRecordConverter: SessionRecordConverter

    @MockK
    private lateinit var mockSessionRecord: SessionRecord

    @MockK
    lateinit var mockSessionRecordEntity: SessionRecordEntity

    @MockK
    lateinit var mockException: Exception

    @MockK
    lateinit var mockThrowable: Throwable

    private lateinit var sessionRecordRepo: SessionRecordRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        assertNotNull(mockSessionRecordDao)
        assertNotNull(mockSessionRecord)
        assertNotNull(mockSessionRecordEntity)
        sessionRecordRepo = SessionRecordRepositoryImpl(
            mockSessionRecordDao,
            mockSessionRecordConverter
        )
        coEvery { mockSessionRecordConverter.convertModelsToEntities(any()) } returns listOf(
            mockSessionRecordEntity
        )
        coEvery { mockSessionRecordConverter.convertModelToEntity(any()) } returns mockSessionRecordEntity
        coEvery { mockSessionRecordConverter.convertEntitiesToModels(any()) } returns listOf(
            mockSessionRecord
        )
        coEvery { mockSessionRecordConverter.convertEntityToModel(any()) } returns mockSessionRecord
        coEvery { mockException.cause } returns mockThrowable
    }

    @Test
    fun insert() {
        coEvery { mockSessionRecordDao.insert(any()) } returns arrayOf(1L, 2L, 3L)
        val output = runBlocking {
            sessionRecordRepo.insert(
                listOf(
                    mockSessionRecord,
                    mockSessionRecord,
                    mockSessionRecord
                )
            )
        }
        coVerify { mockSessionRecordConverter.convertModelsToEntities(any()) }
        coVerify { mockSessionRecordDao.insert(listOf(mockSessionRecordEntity)) }
        assertTrue(output is Output.Success)
        assertEquals(
            3,
            (output as Output.Success).result.size
        )
    }

    @Test
    fun `insert failed list size does not match`() {
        coEvery { mockSessionRecordDao.insert(any()) } returns arrayOf(1L, 2L)
        val output = runBlocking {
            sessionRecordRepo.insert(
                listOf(
                    mockSessionRecord,
                    mockSessionRecord,
                    mockSessionRecord
                )
            )
        }
        coVerify { mockSessionRecordConverter.convertModelsToEntities(any()) }
        coVerify { mockSessionRecordDao.insert(listOf(mockSessionRecordEntity)) }
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
        coEvery { mockSessionRecordDao.insert(any()) } throws mockException
        val output = runBlocking {
            sessionRecordRepo.insert(
                listOf(
                    mockSessionRecord,
                    mockSessionRecord,
                    mockSessionRecord
                )
            )
        }
        coVerify { mockSessionRecordConverter.convertModelsToEntities(any()) }
        coVerify { mockSessionRecordDao.insert(listOf(mockSessionRecordEntity)) }
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

    //
    @Test
    fun update() {
        coEvery { mockSessionRecordDao.update(any()) } returns 1
        val output = runBlocking {
            sessionRecordRepo.update(mockSessionRecord)
        }
        coVerify { mockSessionRecordConverter.convertModelToEntity(any()) }
        coVerify { mockSessionRecordDao.update(mockSessionRecordEntity) }
        assertTrue(output is Output.Success)
        assertEquals(
            1,
            (output as Output.Success).result
        )
    }

    @Test
    fun `update failed count does not match`() {
        coEvery { mockSessionRecordDao.update(any()) } returns 0
        val output = runBlocking {
            sessionRecordRepo.update(mockSessionRecord)
        }
        coVerify { mockSessionRecordConverter.convertModelToEntity(any()) }
        coVerify { mockSessionRecordDao.update(mockSessionRecordEntity) }
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
        coEvery { mockSessionRecordDao.update(any()) } throws mockException
        val output = runBlocking {
            sessionRecordRepo.update(mockSessionRecord)
        }
        coVerify { mockSessionRecordConverter.convertModelToEntity(any()) }
        coVerify { mockSessionRecordDao.update(mockSessionRecordEntity) }
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

    //
    @Test
    fun deleteSession() {
        coEvery { mockSessionRecordDao.delete(any()) } returns 1
        val output = runBlocking {
            sessionRecordRepo.delete(mockSessionRecord)
        }
        coVerify { mockSessionRecordConverter.convertModelToEntity(any()) }
        coVerify { mockSessionRecordDao.delete(mockSessionRecordEntity) }
        assertTrue(output is Output.Success)
        assertEquals(
            1,
            (output as Output.Success).result
        )
    }

    @Test
    fun `delete failed count does not match`() {
        coEvery { mockSessionRecordDao.delete(any()) } returns 0
        val output = runBlocking {
            sessionRecordRepo.delete(mockSessionRecord)
        }
        coVerify { mockSessionRecordConverter.convertModelToEntity(any()) }
        coVerify { mockSessionRecordDao.delete(mockSessionRecordEntity) }
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
            Constants.ERROR_MESSAGE_DELETE_FAILED,
            output.exception?.message
        )
    }

    @Test
    fun `delete failed with exception`() {
        coEvery { mockSessionRecordDao.delete(any()) } throws mockException
        val output = runBlocking {
            sessionRecordRepo.delete(mockSessionRecord)
        }
        coVerify { mockSessionRecordConverter.convertModelToEntity(any()) }
        coVerify { mockSessionRecordDao.delete(mockSessionRecordEntity) }
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

    //
    @Test
    fun deleteAllSessions() {
        coEvery { mockSessionRecordDao.deleteAllSessions() } returns 17
        val output = runBlocking {
            sessionRecordRepo.deleteAllSessions()
        }
        coVerify { mockSessionRecordDao.deleteAllSessions() }
        assertTrue(output is Output.Success)
        assertEquals(
            17,
            (output as Output.Success).result
        )
    }

    @Test
    fun `delete all failed with exception`() {
        coEvery { mockSessionRecordDao.deleteAllSessions() } throws mockException
        val output = runBlocking {
            sessionRecordRepo.deleteAllSessions()
        }
        coVerify { mockSessionRecordDao.deleteAllSessions() }
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

    //
    @Test
    fun getAllSessionsStartTimeAsc() {
        coEvery { mockSessionRecordDao.getAllSessionsStartTimeAsc() } returns listOf(mockSessionRecordEntity)
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsStartTimeAsc()
        }
        coVerify { mockSessionRecordDao.getAllSessionsStartTimeAsc() }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockSessionRecord),
            (output as Output.Success).result
        )
    }

    @Test
    fun `rewardsActiveAcquisitionDateAsc failed DAO returned empty result`() {
        coEvery { mockSessionRecordDao.getAllSessionsStartTimeAsc() } returns listOf()
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsStartTimeAsc()
        }
        coVerify { mockSessionRecordDao.getAllSessionsStartTimeAsc() }
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
        coEvery { mockSessionRecordDao.getAllSessionsStartTimeAsc() } throws mockException
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsStartTimeAsc()
        }
        coVerify { mockSessionRecordDao.getAllSessionsStartTimeAsc() }
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
    fun getAllSessionsStartTimeDesc() {
        coEvery { mockSessionRecordDao.getAllSessionsStartTimeDesc() } returns listOf(mockSessionRecordEntity)
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsStartTimeDesc()
        }
        coVerify { mockSessionRecordDao.getAllSessionsStartTimeDesc() }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockSessionRecord),
            (output as Output.Success).result
        )
    }

    @Test
    fun `getAllSessionsStartTimeDesc failed DAO returned empty result`() {
        coEvery { mockSessionRecordDao.getAllSessionsStartTimeDesc() } returns listOf()
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsStartTimeDesc()
        }
        coVerify { mockSessionRecordDao.getAllSessionsStartTimeDesc() }
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
    fun `getAllSessionsStartTimeDesc failed with exception`() {
        coEvery { mockSessionRecordDao.getAllSessionsStartTimeDesc() } throws mockException
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsStartTimeDesc()
        }
        coVerify { mockSessionRecordDao.getAllSessionsStartTimeDesc() }
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
    fun getAllSessionsDurationAsc() {
        coEvery { mockSessionRecordDao.getAllSessionsDurationAsc() } returns listOf(mockSessionRecordEntity)
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsDurationAsc()
        }
        coVerify { mockSessionRecordDao.getAllSessionsDurationAsc() }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockSessionRecord),
            (output as Output.Success).result
        )
    }

    @Test
    fun `getAllSessionsDurationAsc failed DAO returned empty result`() {
        coEvery { mockSessionRecordDao.getAllSessionsDurationAsc() } returns listOf()
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsDurationAsc()
        }
        coVerify { mockSessionRecordDao.getAllSessionsDurationAsc() }
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
    fun `getAllSessionsDurationAsc failed with exception`() {
        coEvery { mockSessionRecordDao.getAllSessionsDurationAsc() } throws mockException
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsDurationAsc()
        }
        coVerify { mockSessionRecordDao.getAllSessionsDurationAsc() }
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
    fun getAllSessionsDurationDesc() {
        coEvery { mockSessionRecordDao.getAllSessionsDurationDesc() } returns listOf(mockSessionRecordEntity)
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsDurationDesc()
        }
        coVerify { mockSessionRecordDao.getAllSessionsDurationDesc() }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockSessionRecord),
            (output as Output.Success).result
        )
    }

    @Test
    fun `getAllSessionsDurationDesc failed DAO returned empty result`() {
        coEvery { mockSessionRecordDao.getAllSessionsDurationDesc() } returns listOf()
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsDurationDesc()
        }
        coVerify { mockSessionRecordDao.getAllSessionsDurationDesc() }
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
    fun `getAllSessionsDurationDesc failed with exception`() {
        coEvery { mockSessionRecordDao.getAllSessionsDurationDesc() } throws mockException
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsDurationDesc()
        }
        coVerify { mockSessionRecordDao.getAllSessionsDurationDesc() }
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
    fun getAllSessionsWithMp3() {
        coEvery { mockSessionRecordDao.getAllSessionsWithMp3() } returns listOf(mockSessionRecordEntity)
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsWithMp3()
        }
        coVerify { mockSessionRecordDao.getAllSessionsWithMp3() }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockSessionRecord),
            (output as Output.Success).result
        )
    }

    @Test
    fun `getAllSessionsWithMp3 failed DAO returned empty result`() {
        coEvery { mockSessionRecordDao.getAllSessionsWithMp3() } returns listOf()
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsWithMp3()
        }
        coVerify { mockSessionRecordDao.getAllSessionsWithMp3() }
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
    fun `getAllSessionsWithMp3 failed with exception`() {
        coEvery { mockSessionRecordDao.getAllSessionsWithMp3() } throws mockException
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsWithMp3()
        }
        coVerify { mockSessionRecordDao.getAllSessionsWithMp3() }
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
    fun getAllSessionsWithoutMp3() {
        coEvery { mockSessionRecordDao.getAllSessionsWithoutMp3() } returns listOf(mockSessionRecordEntity)
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsWithoutMp3()
        }
        coVerify { mockSessionRecordDao.getAllSessionsWithoutMp3() }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockSessionRecord),
            (output as Output.Success).result
        )
    }

    @Test
    fun `getAllSessionsWithoutMp3 failed DAO returned empty result`() {
        coEvery { mockSessionRecordDao.getAllSessionsWithoutMp3() } returns listOf()
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsWithoutMp3()
        }
        coVerify { mockSessionRecordDao.getAllSessionsWithoutMp3() }
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
    fun `getAllSessionsWithoutMp3 failed with exception`() {
        coEvery { mockSessionRecordDao.getAllSessionsWithoutMp3() } throws mockException
        val output = runBlocking {
            sessionRecordRepo.getAllSessionsWithoutMp3()
        }
        coVerify { mockSessionRecordDao.getAllSessionsWithoutMp3() }
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
    fun getSessionsSearch() {
        coEvery { mockSessionRecordDao.getSessionsSearch(any()) } returns listOf(mockSessionRecordEntity)
        val output = runBlocking {
            sessionRecordRepo.getSessionsSearch("search request")
        }
        coVerify { mockSessionRecordDao.getSessionsSearch(any()) }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockSessionRecord),
            (output as Output.Success).result
        )
    }

    @Test
    fun `getSessionsSearch failed DAO returned empty result`() {
        coEvery { mockSessionRecordDao.getSessionsSearch(any()) } returns listOf()
        val output = runBlocking {
            sessionRecordRepo.getSessionsSearch("search request")
        }
        coVerify { mockSessionRecordDao.getSessionsSearch(any()) }
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
    fun `getSessionsSearch failed with exception`() {
        coEvery { mockSessionRecordDao.getSessionsSearch(any()) } throws mockException
        val output = runBlocking {
            sessionRecordRepo.getSessionsSearch("search request")
        }
        coVerify { mockSessionRecordDao.getSessionsSearch(any()) }
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
    fun asyncGetAllSessionsStartTimeAsc() {
        coEvery { mockSessionRecordDao.asyncGetAllSessionsStartTimeAsc() } returns listOf(mockSessionRecordEntity)
        val output = runBlocking {
            sessionRecordRepo.asyncGetAllSessionsStartTimeAsc()
        }
        coVerify { mockSessionRecordDao.asyncGetAllSessionsStartTimeAsc() }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockSessionRecord),
            (output as Output.Success).result
        )
    }

    @Test
    fun `asyncGetAllSessionsStartTimeAsc failed DAO returned empty result`() {
        coEvery { mockSessionRecordDao.asyncGetAllSessionsStartTimeAsc() } returns listOf()
        val output = runBlocking {
            sessionRecordRepo.asyncGetAllSessionsStartTimeAsc()
        }
        coVerify { mockSessionRecordDao.asyncGetAllSessionsStartTimeAsc() }
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
    fun `asyncGetAllSessionsStartTimeAsc failed with exception`() {
        coEvery { mockSessionRecordDao.asyncGetAllSessionsStartTimeAsc() } throws mockException
        val output = runBlocking {
            sessionRecordRepo.asyncGetAllSessionsStartTimeAsc()
        }
        coVerify { mockSessionRecordDao.asyncGetAllSessionsStartTimeAsc() }
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
    fun getLatestRecordedSessionDate() {
        coEvery { mockSessionRecordDao.getMostRecentSessionRecordDate() } returns 73L
        val output = runBlocking {
            sessionRecordRepo.getMostRecentSessionRecordDate()
        }
        coVerify { mockSessionRecordDao.getMostRecentSessionRecordDate() }
        assertTrue(output is Output.Success)
        assertEquals(
            73L,
            (output as Output.Success).result
        )
    }
}
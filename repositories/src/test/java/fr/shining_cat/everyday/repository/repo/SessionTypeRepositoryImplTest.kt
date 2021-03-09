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
import fr.shining_cat.everyday.locale.dao.SessionTypeDao
import fr.shining_cat.everyday.locale.entities.SessionTypeEntity
import fr.shining_cat.everyday.models.SessionType
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.converters.SessionTypeConverter
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

class SessionTypeRepositoryImplTest {

    @MockK
    private lateinit var mockSessionTypeDao: SessionTypeDao

    @MockK
    private lateinit var mockSessionTypeConverter: SessionTypeConverter

    @MockK
    private lateinit var mockSessionType: SessionType

    @MockK
    lateinit var mockSessionTypeEntity: SessionTypeEntity

    @MockK
    lateinit var mockException: Exception

    @MockK
    lateinit var mockThrowable: Throwable

    private lateinit var sessionTypeRepo: SessionTypeRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        assertNotNull(mockSessionTypeDao)
        assertNotNull(mockSessionType)
        assertNotNull(mockSessionTypeEntity)
        sessionTypeRepo = SessionTypeRepositoryImpl(
            mockSessionTypeDao,
            mockSessionTypeConverter
        )
        coEvery { mockSessionTypeConverter.convertModelsToEntities(any()) } returns listOf(mockSessionTypeEntity)
        coEvery { mockSessionTypeConverter.convertModelToEntity(any()) } returns mockSessionTypeEntity
        coEvery { mockSessionTypeConverter.convertEntitiesToModels(any()) } returns listOf(mockSessionType)
        coEvery { mockSessionTypeConverter.convertEntitytoModel(any()) } returns mockSessionType
        coEvery { mockException.cause } returns mockThrowable
    }

    @Test
    fun insert() {
        coEvery { mockSessionTypeDao.insert(any()) } returns arrayOf(1L, 2L, 3L)
        val output = runBlocking {
            sessionTypeRepo.insert(
                listOf(
                    mockSessionType,
                    mockSessionType,
                    mockSessionType
                )
            )
        }
        coVerify { mockSessionTypeConverter.convertModelsToEntities(any()) }
        coVerify { mockSessionTypeDao.insert(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            3,
            (output as Output.Success).result.size
        )
    }

    @Test
    fun `insert failed list size does not match`() {
        coEvery { mockSessionTypeDao.insert(any()) } returns arrayOf(1L, 2L)
        val output = runBlocking {
            sessionTypeRepo.insert(listOf(mockSessionType))
        }
        coVerify { mockSessionTypeConverter.convertModelsToEntities(any()) }
        coVerify { mockSessionTypeDao.insert(any()) }
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
        coEvery { mockSessionTypeDao.insert(any()) } throws mockException
        val output = runBlocking {
            sessionTypeRepo.insert(listOf(mockSessionType))
        }
        coVerify { mockSessionTypeConverter.convertModelsToEntities(any()) }
        coVerify { mockSessionTypeDao.insert(any()) }
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
        coEvery { mockSessionTypeDao.update(any()) } returns 1
        val output = runBlocking {
            sessionTypeRepo.update(mockSessionType)
        }
        coVerify { mockSessionTypeConverter.convertModelToEntity(any()) }
        coVerify { mockSessionTypeDao.update(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            1,
            (output as Output.Success).result
        )
    }

    @Test
    fun `update failed count does not match`() {
        coEvery { mockSessionTypeDao.update(any()) } returns 0
        val output = runBlocking {
            sessionTypeRepo.update(mockSessionType)
        }
        coVerify { mockSessionTypeConverter.convertModelToEntity(any()) }
        coVerify { mockSessionTypeDao.update(any()) }
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
        coEvery { mockSessionTypeDao.update(any()) } throws mockException
        val output = runBlocking {
            sessionTypeRepo.update(mockSessionType)
        }
        coVerify { mockSessionTypeConverter.convertModelToEntity(any()) }
        coVerify { mockSessionTypeDao.update(any()) }
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
    fun deleteSessionType() {
        coEvery { mockSessionTypeDao.delete(any()) } returns 1
        val output = runBlocking {
            sessionTypeRepo.delete(mockSessionType)
        }
        coVerify { mockSessionTypeConverter.convertModelToEntity(any()) }
        coVerify { mockSessionTypeDao.delete(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            1,
            (output as Output.Success).result
        )
    }

    @Test
    fun `delete failed count does not match`() {
        coEvery { mockSessionTypeDao.delete(any()) } returns 0
        val output = runBlocking {
            sessionTypeRepo.delete(mockSessionType)
        }
        coVerify { mockSessionTypeConverter.convertModelToEntity(any()) }
        coVerify { mockSessionTypeDao.delete(any()) }
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
        coEvery { mockSessionTypeDao.delete(any()) } throws mockException
        val output = runBlocking {
            sessionTypeRepo.delete(mockSessionType)
        }
        coVerify { mockSessionTypeConverter.convertModelToEntity(any()) }
        coVerify { mockSessionTypeDao.delete(any()) }
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
    fun getAllSessionTypesLastEditTimeDesc() {
        coEvery { mockSessionTypeDao.getAllSessionTypesLastEditTimeDesc() } returns listOf(mockSessionTypeEntity)
        val output = runBlocking {
            sessionTypeRepo.getAllSessionTypesLastEditTimeDesc()
        }
        coVerify { mockSessionTypeConverter.convertEntitiesToModels(any()) }
        coVerify { mockSessionTypeDao.getAllSessionTypesLastEditTimeDesc() }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockSessionType),
            (output as Output.Success).result
        )
    }

    @Test
    fun `getAllSessionTypesLastEditTimeDesc failed DAO returned empty result`() {
        coEvery { mockSessionTypeDao.getAllSessionTypesLastEditTimeDesc() } returns listOf()
        val output = runBlocking {
            sessionTypeRepo.getAllSessionTypesLastEditTimeDesc()
        }
        coVerify(exactly = 0) { mockSessionTypeConverter.convertEntitiesToModels(any()) }
        coVerify { mockSessionTypeDao.getAllSessionTypesLastEditTimeDesc() }
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
    fun `getAllSessionTypesLastEditTimeDesc failed with exception`() {
        coEvery { mockSessionTypeDao.getAllSessionTypesLastEditTimeDesc() } throws mockException
        val output = runBlocking {
            sessionTypeRepo.getAllSessionTypesLastEditTimeDesc()
        }
        coVerify(exactly = 0) { mockSessionTypeConverter.convertEntitiesToModels(any()) }
        coVerify { mockSessionTypeDao.getAllSessionTypesLastEditTimeDesc() }
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

}
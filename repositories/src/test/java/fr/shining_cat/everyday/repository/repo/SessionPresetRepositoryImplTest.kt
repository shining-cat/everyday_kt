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
import fr.shining_cat.everyday.locale.dao.SessionPresetDao
import fr.shining_cat.everyday.locale.entities.SessionPresetEntity
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.converters.SessionPresetConverter
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

class SessionPresetRepositoryImplTest {

    @MockK
    private lateinit var mockSessionPresetDao: SessionPresetDao

    @MockK
    private lateinit var mockSessionPresetConverter: SessionPresetConverter

    @MockK
    private lateinit var mockSessionPreset: SessionPreset

    @MockK
    lateinit var mockSessionPresetEntity: SessionPresetEntity

    @MockK
    lateinit var mockException: Exception

    @MockK
    lateinit var mockThrowable: Throwable

    private lateinit var sessionPresetRepo: SessionPresetRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        assertNotNull(mockSessionPresetDao)
        assertNotNull(mockSessionPreset)
        assertNotNull(mockSessionPresetEntity)
        sessionPresetRepo = SessionPresetRepositoryImpl(
            mockSessionPresetDao,
            mockSessionPresetConverter
        )
        coEvery { mockSessionPresetConverter.convertModelsToEntities(any()) } returns listOf(mockSessionPresetEntity)
        coEvery { mockSessionPresetConverter.convertEntitiesToModels(any()) } returns listOf(mockSessionPreset)
        coEvery { mockSessionPresetConverter.convertModelToEntity(any()) } returns mockSessionPresetEntity
        coEvery { mockException.cause } returns mockThrowable
    }

    @Test
    fun insert() {
        coEvery { mockSessionPresetDao.insert(any()) } returns arrayOf(1L, 2L, 3L)
        val output = runBlocking {
            sessionPresetRepo.insert(
                listOf(
                    mockSessionPreset,
                    mockSessionPreset,
                    mockSessionPreset
                )
            )
        }
        coVerify { mockSessionPresetConverter.convertModelsToEntities(any()) }
        coVerify { mockSessionPresetDao.insert(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            3,
            (output as Output.Success).result.size
        )
    }

    @Test
    fun `insert failed list size does not match`() {
        coEvery { mockSessionPresetDao.insert(any()) } returns arrayOf(1L, 2L)
        val output = runBlocking {
            sessionPresetRepo.insert(
                listOf(
                    mockSessionPreset,
                    mockSessionPreset,
                    mockSessionPreset
                )
            )
        }
        coVerify { mockSessionPresetDao.insert(any()) }
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
        coEvery { mockSessionPresetDao.insert(any()) } throws mockException
        val output = runBlocking {
            sessionPresetRepo.insert(
                listOf(
                    mockSessionPreset,
                    mockSessionPreset,
                    mockSessionPreset
                )
            )
        }
        coVerify { mockSessionPresetDao.insert(any()) }
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
        coEvery { mockSessionPresetDao.update(any()) } returns 1
        val output = runBlocking {
            sessionPresetRepo.update(mockSessionPreset)
        }
        coVerify { mockSessionPresetConverter.convertModelToEntity(any()) }
        coVerify { mockSessionPresetDao.update(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            1,
            (output as Output.Success).result
        )
    }

    @Test
    fun `update failed count does not match`() {
        coEvery { mockSessionPresetDao.update(any()) } returns 0
        val output = runBlocking {
            sessionPresetRepo.update(mockSessionPreset)
        }
        coVerify { mockSessionPresetConverter.convertModelToEntity(any()) }
        coVerify { mockSessionPresetDao.update(any()) }
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
        coEvery { mockSessionPresetDao.update(any()) } throws mockException
        val output = runBlocking {
            sessionPresetRepo.update(mockSessionPreset)
        }
        coVerify { mockSessionPresetConverter.convertModelToEntity(any()) }
        coVerify { mockSessionPresetDao.update(any()) }
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
    fun deleteSessionPreset() {
        coEvery { mockSessionPresetDao.delete(any()) } returns 1
        val output = runBlocking {
            sessionPresetRepo.delete(mockSessionPreset)
        }
        coVerify { mockSessionPresetConverter.convertModelToEntity(any()) }
        coVerify { mockSessionPresetDao.delete(any()) }
        assertTrue(output is Output.Success)
        assertEquals(
            1,
            (output as Output.Success).result
        )
    }

    @Test
    fun `delete failed count does not match`() {
        coEvery { mockSessionPresetDao.delete(any()) } returns 0
        val output = runBlocking {
            sessionPresetRepo.delete(mockSessionPreset)
        }
        coVerify { mockSessionPresetConverter.convertModelToEntity(any()) }
        coVerify { mockSessionPresetDao.delete(any()) }
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
        coEvery { mockSessionPresetDao.delete(any()) } throws mockException
        val output = runBlocking {
            sessionPresetRepo.delete(mockSessionPreset)
        }
        coVerify { mockSessionPresetConverter.convertModelToEntity(any()) }
        coVerify { mockSessionPresetDao.delete(any()) }
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
    fun getAllSessionPresetsLastEditTimeDesc() {
        coEvery { mockSessionPresetDao.getAllSessionPresetsLastEditTimeDesc() } returns listOf(mockSessionPresetEntity)
        val output = runBlocking {
            sessionPresetRepo.getAllSessionPresetsLastEditTimeDesc()
        }
        coVerify { mockSessionPresetConverter.convertEntitiesToModels(any()) }
        coVerify { mockSessionPresetDao.getAllSessionPresetsLastEditTimeDesc() }
        assertTrue(output is Output.Success)
        assertEquals(
            listOf(mockSessionPreset),
            (output as Output.Success).result
        )
    }

    @Test
    fun `getAllSessionPresetsLastEditTimeDesc failed DAO returned empty result`() {
        coEvery { mockSessionPresetDao.getAllSessionPresetsLastEditTimeDesc() } returns listOf()
        val output = runBlocking {
            sessionPresetRepo.getAllSessionPresetsLastEditTimeDesc()
        }
        coVerify { mockSessionPresetDao.getAllSessionPresetsLastEditTimeDesc() }
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
    fun `getAllSessionPresetsLastEditTimeDesc failed with exception`() {
        coEvery { mockSessionPresetDao.getAllSessionPresetsLastEditTimeDesc() } throws mockException
        val output = runBlocking {
            sessionPresetRepo.getAllSessionPresetsLastEditTimeDesc()
        }
        coVerify { mockSessionPresetDao.getAllSessionPresetsLastEditTimeDesc() }
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
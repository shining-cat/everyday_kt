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

import fr.shining_cat.everyday.locale.dao.SessionRecordDao
import fr.shining_cat.everyday.locale.entities.SessionRecordEntity
import fr.shining_cat.everyday.models.sessionrecord.SessionRecord
import fr.shining_cat.everyday.repository.converters.SessionRecordConverter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
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

    private lateinit var sessionRecordRepo: SessionRecordRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        assertNotNull(mockSessionRecordDao)
        assertNotNull(mockSessionRecord)
        assertNotNull(mockSessionRecordEntity)
        sessionRecordRepo =
            SessionRecordRepositoryImpl(
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
        coEvery { mockSessionRecordDao.insert(any()) } returns arrayOf(1, 2, 3)
        coEvery { mockSessionRecordDao.update(any()) } returns 3
        coEvery { mockSessionRecordDao.delete(any()) } returns 3
        coEvery { mockSessionRecordDao.asyncGetAllSessionsStartTimeAsc() } returns listOf(
            mockSessionRecordEntity
        )
        coEvery { mockSessionRecordDao.getAllSessionsStartTimeAsc() } returns listOf(
            mockSessionRecordEntity
        )
        coEvery { mockSessionRecordDao.getAllSessionsStartTimeDesc() } returns listOf(
            mockSessionRecordEntity
        )
        coEvery { mockSessionRecordDao.getAllSessionsDurationAsc() } returns listOf(
            mockSessionRecordEntity
        )
        coEvery { mockSessionRecordDao.getAllSessionsDurationDesc() } returns listOf(
            mockSessionRecordEntity
        )
        coEvery { mockSessionRecordDao.getAllSessionsWithMp3() } returns listOf(
            mockSessionRecordEntity
        )
        coEvery { mockSessionRecordDao.getAllSessionsWithoutMp3() } returns listOf(
            mockSessionRecordEntity
        )
        coEvery { mockSessionRecordDao.getSessionsSearch(any()) } returns listOf(
            mockSessionRecordEntity
        )
    }

    @Test
    fun insert() {
        runBlocking {
            sessionRecordRepo.insert(listOf(mockSessionRecord))
        }
        coVerify { mockSessionRecordConverter.convertModelsToEntities(any()) }
        coVerify { mockSessionRecordDao.insert(listOf(mockSessionRecordEntity)) }
    }

    @Test
    fun update() {
        runBlocking {
            sessionRecordRepo.update(mockSessionRecord)
        }
        coVerify { mockSessionRecordConverter.convertModelToEntity(any()) }
        coVerify { mockSessionRecordDao.update(mockSessionRecordEntity) }
    }

    @Test
    fun deleteSession() {
        runBlocking {
            sessionRecordRepo.delete(mockSessionRecord)
        }
        coVerify { mockSessionRecordConverter.convertModelToEntity(any()) }
        coVerify { mockSessionRecordDao.delete(mockSessionRecordEntity) }
    }

    @Test
    fun deleteAllSessions() {
        runBlocking {
            sessionRecordRepo.deleteAllSessions()
        }
        coVerify { mockSessionRecordDao.deleteAllSessions() }
    }

    @Test
    fun getAllSessionsStartTimeAsc() {
        runBlocking {
            sessionRecordRepo.getAllSessionsStartTimeAsc()
        }
        coVerify { mockSessionRecordDao.getAllSessionsStartTimeAsc() }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
    }

    @Test
    fun getAllSessionsStartTimeDesc() {
        runBlocking {
            sessionRecordRepo.getAllSessionsStartTimeDesc()
        }
        coVerify { mockSessionRecordDao.getAllSessionsStartTimeDesc() }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
    }

    @Test
    fun getAllSessionsDurationAsc() {
        runBlocking {
            sessionRecordRepo.getAllSessionsDurationAsc()
        }
        coVerify { mockSessionRecordDao.getAllSessionsDurationAsc() }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
    }

    @Test
    fun getAllSessionsDurationDesc() {
        runBlocking {
            sessionRecordRepo.getAllSessionsDurationDesc()
        }
        coVerify { mockSessionRecordDao.getAllSessionsDurationDesc() }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
    }

    @Test
    fun `try name`() {
        runBlocking {
            sessionRecordRepo.getAllSessionsWithMp3()
        }
        coVerify { mockSessionRecordDao.getAllSessionsWithMp3() }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
    }

    @Test
    fun getAllSessionsWithMp3() {
        runBlocking {
            sessionRecordRepo.getAllSessionsWithMp3()
        }
        coVerify { mockSessionRecordDao.getAllSessionsWithMp3() }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
    }

    @Test
    fun getAllSessionsWithoutMp3() {
        runBlocking {
            sessionRecordRepo.getAllSessionsWithoutMp3()
        }
        coVerify { mockSessionRecordDao.getAllSessionsWithoutMp3() }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
    }

    @Test
    fun getSessionsSearch() {
        runBlocking {
            sessionRecordRepo.getSessionsSearch("search request")
        }
        coVerify { mockSessionRecordDao.getSessionsSearch(any()) }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
    }

    @Test
    fun asyncGetAllSessionsStartTimeAsc() {
        runBlocking {
            sessionRecordRepo.asyncGetAllSessionsStartTimeAsc()
        }
        coVerify { mockSessionRecordDao.asyncGetAllSessionsStartTimeAsc() }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
    }

    @Test
    fun getLatestRecordedSessionDate() {
        runBlocking {
            sessionRecordRepo.getMostRecentSessionRecordDate()
        }
        coVerify { mockSessionRecordDao.getMostRecentSessionRecordDate() }
    }
}
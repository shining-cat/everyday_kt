package fr.shining_cat.everyday.repository.repo

import fr.shining_cat.everyday.locale.dao.SessionRecordDao
import fr.shining_cat.everyday.locale.entities.SessionRecordEntity
import fr.shining_cat.everyday.models.sessionrecord.SessionRecord
import fr.shining_cat.everyday.repository.converter.SessionRecordConverter
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
        coVerify { mockSessionRecordDao.insert(any()) }
    }

    @Test
    fun update() {
        runBlocking {
            sessionRecordRepo.update(mockSessionRecord)
        }
        coVerify { mockSessionRecordConverter.convertModelToEntity(any()) }
        coVerify { mockSessionRecordDao.update(any()) }
    }

    @Test
    fun deleteSession() {
        runBlocking {
            sessionRecordRepo.delete(mockSessionRecord)
        }
        coVerify { mockSessionRecordConverter.convertModelToEntity(any()) }
        coVerify { mockSessionRecordDao.delete(any()) }
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
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        coVerify { mockSessionRecordDao.getAllSessionsStartTimeAsc() }
    }

    @Test
    fun getAllSessionsStartTimeDesc() {
        runBlocking {
            sessionRecordRepo.getAllSessionsStartTimeDesc()
        }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        coVerify { mockSessionRecordDao.getAllSessionsStartTimeDesc() }
    }

    @Test
    fun getAllSessionsDurationAsc() {
        runBlocking {
            sessionRecordRepo.getAllSessionsDurationAsc()
        }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        coVerify { mockSessionRecordDao.getAllSessionsDurationAsc() }
    }

    @Test
    fun getAllSessionsDurationDesc() {
        runBlocking {
            sessionRecordRepo.getAllSessionsDurationDesc()
        }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        coVerify { mockSessionRecordDao.getAllSessionsDurationDesc() }
    }


    @Test
    fun `try name`() {
        runBlocking {
            sessionRecordRepo.getAllSessionsWithMp3()
        }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        coVerify { mockSessionRecordDao.getAllSessionsWithMp3() }
    }

    @Test
    fun getAllSessionsWithMp3() {
        runBlocking {
            sessionRecordRepo.getAllSessionsWithMp3()
        }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        coVerify { mockSessionRecordDao.getAllSessionsWithMp3() }
    }

    @Test
    fun getAllSessionsWithoutMp3() {
        runBlocking {
            sessionRecordRepo.getAllSessionsWithoutMp3()
        }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        coVerify { mockSessionRecordDao.getAllSessionsWithoutMp3() }
    }

    @Test
    fun getSessionsSearch() {
        runBlocking {
            sessionRecordRepo.getSessionsSearch("search request")
        }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        coVerify { mockSessionRecordDao.getSessionsSearch(any()) }
    }

    @Test
    fun asyncGetAllSessionsStartTimeAsc() {
        runBlocking {
            sessionRecordRepo.asyncGetAllSessionsStartTimeAsc()
        }
        coVerify { mockSessionRecordConverter.convertEntitiesToModels(any()) }
        coVerify { mockSessionRecordDao.asyncGetAllSessionsStartTimeAsc() }
    }

    @Test
    fun getLatestRecordedSessionDate() {
        runBlocking {
            sessionRecordRepo.getMostRecentSessionRecordDate()
        }
        coVerify { mockSessionRecordDao.getMostRecentSessionRecordDate() }
    }
}
package fr.shining_cat.everyday.repository

import androidx.lifecycle.LiveData
import fr.shining_cat.everyday.locale.dao.SessionRecordDao
import fr.shining_cat.everyday.locale.entities.SessionRecordEntity
import fr.shining_cat.everyday.repository.converter.SessionRecordConverter
import fr.shining_cat.everyday.repository.repo.SessionRepository
import fr.shining_cat.everyday.repository.repo.SessionRepositoryImpl
import fr.shining_cat.everyday.testutils.AbstractBaseTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SessionRepositoryImplTest: AbstractBaseTest() {

    @Mock
    private lateinit var mockSessionRecordDao: SessionRecordDao
    @Mock
    private lateinit var mockSessionRecordConverter: SessionRecordConverter

    @Mock
    lateinit var sessionRecordEntityLive: LiveData<SessionRecordEntity?>

    @Mock
    lateinit var sessionEntitiesLive: LiveData<List<SessionRecordEntity>?>

    private lateinit var sessionRepo: SessionRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        assertNotNull(mockSessionRecordDao)
        assertNotNull(sessionRecordEntityLive)
        assertNotNull(sessionEntitiesLive)
        sessionRepo =
            SessionRepositoryImpl(
                mockSessionRecordDao,
                mockSessionRecordConverter
            )
        Mockito.`when`(mockSessionRecordDao.getAllSessionsStartTimeAsc()).thenReturn(sessionEntitiesLive)
        Mockito.`when`(mockSessionRecordDao.getAllSessionsStartTimeDesc()).thenReturn(sessionEntitiesLive)
        Mockito.`when`(mockSessionRecordDao.getAllSessionsDurationAsc()).thenReturn(sessionEntitiesLive)
        Mockito.`when`(mockSessionRecordDao.getAllSessionsDurationDesc()).thenReturn(sessionEntitiesLive)
        Mockito.`when`(mockSessionRecordDao.getAllSessionsWithMp3()).thenReturn(sessionEntitiesLive)
        Mockito.`when`(mockSessionRecordDao.getAllSessionsWithoutMp3()).thenReturn(sessionEntitiesLive)
        Mockito.`when`(mockSessionRecordDao.getSessionsSearch(anyString())).thenReturn(sessionEntitiesLive)
    }
    /**
     * See [Memory leak in mockito-inline...](https://github.com/mockito/mockito/issues/1614)
     */
    @After
    fun clearMocks() {
        Mockito.framework().clearInlineMocks()
    }
    @Test
    fun tests(){
        Assert.fail("TODO: write correct tests")
    }
//    @Test
//    fun insert() {
//        runBlocking {
//            sessionRepo.insert(generateSession())
//            Mockito.verify(mockSessionRecordDao).insert(any())
//        }
//    }
//
//    @Test
//    fun insertMultiple() {
//        runBlocking {
//            sessionRepo.insertMultiple(generateSessions(23))
//            Mockito.verify(mockSessionRecordDao).insertMultiple(any())
//        }
//    }
//
//    @Test
//    fun updateSession() {
//        runBlocking {
//            sessionRepo.updateSession(generateSession())
//            Mockito.verify(mockSessionRecordDao).updateSession(any())
//        }
//    }
//
//    @Test
//    fun deleteSession() {
//        runBlocking {
//            sessionRepo.deleteSession(generateSession())
//            Mockito.verify(mockSessionRecordDao).deleteSession(any())
//        }
//    }
//
//    @Test
//    fun deleteAllSessions() {
//        runBlocking {
//            sessionRepo.deleteAllSessions()
//            Mockito.verify(mockSessionRecordDao).deleteAllSessions()
//        }
//    }
//
//    @Test
//    fun getAllSessionsStartTimeAsc() {
//        runBlocking {
//            sessionRepo.getAllSessionsStartTimeAsc()
//            Mockito.verify(mockSessionRecordDao).getAllSessionsStartTimeAsc()
//        }
//    }
//
//    @Test
//    fun getAllSessionsStartTimeDesc() {
//        runBlocking {
//            sessionRepo.getAllSessionsStartTimeDesc()
//            Mockito.verify(mockSessionRecordDao).getAllSessionsStartTimeDesc()
//        }
//    }
//
//    @Test
//    fun getAllSessionsDurationAsc() {
//        runBlocking {
//            sessionRepo.getAllSessionsDurationAsc()
//            Mockito.verify(mockSessionRecordDao).getAllSessionsDurationAsc()
//        }
//    }
//
//    @Test
//    fun getAllSessionsDurationDesc() {
//        runBlocking {
//            sessionRepo.getAllSessionsDurationDesc()
//            Mockito.verify(mockSessionRecordDao).getAllSessionsDurationDesc()
//        }
//    }
//
//
//    @Test
//    fun `try name`() {
//        runBlocking {
//            sessionRepo.getAllSessionsWithMp3()
//            Mockito.verify(mockSessionRecordDao).getAllSessionsWithMp3()
//        }
//    }
//
//    @Test
//    fun getAllSessionsWithMp3() {
//        runBlocking {
//            sessionRepo.getAllSessionsWithMp3()
//            Mockito.verify(mockSessionRecordDao).getAllSessionsWithMp3()
//        }
//    }
//
//    @Test
//    fun getAllSessionsWithoutMp3() {
//        runBlocking {
//            sessionRepo.getAllSessionsWithoutMp3()
//            Mockito.verify(mockSessionRecordDao).getAllSessionsWithoutMp3()
//        }
//    }
//
//    @Test
//    fun getSessionsSearch() {
//        runBlocking {
//            sessionRepo.getSessionsSearch("search request")
//            Mockito.verify(mockSessionRecordDao).getSessionsSearch(anyString())
//        }
//    }
//
//    @Test
//    fun getAllSessionsNotLiveStartTimeAsc() {
//        runBlocking {
//            sessionRepo.getAllSessionsNotLiveStartTimeAsc()
//            Mockito.verify(mockSessionRecordDao).getAllSessionsNotLiveStartTimeAsc()
//        }
//    }
//
//    @Test
//    fun getLatestRecordedSessionDate() {
//        runBlocking {
//            sessionRepo.getLatestRecordedSessionDate()
//            Mockito.verify(mockSessionRecordDao).getLatestRecordedSessionDate()
//        }
//    }
//
//    fun generateSessions(numberOfSessions: Int = 1): List<SessionRecord> {
//        val returnList = mutableListOf<SessionRecord>()
//        var yearStart = 1980
//        var yearEnd = 1981
//        for (i in 0 until numberOfSessions) {
//            returnList.add(
//                generateSession(
//                    startMood = MoodModelTestUtils.generateMood(
//                        yearStart,
//                        5,
//                        2,
//                        15,
//                        27,
//                        54,
//                        MoodValue.NOT_SET,
//                        MoodValue.BEST,
//                        MoodValue.BAD,
//                        MoodValue.GOOD
//                    ),
//                    endMood = MoodModelTestUtils.generateMood(
//                        yearEnd,
//                        6,
//                        3,
//                        17,
//                        45,
//                        3,
//                        MoodValue.NOT_SET,
//                        MoodValue.BEST,
//                        MoodValue.BAD,
//                        MoodValue.GOOD
//                    )
//                )
//            )
//            yearStart++
//            yearEnd++
//        }
//        return returnList
//    }
//
//    fun generateSession(
//        id: Long = -1L,
//        startMood: Mood = MoodModelTestUtils.generateMood(
//            1980, 5, 2, 15, 27, 54,
//            MoodValue.NOT_SET,
//            MoodValue.BEST,
//            MoodValue.BAD,
//            MoodValue.GOOD
//        ),
//        endMood: Mood = MoodModelTestUtils.generateMood(1982, 6, 3, 17, 45, 3,
//            MoodValue.NOT_SET,
//            MoodValue.BEST,
//            MoodValue.BAD,
//            MoodValue.GOOD),
//        notes: String = "generateSession default notes",
//        realDuration: Long = 123456L,
//        pausesCount: Int = 0,
//        realDurationVsPlanned: RealDurationVsPlanned = RealDurationVsPlanned.EQUAL,
//        guideMp3: String = "generateSession default guideMp3"
//    ): SessionRecord {
//        if (id == -1L) {
//            return SessionRecord(
//                startMood = startMood,
//                endMood = endMood,
//                notes = notes,
//                realDuration = realDuration,
//                pausesCount = pausesCount,
//                realDurationVsPlanned = realDurationVsPlanned,
//                guideMp3 = guideMp3
//            )
//        } else {
//            return SessionRecord(
//                id = id,
//                startMood = startMood,
//                endMood = endMood,
//                notes = notes,
//                realDuration = realDuration,
//                pausesCount = pausesCount,
//                realDurationVsPlanned = realDurationVsPlanned,
//                guideMp3 = guideMp3
//            )
//        }
//    }
}
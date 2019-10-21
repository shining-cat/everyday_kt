package fr.shining_cat.everyday.localdata.dao


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import fr.shining_cat.everyday.localdata.EveryDayRoomDatabase
import fr.shining_cat.everyday.localdata.dto.RewardDTO
import fr.shining_cat.everyday.testutils.dto.RewardDTOTestUtils
import fr.shining_cat.everyday.testutils.extensions.getValueBlocking
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class RewardDaoTest {

    //set the testing environment to use Main thread instead of background one
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private var rewardDao: RewardDao? = null

//    @Before
    private fun setupEmptyTable(){
        tearDown()
        EveryDayRoomDatabase.TEST_MODE = true
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        rewardDao = EveryDayRoomDatabase.getInstance(appContext).rewardDao()
        emptyTableAndCheck()
    }

    private fun emptyTableAndCheck(){
        runBlocking {
            rewardDao?.deleteAllRewards()
        }
        checkTotalCountIs(0)
    }

    private fun checkTotalCountIs(expectedCount: Int){
        val count = runBlocking {
            rewardDao?.getNumberOfRows()
        }
        assertEquals(expectedCount, count)
    }

//    @After
    private fun tearDown() {
        EveryDayRoomDatabase.closeAndDestroy()
    }

////////////////////////////////////////////////////////////////
    @Test
    fun insertRewardTest() {
        setupEmptyTable()
        val rewardDTO = RewardDTOTestUtils.generateReward(desiredId = 25)
        val rewardDTOTestID = runBlocking {
            rewardDao?.insert(rewardDTO)
        }
        assertEquals(25L, rewardDTOTestID)
        checkTotalCountIs(1)
        tearDown()
    }

    @Test
    fun insertMultiRewardTest(){
        setupEmptyTable()
        val rewardsToInsertList = RewardDTOTestUtils.generateRewards(20)
        val insertedIds = runBlocking {
            rewardDao?.insert(rewardsToInsertList)
        }
        assertEquals(20, insertedIds?.size)
        assertNotNull(insertedIds)
        if(insertedIds != null) {
            var i = 1L
            for (id in insertedIds) {
                assertEquals(i, id)
                i++
            }
        }
        checkTotalCountIs(20)
        tearDown()
    }

    @Test
    fun deleteOneRewardTest(){
        setupEmptyTable()
        val rewardDTOToDeleteTest = RewardDTOTestUtils.generateReward(desiredId = 25)
        val rewardsToInsertList = mutableListOf<RewardDTO>()
        rewardsToInsertList.add(rewardDTOToDeleteTest)
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(50))
        runBlocking {
            rewardDao?.insert(rewardsToInsertList)
        }
        checkTotalCountIs(51)
        //
        val countDeleted = runBlocking {
            rewardDao?.deleteReward(rewardDTOToDeleteTest)
        }
        assertEquals(1, countDeleted)
        //
        checkTotalCountIs(50)
        tearDown()
    }

    @Test
    fun deleteMultiRewardTest(){
        setupEmptyTable()
        //insert the test-subject list of items
        val rewardsToDeleteList = RewardDTOTestUtils.generateRewards(17)
        //insert and collect the ids
        val insertedIds = runBlocking {
            rewardDao?.insert(rewardsToDeleteList)
        }
        assertNotNull(insertedIds)
        if(insertedIds != null) {
            assertEquals(17, insertedIds.size)
            var i = 1L //first index generated in DB will be 1 and not 0
            for (id in insertedIds) {
                assertEquals(i, id)
                rewardsToDeleteList[i.toInt()-1].id = id //update test-subject list with ids from insertion
                i++
            }
        }
        checkTotalCountIs(17)
        //insert some more data
        runBlocking {
            rewardDao?.insert(RewardDTOTestUtils.generateRewards(29))
        }
        checkTotalCountIs(46)
        //delete test-subject list of items
        val numberOfDeletedRows = runBlocking {
            rewardDao?.deleteReward(rewardsToDeleteList)
        }
        checkTotalCountIs(29)
        assertEquals(17, numberOfDeletedRows)
        //
        tearDown()
    }

    @Test
    fun deleteAllRewardTest() {
        setupEmptyTable()
        runBlocking {
            rewardDao?.insert(RewardDTOTestUtils.generateRewards(73))
        }
        checkTotalCountIs(73)
        val numberOfDeletedRows = runBlocking {
            rewardDao?.deleteAllRewards()
        }
        assertEquals(73, numberOfDeletedRows)
        checkTotalCountIs(0)
        tearDown()
    }

    @Test
    fun getOneRewardTest(){
        setupEmptyTable()
        val rewardDTO = RewardDTOTestUtils.generateReward(
                                                desiredLevel = 3,
                                                active = true,
                                                escaped = false,
                                                desiredId = 83,
                                                yearAcquired = 1989,
                                                monthAcquired = 3,
                                                dayAcquired = 7,
                                                yearEscaped = 1997,
                                                monthEscaped = 8,
                                                dayEscaped = 13,
                                                desiredName = "getOneRewardTest is my name",
                                                desiredLegsColor = "getOneRewardTest legs color",
                                                desiredBodyColor = "getOneRewardTest body color",
                                                desiredArmsColor = "getOneRewardTest arms color")
        val rewardCode = rewardDTO.code
        val rewardDtoInsertedTestID = runBlocking {
            rewardDao?.insert(rewardDTO)
        }
        assertNotNull(rewardDtoInsertedTestID)
        assertEquals(83L, rewardDtoInsertedTestID)
        //
        runBlocking {
            rewardDao?.insert(RewardDTOTestUtils.generateRewards(13))
        }
        checkTotalCountIs(14)
        //
        if(rewardDtoInsertedTestID != null) {
            val rewardDtoExtractedLive = rewardDao?.getRewardLive(rewardDtoInsertedTestID)
            assertNotNull(rewardDtoExtractedLive)
            if(rewardDtoExtractedLive != null) {
                val rewardDtoExtracted = rewardDtoExtractedLive.getValueBlocking()
                assertNotNull(rewardDtoExtracted)
                if (rewardDtoExtracted != null) {
                    assertEquals(83, rewardDtoExtracted.id)
                    assertEquals(3, rewardDtoExtracted.level)
                    assertEquals(rewardCode, rewardDtoExtracted.code)
                    assertEquals(GregorianCalendar(1989, 3, 7).timeInMillis, rewardDtoExtracted.acquisitionDate)
                    assertEquals(GregorianCalendar(1997, 8, 13).timeInMillis, rewardDtoExtracted.escapingDate)
                    assertEquals(true, rewardDtoExtracted.isActive)
                    assertEquals(false, rewardDtoExtracted.isEscaped)
                    assertEquals("getOneRewardTest is my name", rewardDtoExtracted.name)
                    assertEquals("getOneRewardTest legs color", rewardDtoExtracted.legsColor)
                    assertEquals("getOneRewardTest body color", rewardDtoExtracted.bodyColor)
                    assertEquals("getOneRewardTest arms color", rewardDtoExtracted.armsColor)
                }
            }
        }
        tearDown()
    }

    @Test
    fun updateOneRewardTest(){
        setupEmptyTable()
        val rewardDTO = RewardDTOTestUtils.generateReward(
            desiredLevel = 5,
            active = true,
            escaped = false,
            desiredId = 43,
            yearAcquired = 1987,
            monthAcquired = 2,
            dayAcquired = 9,
            yearEscaped = 1995,
            monthEscaped = 9,
            dayEscaped = 11,
            desiredName = "updateOneRewardTest is my name",
            desiredLegsColor = "updateOneRewardTest legs color",
            desiredBodyColor = "updateOneRewardTest body color",
            desiredArmsColor = "updateOneRewardTest arms color")
        val rewardCode = rewardDTO.code
        val rewardDtoInsertedTestID = runBlocking {
            rewardDao?.insert(rewardDTO)
        }
        assertNotNull(rewardDtoInsertedTestID)
        assertEquals(43L, rewardDtoInsertedTestID)

        runBlocking {
            rewardDao?.insert(RewardDTOTestUtils.generateRewards(53))

        }
        checkTotalCountIs(54)
        //
        rewardDTO.acquisitionDate = GregorianCalendar(2019, 6, 16).timeInMillis
        rewardDTO.escapingDate = GregorianCalendar(2020, 5, 22).timeInMillis
        rewardDTO.isActive = false
        rewardDTO.isEscaped = true
        rewardDTO.name = "I have updated my name"
        rewardDTO.legsColor = "#00FF000000"
        rewardDTO.bodyColor = "#0000FF00"
        rewardDTO.armsColor = "#FFFFFFFF"
        //
        val numberOfUpdatedItems = runBlocking {
            rewardDao?.updateReward(rewardDTO)
        }
        assertEquals(1, numberOfUpdatedItems)
        checkTotalCountIs(54)
        val rewardDtoUpdatedLive = rewardDao?.getRewardLive(rewardDTO.id)
        assertNotNull(rewardDtoUpdatedLive)
        if(rewardDtoUpdatedLive != null) {
            val rewardDtoUpdated = rewardDtoUpdatedLive.getValueBlocking()
            assertNotNull(rewardDtoUpdated)
            if (rewardDtoUpdated != null) {
                assertEquals(43, rewardDtoUpdated.id)
                assertEquals(5, rewardDtoUpdated.level)
                assertEquals(rewardCode, rewardDtoUpdated.code)
                assertEquals(GregorianCalendar(2019, 6, 16).timeInMillis, rewardDtoUpdated.acquisitionDate)
                assertEquals(GregorianCalendar(2020, 5, 22).timeInMillis, rewardDtoUpdated.escapingDate)
                assertEquals(false, rewardDtoUpdated.isActive)
                assertEquals(true, rewardDtoUpdated.isEscaped)
                assertEquals("I have updated my name", rewardDtoUpdated.name)
                assertEquals("#00FF000000", rewardDtoUpdated.legsColor)
                assertEquals("#0000FF00", rewardDtoUpdated.bodyColor)
                assertEquals("#FFFFFFFF", rewardDtoUpdated.armsColor)
            }
        }
        //
        tearDown()
    }

    @Test
    fun updateMultipleRewardsTest(){
        setupEmptyTable()
        //
        val rewardsToTestUpdateList = listOf(
            RewardDTOTestUtils.generateReward(desiredId = 13),
            RewardDTOTestUtils.generateReward(desiredId = 17),
            RewardDTOTestUtils.generateReward(desiredId = 23),
            RewardDTOTestUtils.generateReward(desiredId = 37))
        val insertedIds = runBlocking {
            rewardDao?.insert(rewardsToTestUpdateList)
        }
        assertEquals(4, insertedIds?.size)
        //
        rewardsToTestUpdateList.get(0).acquisitionDate = GregorianCalendar(2019, 1, 16).timeInMillis
        rewardsToTestUpdateList.get(0).escapingDate = GregorianCalendar(2020, 1, 22).timeInMillis
        rewardsToTestUpdateList.get(0).isActive = false
        rewardsToTestUpdateList.get(0).isEscaped = true
        rewardsToTestUpdateList.get(0).name = "name updated 0"
        rewardsToTestUpdateList.get(0).legsColor = "legsColor updated 0"
        rewardsToTestUpdateList.get(0).bodyColor = "bodyColor updated 0"
        rewardsToTestUpdateList.get(0).armsColor = "armsColor updated 0"
        //
        rewardsToTestUpdateList.get(1).acquisitionDate = GregorianCalendar(2019, 2, 16).timeInMillis
        rewardsToTestUpdateList.get(1).escapingDate = GregorianCalendar(2020, 2, 22).timeInMillis
        rewardsToTestUpdateList.get(1).isActive = true
        rewardsToTestUpdateList.get(1).isEscaped = false
        rewardsToTestUpdateList.get(1).name = "name updated 1"
        rewardsToTestUpdateList.get(1).legsColor = "legsColor updated 1"
        rewardsToTestUpdateList.get(1).bodyColor = "bodyColor updated 1"
        rewardsToTestUpdateList.get(1).armsColor = "armsColor updated 1"
        //
        rewardsToTestUpdateList.get(2).acquisitionDate = GregorianCalendar(2019, 3, 16).timeInMillis
        rewardsToTestUpdateList.get(2).escapingDate = GregorianCalendar(2020, 3, 22).timeInMillis
        rewardsToTestUpdateList.get(2).isActive = true
        rewardsToTestUpdateList.get(2).isEscaped = true
        rewardsToTestUpdateList.get(2).name = "name updated 2"
        rewardsToTestUpdateList.get(2).legsColor = "legsColor updated 2"
        rewardsToTestUpdateList.get(2).bodyColor = "bodyColor updated 2"
        rewardsToTestUpdateList.get(2).armsColor = "armsColor updated 2"
        //
        rewardsToTestUpdateList.get(3).acquisitionDate = GregorianCalendar(2019, 4, 16).timeInMillis
        rewardsToTestUpdateList.get(3).escapingDate = GregorianCalendar(2020, 4, 22).timeInMillis
        rewardsToTestUpdateList.get(3).isActive = false
        rewardsToTestUpdateList.get(3).isEscaped = false
        rewardsToTestUpdateList.get(3).name = "name updated 3"
        rewardsToTestUpdateList.get(3).legsColor = "legsColor updated 3"
        rewardsToTestUpdateList.get(3).bodyColor = "bodyColor updated 3"
        rewardsToTestUpdateList.get(3).armsColor = "armsColor updated 3"
        //
        val numberOfUpdatedItems = runBlocking {
            rewardDao?.updateRewards(rewardsToTestUpdateList)
        }
        assertEquals(4, numberOfUpdatedItems)
        //
        var rewardDtoUpdatedLive = rewardDao?.getRewardLive(rewardsToTestUpdateList.get(0).id)
        assertNotNull(rewardDtoUpdatedLive)
        if(rewardDtoUpdatedLive != null) {
            val rewardDtoUpdated = rewardDtoUpdatedLive.getValueBlocking()
            assertNotNull(rewardDtoUpdated)
            if (rewardDtoUpdated != null) {
                assertEquals(rewardsToTestUpdateList.get(0).id, rewardDtoUpdated.id)
                assertEquals(rewardsToTestUpdateList.get(0).level, rewardDtoUpdated.level)
                assertEquals(rewardsToTestUpdateList.get(0).code, rewardDtoUpdated.code)
                assertEquals(GregorianCalendar(2019, 1, 16).timeInMillis, rewardDtoUpdated.acquisitionDate)
                assertEquals(GregorianCalendar(2020, 1, 22).timeInMillis, rewardDtoUpdated.escapingDate)
                assertEquals(false, rewardDtoUpdated.isActive)
                assertEquals(true, rewardDtoUpdated.isEscaped)
                assertEquals("name updated 0", rewardDtoUpdated.name)
                assertEquals("legsColor updated 0", rewardDtoUpdated.legsColor)
                assertEquals("bodyColor updated 0", rewardDtoUpdated.bodyColor)
                assertEquals("armsColor updated 0", rewardDtoUpdated.armsColor)
            }
        }
        //
        rewardDtoUpdatedLive = rewardDao?.getRewardLive(rewardsToTestUpdateList.get(1).id)
        assertNotNull(rewardDtoUpdatedLive)
        if(rewardDtoUpdatedLive != null) {
            val rewardDtoUpdated = rewardDtoUpdatedLive.getValueBlocking()
            assertNotNull(rewardDtoUpdated)
            if (rewardDtoUpdated != null) {
                assertEquals(rewardsToTestUpdateList.get(1).id, rewardDtoUpdated.id)
                assertEquals(rewardsToTestUpdateList.get(1).level, rewardDtoUpdated.level)
                assertEquals(rewardsToTestUpdateList.get(1).code, rewardDtoUpdated.code)
                assertEquals(GregorianCalendar(2019, 2, 16).timeInMillis, rewardDtoUpdated.acquisitionDate)
                assertEquals(GregorianCalendar(2020, 2, 22).timeInMillis, rewardDtoUpdated.escapingDate)
                assertEquals(true, rewardDtoUpdated.isActive)
                assertEquals(false, rewardDtoUpdated.isEscaped)
                assertEquals("name updated 1", rewardDtoUpdated.name)
                assertEquals("legsColor updated 1", rewardDtoUpdated.legsColor)
                assertEquals("bodyColor updated 1", rewardDtoUpdated.bodyColor)
                assertEquals("armsColor updated 1", rewardDtoUpdated.armsColor)
            }
        }
        //
        rewardDtoUpdatedLive = rewardDao?.getRewardLive(rewardsToTestUpdateList.get(2).id)
        assertNotNull(rewardDtoUpdatedLive)
        if(rewardDtoUpdatedLive != null) {
            val rewardDtoUpdated = rewardDtoUpdatedLive.getValueBlocking()
            assertNotNull(rewardDtoUpdated)
            if (rewardDtoUpdated != null) {
                assertEquals(rewardsToTestUpdateList.get(2).id, rewardDtoUpdated.id)
                assertEquals(rewardsToTestUpdateList.get(2).level, rewardDtoUpdated.level)
                assertEquals(rewardsToTestUpdateList.get(2).code, rewardDtoUpdated.code)
                assertEquals(GregorianCalendar(2019, 3, 16).timeInMillis, rewardDtoUpdated.acquisitionDate)
                assertEquals(GregorianCalendar(2020, 3, 22).timeInMillis, rewardDtoUpdated.escapingDate)
                assertEquals(true, rewardDtoUpdated.isActive)
                assertEquals(true, rewardDtoUpdated.isEscaped)
                assertEquals("name updated 2", rewardDtoUpdated.name)
                assertEquals("legsColor updated 2", rewardDtoUpdated.legsColor)
                assertEquals("bodyColor updated 2", rewardDtoUpdated.bodyColor)
                assertEquals("armsColor updated 2", rewardDtoUpdated.armsColor)
            }
        }
        //
        rewardDtoUpdatedLive = rewardDao?.getRewardLive(rewardsToTestUpdateList.get(3).id)
        assertNotNull(rewardDtoUpdatedLive)
        if(rewardDtoUpdatedLive != null) {
            val rewardDtoUpdated = rewardDtoUpdatedLive.getValueBlocking()
            assertNotNull(rewardDtoUpdated)
            if (rewardDtoUpdated != null) {
                assertEquals(rewardsToTestUpdateList.get(3).id, rewardDtoUpdated.id)
                assertEquals(rewardsToTestUpdateList.get(3).level, rewardDtoUpdated.level)
                assertEquals(rewardsToTestUpdateList.get(3).code, rewardDtoUpdated.code)
                assertEquals(GregorianCalendar(2019, 4, 16).timeInMillis, rewardDtoUpdated.acquisitionDate)
                assertEquals(GregorianCalendar(2020, 4, 22).timeInMillis, rewardDtoUpdated.escapingDate)
                assertEquals(false, rewardDtoUpdated.isActive)
                assertEquals(false, rewardDtoUpdated.isEscaped)
                assertEquals("name updated 3", rewardDtoUpdated.name)
                assertEquals("legsColor updated 3", rewardDtoUpdated.legsColor)
                assertEquals("bodyColor updated 3", rewardDtoUpdated.bodyColor)
                assertEquals("armsColor updated 3", rewardDtoUpdated.armsColor)
            }
        }
        //
        tearDown()
    }

////////////////////////////////////////////////////////////////

    @Test
    fun getRewardLiveTest(){
        setupEmptyTable()
        val rewardDTO = RewardDTOTestUtils.generateReward(desiredId = 87)
        runBlocking {
            rewardDao?.insert(rewardDTO)
            rewardDao?.insert(RewardDTOTestUtils.generateRewards(61))
        }
        checkTotalCountIs(62)
        val rewardDtoInsertedLive = rewardDao?.getRewardLive(87)
        assertNotNull(rewardDtoInsertedLive)
        if(rewardDtoInsertedLive != null) {
            val rewardDtoInserted = rewardDtoInsertedLive.getValueBlocking()
            assertNotNull(rewardDtoInserted)
            if (rewardDtoInserted != null) {
                assertEquals(rewardDTO, rewardDtoInserted)
            }
        }
        //
        tearDown()
    }

    @Test
    fun getAllRewardsActiveAcquisitionDateAscTest() {
        setupEmptyTable()
        val rewardsToInsertList = listOf(
            RewardDTOTestUtils.generateReward(active = true, yearAcquired = 1987, monthAcquired = 2,dayAcquired = 9),
            RewardDTOTestUtils.generateReward(active = false, yearAcquired = 1985, monthAcquired = 3,dayAcquired = 11),
            RewardDTOTestUtils.generateReward(active = true, yearAcquired = 1984, monthAcquired = 4,dayAcquired = 13),
            RewardDTOTestUtils.generateReward(active = false, yearAcquired = 1983, monthAcquired = 5,dayAcquired = 15),
            RewardDTOTestUtils.generateReward(active = true, yearAcquired = 1982, monthAcquired = 6,dayAcquired = 17),
            RewardDTOTestUtils.generateReward(active = true, yearAcquired = 1981, monthAcquired = 7,dayAcquired = 19),
            RewardDTOTestUtils.generateReward(active = false, yearAcquired = 1980, monthAcquired = 8,dayAcquired = 21)
        )
        runBlocking {
            rewardDao?.insert(rewardsToInsertList)
        }
        checkTotalCountIs(7)
        val rewardDtoSortedLive = rewardDao?.getAllRewardsActiveAcquisitionDateAsc()
        assertNotNull(rewardDtoSortedLive)
        if(rewardDtoSortedLive != null) {
            val rewardDtoSorted = rewardDtoSortedLive.getValueBlocking()
            assertNotNull(rewardDtoSorted)
            if(rewardDtoSorted != null){
                assertEquals(4, rewardDtoSorted.size)
                var date = 0L
                for(i in rewardDtoSorted.indices){
                    assertEquals(true, rewardDtoSorted[i].isActive)
                    assert(rewardDtoSorted[i].acquisitionDate >= date)
                    date = rewardDtoSorted[i].acquisitionDate
                    assertEquals(date, rewardDtoSorted[i].acquisitionDate)
                }
            }
        }
        tearDown()
    }

    @Test
    fun getAllRewardsActiveAcquisitionDateDescTest() {
        setupEmptyTable()
        val rewardsToInsertList = listOf(
            RewardDTOTestUtils.generateReward(active = true, yearAcquired = 1987, monthAcquired = 2,dayAcquired = 9),
            RewardDTOTestUtils.generateReward(active = false, yearAcquired = 1985, monthAcquired = 3,dayAcquired = 11),
            RewardDTOTestUtils.generateReward(active = true, yearAcquired = 1984, monthAcquired = 4,dayAcquired = 13),
            RewardDTOTestUtils.generateReward(active = false, yearAcquired = 1983, monthAcquired = 5,dayAcquired = 15),
            RewardDTOTestUtils.generateReward(active = true, yearAcquired = 1982, monthAcquired = 6,dayAcquired = 17),
            RewardDTOTestUtils.generateReward(active = true, yearAcquired = 1981, monthAcquired = 7,dayAcquired = 19),
            RewardDTOTestUtils.generateReward(active = false, yearAcquired = 1980, monthAcquired = 8,dayAcquired = 21)
        )
        runBlocking {
            rewardDao?.insert(rewardsToInsertList)
        }
        checkTotalCountIs(7)
        val rewardDtoSortedLive = rewardDao?.getAllRewardsActiveAcquisitionDateDesc()
        assertNotNull(rewardDtoSortedLive)
        if(rewardDtoSortedLive != null) {
            val rewardDtoSorted = rewardDtoSortedLive.getValueBlocking()
            assertNotNull(rewardDtoSorted)
            if(rewardDtoSorted != null){
                assertEquals(4, rewardDtoSorted.size)
                assertEquals(true, rewardDtoSorted[3].isActive)
                var date = rewardDtoSorted[3].acquisitionDate
                for(i in 2 downTo 0){
                    assertEquals(true, rewardDtoSorted[i].isActive)
                    assert(rewardDtoSorted[i].acquisitionDate < date)
                    date = rewardDtoSorted[i].acquisitionDate
                    assertEquals(date, rewardDtoSorted[i].acquisitionDate)
                }
            }
        }
        tearDown()
    }

    @Test
    fun getAllRewardsActiveLevelAscTest() {
        setupEmptyTable()
        val rewardsToInsertList = mutableListOf<RewardDTO>()
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(42, desiredLevel = 2, active = true))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(51, desiredLevel = 3, active = true))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(67, desiredLevel = 4, active = true))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(83, desiredLevel = 5, active = true))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(21, desiredLevel = 2, active = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(34, desiredLevel = 3, active = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(47, desiredLevel = 4, active = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(59, desiredLevel = 5, active = false))
        runBlocking {
            rewardDao?.insert(rewardsToInsertList)
        }
        checkTotalCountIs(404)
        val rewardDtoSortedLive = rewardDao?.getAllRewardsActiveLevelAsc()
        assertNotNull(rewardDtoSortedLive)
        if(rewardDtoSortedLive != null) {
            val rewardDtoSorted = rewardDtoSortedLive.getValueBlocking()
            assertNotNull(rewardDtoSorted)
            if (rewardDtoSorted != null) {
                assertEquals(243, rewardDtoSorted.size)
                var level = 0
                for(i in rewardDtoSorted.indices) {
                    assertEquals(true, rewardDtoSorted[i].isActive)
                    assert(rewardDtoSorted[i].level <= level)
                    level = rewardDtoSorted[i].level
                }
            }
        }
        //
        tearDown()
    }

    @Test
    fun getAllRewardsActiveLevelDescTest() {
        setupEmptyTable()
        val rewardsToInsertList = mutableListOf<RewardDTO>()
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(29, desiredLevel = 2, active = true))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(19, desiredLevel = 3, active = true))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(42, desiredLevel = 4, active = true))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(81, desiredLevel = 5, active = true))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(8, desiredLevel = 2, active = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(17, desiredLevel = 3, active = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(38, desiredLevel = 4, active = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(91, desiredLevel = 5, active = false))
        runBlocking {
            rewardDao?.insert(rewardsToInsertList)
        }
        checkTotalCountIs(325)

        val rewardDtoSortedLive = rewardDao?.getAllRewardsActiveLevelDesc()
        assertNotNull(rewardDtoSortedLive)
        if(rewardDtoSortedLive != null) {
            val rewardDtoSorted = rewardDtoSortedLive.getValueBlocking()
            assertNotNull(rewardDtoSorted)
            if (rewardDtoSorted != null) {
                assertEquals(171, rewardDtoSorted.size)
                assertEquals(true, rewardDtoSorted[rewardDtoSorted.size - 1].isActive)
                var level = rewardDtoSorted[rewardDtoSorted.size - 1].level
                for(i in (rewardDtoSorted.size - 1) downTo 0){
                    assertEquals(true, rewardDtoSorted[i].isActive)
                    assert(rewardDtoSorted[i].level <= level)
                    level = rewardDtoSorted[i].level
                    assertEquals(level, rewardDtoSorted[i].level)
                }
            }
        }
        //
        tearDown()
    }

    @Test
    fun getAllRewardsNotEscapedAcquisitionDatDescTest() {
        setupEmptyTable()
//        val rewardsToInsertList = listOf(
//            RewardDTOTestUtils.rewardDTO_1_5_0_0_0_0_ACTIVE_ESCAPED_NO_ID,
//            RewardDTOTestUtils.rewardDTO_1_0_0_2_0_0_ACTIVE_NOT_ESCAPED_NO_ID,
//            RewardDTOTestUtils.rewardDTO_2_3_1_3_0_0_INACTIVE_NOT_ESCAPED_NO_ID,
//            RewardDTOTestUtils.rewardDTO_4_2_0_0_0_0_ACTIVE_NOT_ESCAPED_NO_ID,
//            RewardDTOTestUtils.rewardDTO_2_3_1_3_0_0_ACTIVE_ESCAPED_NO_ID,
//            RewardDTOTestUtils.rewardDTO_4_1_6_2_0_0_ACTIVE_NOT_ESCAPED_NO_ID)
//        runBlocking {
//            rewardDao?.insert(rewardsToInsertList)
//        }
//        checkTotalCountIs(6)


        val rewardsToInsertList = listOf(
            RewardDTOTestUtils.generateReward(escaped = true, yearAcquired = 1987, monthAcquired = 2,dayAcquired = 9),
            RewardDTOTestUtils.generateReward(escaped = false, yearAcquired = 1985, monthAcquired = 3,dayAcquired = 11),
            RewardDTOTestUtils.generateReward(escaped = true, yearAcquired = 1984, monthAcquired = 4,dayAcquired = 13),
            RewardDTOTestUtils.generateReward(escaped = false, yearAcquired = 1983, monthAcquired = 5,dayAcquired = 15),
            RewardDTOTestUtils.generateReward(escaped = true, yearAcquired = 1982, monthAcquired = 6,dayAcquired = 17),
            RewardDTOTestUtils.generateReward(escaped = true, yearAcquired = 1981, monthAcquired = 7,dayAcquired = 19),
            RewardDTOTestUtils.generateReward(escaped = false, yearAcquired = 1980, monthAcquired = 8,dayAcquired = 21)
        )
        runBlocking {
            rewardDao?.insert(rewardsToInsertList)
        }
        checkTotalCountIs(7)
        val rewardDtoSortedLive = rewardDao?.getAllRewardsNotEscapedAcquisitionDatDesc()
        assertNotNull(rewardDtoSortedLive)
        if(rewardDtoSortedLive != null) {
            val rewardDtoSorted = rewardDtoSortedLive.getValueBlocking()
            assertNotNull(rewardDtoSorted)
            if (rewardDtoSorted != null) {
                assertEquals(3, rewardDtoSorted.size)
                assertEquals(true, rewardDtoSorted[2].isActive)
                assertEquals(false, rewardDtoSorted[2].isEscaped)
                var date = rewardDtoSorted[2].acquisitionDate
                for(i in 1 downTo 0){
                    assertEquals(true, rewardDtoSorted[i].isActive)
                    assertEquals(false, rewardDtoSorted[i].isEscaped)
                    assert(rewardDtoSorted[i].acquisitionDate < date)
                    date = rewardDtoSorted[i].acquisitionDate
                    assertEquals(date, rewardDtoSorted[i].acquisitionDate)
                }
            }
        }
        //
        tearDown()
    }

    @Test
    fun getAllRewardsEscapedAcquisitionDateDescTest() {
        setupEmptyTable()
        val rewardsToInsertList = listOf(
            RewardDTOTestUtils.generateReward(escaped = true, yearAcquired = 1987, monthAcquired = 2,dayAcquired = 9),
            RewardDTOTestUtils.generateReward(escaped = false, yearAcquired = 1985, monthAcquired = 3,dayAcquired = 11),
            RewardDTOTestUtils.generateReward(escaped = true, yearAcquired = 1984, monthAcquired = 4,dayAcquired = 13),
            RewardDTOTestUtils.generateReward(escaped = false, yearAcquired = 1983, monthAcquired = 5,dayAcquired = 15),
            RewardDTOTestUtils.generateReward(escaped = true, yearAcquired = 1982, monthAcquired = 6,dayAcquired = 17),
            RewardDTOTestUtils.generateReward(escaped = true, yearAcquired = 1981, monthAcquired = 7,dayAcquired = 19),
            RewardDTOTestUtils.generateReward(escaped = false, yearAcquired = 1980, monthAcquired = 8,dayAcquired = 21)
        )
        runBlocking {
            rewardDao?.insert(rewardsToInsertList)
        }
        checkTotalCountIs(7)
        val rewardDtoSortedLive = rewardDao?.getAllRewardsEscapedAcquisitionDateDesc()
        assertNotNull(rewardDtoSortedLive)
        if(rewardDtoSortedLive != null) {
            val rewardDtoSorted = rewardDtoSortedLive.getValueBlocking()
            assertNotNull(rewardDtoSorted)
            if (rewardDtoSorted != null) {
                assertEquals(4, rewardDtoSorted.size)
                assertEquals(true, rewardDtoSorted[rewardDtoSorted.size - 1].isActive)
                assertEquals(true, rewardDtoSorted[rewardDtoSorted.size - 1].isEscaped)
                var date = rewardDtoSorted[rewardDtoSorted.size - 1].acquisitionDate
                for(i in (rewardDtoSorted.size - 1) downTo 0){
                    assertEquals(true, rewardDtoSorted[i].isActive)
                    assertEquals(true, rewardDtoSorted[i].isEscaped)
                    assert(rewardDtoSorted[i].acquisitionDate < date)
                    date = rewardDtoSorted[i].acquisitionDate
                    assertEquals(date, rewardDtoSorted[i].acquisitionDate)
                }
            }
        }
        //
        tearDown()
    }

    @Test
    fun getAllRewardsOfSPecificLevelNotActiveTest() {
        setupEmptyTable()
        val rewardsToInsertList = mutableListOf<RewardDTO>()
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(5, 3, active = false, escaped = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(3, 3, active = true, escaped = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(3, 3, active = true, escaped = true))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(6, 4, active = false, escaped = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(7, 4, active = true, escaped = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(7, 4, active = true, escaped = true))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(2, 5, active = true, escaped = true))
        //
        runBlocking {
            rewardDao?.insert(rewardsToInsertList)
        }
        checkTotalCountIs(33)
        //
        var rewardDtoSortedLive = rewardDao?.getAllRewardsOfSPecificLevelNotActive(3)
        assertNotNull(rewardDtoSortedLive)
        if(rewardDtoSortedLive != null) {
            val rewardDtoSorted = rewardDtoSortedLive.getValueBlocking()
            assertNotNull(rewardDtoSorted)
            if (rewardDtoSorted != null) {
                assertEquals(5, rewardDtoSorted.size)
                for(rewardDto in rewardDtoSorted){
                    assertEquals(false, rewardDto.isActive)
                    assertEquals(false, rewardDto.isEscaped)
                }
            }
        }
        //
        rewardDtoSortedLive = rewardDao?.getAllRewardsOfSPecificLevelNotActive(4)
        assertNotNull(rewardDtoSortedLive)
        if(rewardDtoSortedLive != null) {
            val rewardDtoSorted = rewardDtoSortedLive.getValueBlocking()
            assertNotNull(rewardDtoSorted)
            if (rewardDtoSorted != null) {
                assertEquals(6, rewardDtoSorted.size)
                for(rewardDto in rewardDtoSorted){
                    assertEquals(false, rewardDto.isActive)
                    assertEquals(false, rewardDto.isEscaped)
                }
            }
        }
        //
        rewardDtoSortedLive = rewardDao?.getAllRewardsOfSPecificLevelNotActive(5)
        assertNotNull(rewardDtoSortedLive)
        if(rewardDtoSortedLive != null) {
            val rewardDtoSorted = rewardDtoSortedLive.getValueBlocking()
            assertNotNull(rewardDtoSorted)
            if (rewardDtoSorted != null) {
                assertEquals(0, rewardDtoSorted.size)
            }
        }
        //
        tearDown()
    }

    @Test
    fun getAllRewardsOfSPecificLevelNotActiveOrEscapedTest() {
        setupEmptyTable()
        val rewardsToInsertList = mutableListOf<RewardDTO>()
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(5, 3, active = false, escaped = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(7, 3, active = true, escaped = true))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(6, 4, active = false, escaped = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(3, 4, active = true, escaped = true))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(8, 4, active = true, escaped = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(9, 3, active = true, escaped = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(2, 5, active = true, escaped = false))
        //
        runBlocking {
            rewardDao?.insert(rewardsToInsertList)
        }
        checkTotalCountIs(40)
        //
        var rewardDtoSortedLive = rewardDao?.getAllRewardsOfSPecificLevelNotActiveOrEscaped(3)
        assertNotNull(rewardDtoSortedLive)
        if(rewardDtoSortedLive != null) {
            val rewardDtoSorted = rewardDtoSortedLive.getValueBlocking()
            assertNotNull(rewardDtoSorted)
            if (rewardDtoSorted != null) {
                assertEquals(12, rewardDtoSorted.size)
                for(rewardDto in rewardDtoSorted){
                    val desiredState = (rewardDto.isActive && rewardDto.isEscaped) || (!rewardDto.isActive && !rewardDto.isEscaped)
                    assertEquals(true, desiredState)
                }
            }
        }
        //
        rewardDtoSortedLive = rewardDao?.getAllRewardsOfSPecificLevelNotActiveOrEscaped(4)
        assertNotNull(rewardDtoSortedLive)
        if(rewardDtoSortedLive != null) {
            val rewardDtoSorted = rewardDtoSortedLive.getValueBlocking()
            assertNotNull(rewardDtoSorted)
            if (rewardDtoSorted != null) {
                assertEquals(9, rewardDtoSorted.size)
                for(rewardDto in rewardDtoSorted){
                    val desiredState = (rewardDto.isActive && rewardDto.isEscaped) || (!rewardDto.isActive && !rewardDto.isEscaped)
                    assertEquals(true, desiredState)
                }
            }
        }
        //
        rewardDtoSortedLive = rewardDao?.getAllRewardsOfSPecificLevelNotActiveOrEscaped(5)
        assertNotNull(rewardDtoSortedLive)
        if(rewardDtoSortedLive != null) {
            val rewardDtoSorted = rewardDtoSortedLive.getValueBlocking()
            assertNotNull(rewardDtoSorted)
            if (rewardDtoSorted != null) {
                assertEquals(0, rewardDtoSorted.size)
            }
        }
        //
        tearDown()
    }

////////////////////////////////////////////////////////////////
    @Test
    fun countRewardsTest(){
        setupEmptyTable()
        runBlocking {
            rewardDao?.insert(RewardDTOTestUtils.generateRewards(125))
        }
        val totalRewards = runBlocking {
            rewardDao?.getNumberOfRows()
        }
        assertEquals(125, totalRewards)
        checkTotalCountIs(125)
        tearDown()
    }

    @Test
    fun countRewardsActiveNotEscapedLevelTest(){
        setupEmptyTable()
        val rewardsToInsertList = mutableListOf<RewardDTO>()
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(3, 1, active = true, escaped = true))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(5, 3, active = true, escaped = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(7, 3, active = true, escaped = true))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(9, 5, active = true, escaped = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(13, 5, active = true, escaped = true))

        runBlocking {
            rewardDao?.insert(rewardsToInsertList)
        }
        checkTotalCountIs(37)
        val numberLevel1 = runBlocking {
            rewardDao?.getNumberOfActiveNotEscapedRewardsForLevel(1)
        }
        assertEquals(0, numberLevel1)
        //
        val numberLevel3 = runBlocking {
            rewardDao?.getNumberOfActiveNotEscapedRewardsForLevel(3)
        }
        assertEquals(5, numberLevel3)
        //
        val numberLevel5 = runBlocking {
            rewardDao?.getNumberOfActiveNotEscapedRewardsForLevel(5)
        }
        assertEquals(9, numberLevel5)
        tearDown()
    }

    @Test
    fun countRewardsEscapedLevelTest(){
        setupEmptyTable()
        val rewardsToInsertList = mutableListOf<RewardDTO>()
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(3, 1, active = true, escaped = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(5, 3, active = true, escaped = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(7, 3, active = true, escaped = true))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(9, 5, active = true, escaped = false))
        rewardsToInsertList.addAll(RewardDTOTestUtils.generateRewards(13, 5, active = true, escaped = true))

        runBlocking {
            rewardDao?.insert(rewardsToInsertList)
        }
        checkTotalCountIs(37)
        val numberLevel1 = runBlocking {
            rewardDao?.getNumberOfEscapedRewardsForLevel(1)
        }
        assertEquals(0, numberLevel1)
        //
        val numberLevel3 = runBlocking {
            rewardDao?.getNumberOfEscapedRewardsForLevel(3)
        }
        assertEquals(7, numberLevel3)
        //
        val numberLevel5 = runBlocking {
            rewardDao?.getNumberOfEscapedRewardsForLevel(5)
        }
        assertEquals(13, numberLevel5)
        tearDown()
    }

}
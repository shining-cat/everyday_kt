package fr.shining_cat.everyday.localdata.dao


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import fr.shining_cat.everyday.localdata.EveryDayRoomDatabase
import fr.shining_cat.everyday.testutils.dto.RewardDTOTestUtils
import fr.shining_cat.everyday.testutils.extensions.getValueBlocking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
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
        EveryDayRoomDatabase.TEST_MODE = true
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        rewardDao = EveryDayRoomDatabase.getInstance(appContext).rewardDao()
        emptyTableAndCheck()
    }

//    @After
    private fun tearDown() {
        EveryDayRoomDatabase.closeAndDestroy()
    }

    @Test
    fun insertRewardTest() {
        setupEmptyTable()
        val rewardDTO = RewardDTOTestUtils.rewardDTO_4_1_6_2_0_0_WITH_ID
        val rewardDTOTestID = runBlocking {
            rewardDao?.insert(rewardDTO)
        }
        assertEquals(rewardDTOTestID, rewardDTO.id)
        tearDown()
    }

    @Test
    fun insertMultiRewardTest(){
        setupEmptyTable()
        val rewardsToInsertList = listOf(
            RewardDTOTestUtils.rewardDTO_1_5_0_0_0_0_ESCAPED,
            RewardDTOTestUtils.rewardDTO_1_5_0_0_0_0_ACTIVE,
            RewardDTOTestUtils.rewardDTO_1_0_0_2_0_0_ACTIVE,
            RewardDTOTestUtils.rewardDTO_1_0_0_2_0_0_ESCAPED)
        val insertedIds = runBlocking {
            rewardDao?.insert(rewardsToInsertList)
        }
        assertEquals(4, insertedIds?.size)
        assertNotNull(insertedIds)
        if(insertedIds != null) {
            var i = 1L
            for (id in insertedIds) {
                assertEquals(i, id)
                i++
            }
        }
        checkTotalCountIs(4)
        tearDown()
    }

    @Test
    fun countRewardsTest(){
        setupEmptyTable()
        val rewardDTO = RewardDTOTestUtils.rewardDTO_4_1_6_2_0_0_NO_ID
        runBlocking {
            for (i in 0..9) {
                rewardDao?.insert(rewardDTO)
            }
        }
        checkTotalCountIs(10)
        tearDown()
    }

    @Test
    fun countRewardsNotEscapedLevelTest(){
        setupEmptyTable()
        val rewarddto1a = RewardDTOTestUtils.rewardDTO_1_0_0_2_0_0_ACTIVE
        val rewarddto2a = RewardDTOTestUtils.rewardDTO_1_5_0_0_0_0_ACTIVE
        runBlocking {
            for (i in 1..5) {
                rewardDao?.insert(rewarddto1a)
            }
            for (i in 1..12) {
                rewardDao?.insert(rewarddto2a)
            }
        }
        val numberLevel1A = runBlocking {
            rewardDao?.getNumberOfActiveNotEscapedRewardsForLevel(1)
        }
        assertEquals(5, numberLevel1A)
        val numberLevel2a = runBlocking {
            rewardDao?.getNumberOfActiveNotEscapedRewardsForLevel(2)
        }
        assertEquals(12, numberLevel2a)
        val numberLevel5a = runBlocking {
            rewardDao?.getNumberOfActiveNotEscapedRewardsForLevel(5)
        }
        assertEquals(0, numberLevel5a)
        tearDown()
    }

    @Test
    fun countRewardsEscapedLevelTest(){
        setupEmptyTable()
        val rewarddto1e = RewardDTOTestUtils.rewardDTO_1_0_0_2_0_0_ESCAPED
        val rewarddto2e = RewardDTOTestUtils.rewardDTO_1_5_0_0_0_0_ESCAPED
        runBlocking {
            for (i in 1..10) {
                rewardDao?.insert(rewarddto1e)
            }
            for (i in 1..17) {
                rewardDao?.insert(rewarddto2e)
            }
        }
        //
        val numberLevel1e = runBlocking {
            rewardDao?.getNumberOfEscapedRewardsForLevel(1)
        }
        assertEquals(10, numberLevel1e)
        //
        val numberLevel2e = runBlocking {
            rewardDao?.getNumberOfEscapedRewardsForLevel(2)
        }
        assertEquals(17, numberLevel2e)
        //
        val numberLevel5e = runBlocking {
            rewardDao?.getNumberOfEscapedRewardsForLevel(5)
        }
        assertEquals(0, numberLevel5e)
        tearDown()
    }

    @Test
    fun deleteOneRewardTest(){
        setupEmptyTable()
        val rewardDTOToDeleteTest = RewardDTOTestUtils.rewardDTO_4_1_6_2_0_0_WITH_ID
        //
        runBlocking {
            rewardDao?.insert(rewardDTOToDeleteTest)
            for (i in 0..9) {
                rewardDao?.insert(RewardDTOTestUtils.rewardDTO_4_1_6_2_0_0_NO_ID)
            }
        }
        checkTotalCountIs(11)
        //
        val countDeleted = runBlocking {
            rewardDao?.deleteReward(rewardDTOToDeleteTest)
        }
        assertEquals(1, countDeleted)
        //
        checkTotalCountIs(10)
        tearDown()
    }

    @Test
    fun deleteMultiRewardTest(){
        setupEmptyTable()
        //insert the test-subject list of items
        val rewardsToDeleteList = listOf(
            RewardDTOTestUtils.rewardDTO_1_5_0_0_0_0_ESCAPED,
            RewardDTOTestUtils.rewardDTO_1_5_0_0_0_0_ACTIVE,
            RewardDTOTestUtils.rewardDTO_1_0_0_2_0_0_ACTIVE,
            RewardDTOTestUtils.rewardDTO_1_0_0_2_0_0_ESCAPED)
        //insert and collect the ids
        val insertedIds = runBlocking {
            rewardDao?.insert(rewardsToDeleteList)
        }
        assertNotNull(insertedIds)
        if(insertedIds != null) {
            assertEquals(4, insertedIds.size)
            var i = 1L //first index generated in DB will be 1 and not 0
            for (id in insertedIds) {
                assertEquals(i, id)
                rewardsToDeleteList[i.toInt()-1].id = id //update test-subject list with ids from insertion
                i++
            }
        }
        checkTotalCountIs(4)
        //insert some more data
        runBlocking {
            for (i in 0..19) {
                rewardDao?.insert(RewardDTOTestUtils.rewardDTO_4_1_6_2_0_0_NO_ID)
            }
        }
        checkTotalCountIs(24)
        //delete test-subject list of items
        val numberOfDeletedRows = runBlocking {
            rewardDao?.deleteReward(rewardsToDeleteList)
        }
        checkTotalCountIs(20)
        assertEquals(4, numberOfDeletedRows)
        //
        tearDown()
    }

    @Test
    fun deleteAllRewardTest() {
        setupEmptyTable()
        val rewardDTO = RewardDTOTestUtils.rewardDTO_4_1_6_2_0_0_NO_ID
        val numberOfDeletedRows = runBlocking {
            for (i in 0..19) {
                rewardDao?.insert(rewardDTO)
            }
            rewardDao?.deleteAllRewards()
        }
        assertEquals(20, numberOfDeletedRows)
        checkTotalCountIs(0)
        tearDown()
    }

    @Test
    fun getOneRewardTest(){
        setupEmptyTable()
        val rewardDTO = RewardDTOTestUtils.rewardDTO_4_1_6_2_0_0_NO_ID
        val rewardDtoInsertTestID = runBlocking {
            rewardDao?.insert(rewardDTO)
        }
        assertNotNull(rewardDtoInsertTestID)
        //
        if(rewardDtoInsertTestID != null) {
            val rewardDtoExtractedLive = rewardDao?.getRewardLive(rewardDtoInsertTestID)
            assertNotNull(rewardDtoExtractedLive)
            if(rewardDtoExtractedLive != null) {
                val rewardDtoExtracted = rewardDtoExtractedLive.getValueBlocking()
                assertNotNull(rewardDtoExtracted)
                if (rewardDtoExtracted != null) {
                    assertEquals(rewardDtoInsertTestID, rewardDtoExtracted.id)
                    assertEquals(rewardDTO.level, rewardDtoExtracted.level)
                    assertEquals(rewardDTO.code, rewardDtoExtracted.code)
                    assertEquals(rewardDTO.acquisitionDate, rewardDtoExtracted.acquisitionDate)
                    assertEquals(rewardDTO.escapingDate, rewardDtoExtracted.escapingDate)
                    assertEquals(rewardDTO.isActive, rewardDtoExtracted.isActive)
                    assertEquals(rewardDTO.isEscaped, rewardDtoExtracted.isEscaped)
                    assertEquals(rewardDTO.name, rewardDtoExtracted.name)
                    assertEquals(rewardDTO.legsColor, rewardDtoExtracted.legsColor)
                    assertEquals(rewardDTO.bodyColor, rewardDtoExtracted.bodyColor)
                    assertEquals(rewardDTO.armsColor, rewardDtoExtracted.armsColor)
                }
            }
        }
        tearDown()
    }

    @Test
    fun updateOneRewardTest(){
        setupEmptyTable()
        val rewardDTO = RewardDTOTestUtils.rewardDTO_4_1_6_2_0_0_WITH_ID
        runBlocking {
            rewardDao?.insert(rewardDTO)
            for (i in 0..9) {
                rewardDao?.insert(RewardDTOTestUtils.rewardDTO_4_1_6_2_0_0_NO_ID)
            }
        }
        checkTotalCountIs(11)
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
        checkTotalCountIs(11)
        val rewardDtoUpdatedLive = rewardDao?.getRewardLive(rewardDTO.id)
        assertNotNull(rewardDtoUpdatedLive)
        if(rewardDtoUpdatedLive != null) {
            val rewardDtoUpdated = rewardDtoUpdatedLive.getValueBlocking()
            assertNotNull(rewardDtoUpdated)
            if (rewardDtoUpdated != null) {
                assertEquals(rewardDTO.id, rewardDtoUpdated.id)
                assertEquals(rewardDTO.level, rewardDtoUpdated.level)
                assertEquals(rewardDTO.code, rewardDtoUpdated.code)
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

}
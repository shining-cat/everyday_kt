package fr.shining_cat.everyday.localdata.dao

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking

import fr.shining_cat.everyday.localdata.EveryDayRoomDatabase
import fr.shining_cat.everyday.testutils.dto.RewardDTOTestUtils
import org.junit.Assert.assertNotNull

@RunWith(AndroidJUnit4::class)
class RewardDaoTest {

    private var rewardDao: RewardDao? = null

    private fun setup(){
        EveryDayRoomDatabase.TEST_MODE = true
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        rewardDao = EveryDayRoomDatabase.getInstance(appContext).rewardDao()
        emptyTableAndCheck()
    }

    private fun tearDown() {
        EveryDayRoomDatabase.closeAndDestroy()
    }

    @Test
    fun insertRewardTest() {
        setup()
        val rewardDTO = RewardDTOTestUtils.rewardDTO_4_1_6_2_0_0_WITH_ID
        val rewardDTOTestID = runBlocking {
            rewardDao?.insert(rewardDTO)
        }
        assertEquals(rewardDTOTestID, rewardDTO.id)
        tearDown()
    }

    @Test
    fun insertMultiRewardTest(){
        setup()
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
        setup()
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
        setup()
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
        setup()
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
        setup()
        val rewardDTOToDeleteTest = RewardDTOTestUtils.rewardDTO_4_1_6_2_0_0_WITH_ID
        //
        val count = runBlocking {
            rewardDao?.insert(rewardDTOToDeleteTest)
            for (i in 0..9) {
                rewardDao?.insert(RewardDTOTestUtils.rewardDTO_4_1_6_2_0_0_NO_ID)
            }
            rewardDao?.getNumberOfRows()
        }
        assertEquals(11, count)
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
        setup()
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
        setup()
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
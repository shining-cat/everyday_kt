package fr.shining_cat.everyday.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import fr.shining_cat.everyday.localdata.EveryDayRoomDatabase
import fr.shining_cat.everyday.model.Critter
import fr.shining_cat.everyday.testutils.extensions.getValueBlocking
import fr.shining_cat.everyday.testutils.model.RewardModelTestUtils
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.util.*

class RewardRepositoryTest {

    //set the testing environment to use Main thread instead of background one
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private var rewardRepository: RewardRepository? = null

    private fun setupEmptyTable(){
        tearDown()
        EveryDayRoomDatabase.TEST_MODE = true
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val rewardDao = EveryDayRoomDatabase.getInstance(appContext).rewardDao()
        rewardRepository = RewardRepository(rewardDao)
        emptyTableAndCheck()
    }

    private fun emptyTableAndCheck(){
        runBlocking {
            rewardRepository?.deleteAllRewards()
        }
        checkTotalCountIs(0)
    }

    private fun checkTotalCountIs(expectedCount: Int){
        val count = runBlocking {
            rewardRepository?.allRewardsCount()
        }
        assertEquals(expectedCount, count)
    }

    private fun tearDown() {
        EveryDayRoomDatabase.closeAndDestroy()
    }

////////////////////////////////////////////////////////////////
    @Test
    fun insertRewardTest() {
        setupEmptyTable()
        val rewardModel = RewardModelTestUtils.generateReward()
        val rewardModelTestID = runBlocking {
            rewardRepository?.insert(rewardModel)
        }
        assertEquals(1L, rewardModelTestID)
        checkTotalCountIs(1)
        tearDown()
    }

    @Test
    fun insertMultiRewardTest(){
        setupEmptyTable()
        val rewardsToInsertList = RewardModelTestUtils.generateRewards(20)
        val insertedIds = runBlocking {
            rewardRepository?.insert(rewardsToInsertList)
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
    fun updateOneRewardTest(){
        setupEmptyTable()
        val rewardModel = RewardModelTestUtils.generateReward(
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
        val rewardCode = rewardModel.code
        val rewardModelInsertedTestID = runBlocking {
            rewardRepository?.insert(rewardModel)
        }
        runBlocking {
            rewardRepository?.insert(RewardModelTestUtils.generateRewards(53))
        }
        checkTotalCountIs(54)
        assertNotNull(rewardModelInsertedTestID)
        if(rewardModelInsertedTestID != null) {
            rewardModel.id = rewardModelInsertedTestID
            rewardModel.acquisitionDate = GregorianCalendar(2019, 6, 16).timeInMillis
            rewardModel.escapingDate = GregorianCalendar(2020, 5, 22).timeInMillis
            rewardModel.isActive = false
            rewardModel.isEscaped = true
            rewardModel.name = "I have updated my name"
            rewardModel.legsColor = "#00FF000000"
            rewardModel.bodyColor = "#0000FF00"
            rewardModel.armsColor = "#FFFFFFFF"
            //
            val numberOfUpdatedItems = runBlocking {
                rewardRepository?.updateReward(rewardModel)
            }
            assertEquals(1, numberOfUpdatedItems)
            checkTotalCountIs(54)
            val rewardModelUpdatedLive = rewardRepository?.getRewardLive(rewardModel.id)
            assertNotNull(rewardModelUpdatedLive)
            if (rewardModelUpdatedLive != null) {
                val rewardModelUpdated = rewardModelUpdatedLive.getValueBlocking()
                assertNotNull(rewardModelUpdated)
                if (rewardModelUpdated != null) {
                    assertEquals(43, rewardModelUpdated.id)
                    assertEquals(Critter.Level.LEVEL_5, rewardModelUpdated.level)
                    assertEquals(rewardCode, rewardModelUpdated.code)
                    assertEquals(
                        GregorianCalendar(2019, 6, 16).timeInMillis,
                        rewardModelUpdated.acquisitionDate
                    )
                    assertEquals(
                        GregorianCalendar(2020, 5, 22).timeInMillis,
                        rewardModelUpdated.escapingDate
                    )
                    assertEquals(false, rewardModelUpdated.isActive)
                    assertEquals(true, rewardModelUpdated.isEscaped)
                    assertEquals("I have updated my name", rewardModelUpdated.name)
                    assertEquals("#00FF000000", rewardModelUpdated.legsColor)
                    assertEquals("#0000FF00", rewardModelUpdated.bodyColor)
                    assertEquals("#FFFFFFFF", rewardModelUpdated.armsColor)
                }
            }
        }
        //
        tearDown()
    }

////////////////////////////////////////////////////////////////

    @Test
    fun getRewardLiveTest(){
        setupEmptyTable()
        val rewardModel = RewardModelTestUtils.generateReward()
        runBlocking {
            rewardRepository?.insert(RewardModelTestUtils.generateRewards(61))
        }
        val rewardModelTestID = runBlocking {
            rewardRepository?.insert(rewardModel)
        }
        checkTotalCountIs(62)
        assertNotNull(rewardModelTestID)
        if(rewardModelTestID != null) {
            val rewardModelInsertedLive = rewardRepository?.getRewardLive(rewardModelTestID)
            assertNotNull(rewardModelInsertedLive)
            if (rewardModelInsertedLive != null) {
                val rewardModelInserted = rewardModelInsertedLive.getValueBlocking()
                assertNotNull(rewardModelInserted)
                rewardModel.id = rewardModelTestID
                if (rewardModelInserted != null) {
                    assertEquals(rewardModel, rewardModelInserted)
                }
            }
        }
        //
        tearDown()
    }
}
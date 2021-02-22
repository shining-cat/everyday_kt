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

package fr.shining_cat.everyday.locale.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import fr.shining_cat.everyday.locale.EveryDayRoomDatabase
import fr.shining_cat.everyday.locale.entities.RewardEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
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

    private lateinit var rewardDao: RewardDao

    @Before
    fun setupEmptyTable() {
        tearDown()
        EveryDayRoomDatabase.TEST_MODE = true
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        rewardDao = EveryDayRoomDatabase.getInstance(appContext).rewardDao()
        emptyTableAndCheck()
    }


    /////////////////////////////
    //  UTILS
    /////////////////////////////
    private fun emptyTableAndCheck() {
        runBlocking {
            rewardDao.deleteAllRewards()
        }
        assertTableSize(0)
    }

    private fun assertTableSize(expectedCount: Int) {
        val count = runBlocking {
            rewardDao.getNumberOfRows()
        }
        assertEquals(expectedCount, count)
    }

    @After
    fun tearDown() {
        EveryDayRoomDatabase.closeAndDestroy()
    }

    private fun generateReward(
        desiredFlower: Int = 1,
        desiredMouth: Int = 1,
        desiredLegs: Int = 1,
        desiredArms: Int = 1,
        desiredEyes: Int = 1,
        desiredHorns: Int = 1,
        desiredLevel: Int = 1,
        active: Boolean = true,
        escaped: Boolean = false,
        desiredId: Long = -1,
        yearAcquired: Int = 2000,
        monthAcquired: Int = 5,
        dayAcquired: Int = 13,
        yearEscaped: Int = 2001,
        monthEscaped: Int = 6,
        dayEscaped: Int = 25,
        desiredName: String = "this is my name",
        desiredLegsColor: String = "#FF000000",
        desiredBodyColor: String = "#00FF0000",
        desiredArmsColor: String = "#0000FF00"
    ): RewardEntity {
        val returnEntity = RewardEntity(
            flower = desiredFlower,
            mouth = desiredMouth,
            legs = desiredLegs,
            arms = desiredArms,
            eyes = desiredEyes,
            horns = desiredHorns,
            level = desiredLevel,
            acquisitionDate = GregorianCalendar(
                yearAcquired,
                monthAcquired,
                dayAcquired
            ).timeInMillis,
            escapingDate = GregorianCalendar(
                yearEscaped,
                monthEscaped,
                dayEscaped
            ).timeInMillis,
            isActive = active,
            isEscaped = escaped,
            name = desiredName,
            legsColor = desiredLegsColor,
            bodyColor = desiredBodyColor,
            armsColor = desiredArmsColor
        )
        if (desiredId != -1L) {
            returnEntity.id = desiredId
        }
        return returnEntity
    }

    private fun generateRewards(
        numberOfEntities: Int = 1,
        startingId: Long = 1L,
        desiredLevel: Int = 1,
        active: Boolean = true,
        escaped: Boolean = false
    ): List<RewardEntity> {
        val returnList = mutableListOf<RewardEntity>()
        for (i in startingId until (startingId + numberOfEntities)) {
            returnList.add(
                generateReward(
                    desiredId = i,
                    desiredLevel = desiredLevel,
                    active = active,
                    escaped = escaped
                )
            )
        }
        return returnList
    }

    /////////////////////////////
    //  INSERTS
    /////////////////////////////
    @Test
    fun testInsertReward() {
        insertAndCheckRewardEntities()
        assertTableSize(20)
    }

    @Test
    fun testInsertWithConflict() {
        insertAndCheckRewardEntities()
        assertTableSize(20)
        //insert same entities again to cause conflict
        insertAndCheckRewardEntities()
        assertTableSize(20)
    }

    private fun insertAndCheckRewardEntities() {
        val rewardsToInsertList = generateRewards(20)
        assertEquals(20, rewardsToInsertList.size)
        val insertedIds = runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        assertEquals(20, insertedIds.size)
    }

    ///////////////////////////////////
    //DELETES
    ///////////////////////////////////

    @Test
    fun testDeleteRewardFromEmptyTable() {
        val rewardEntityToDeleteTest = generateReward(desiredId = 25)
        //
        assertTableSize(0)
        val countDeleted = runBlocking {
            rewardDao.delete(listOf(rewardEntityToDeleteTest))
        }
        assertEquals(0, countDeleted)
        assertTableSize(0)
    }

    @Test
    fun testDeleteNonExistentReward() {
        runBlocking {
            rewardDao.insert(
                listOf(
                    generateReward(desiredId = 25),
                    generateReward(desiredId = 26),
                    generateReward(desiredId = 27),
                    generateReward(desiredId = 28),
                    generateReward(desiredId = 29),
                    generateReward(desiredId = 30)
                )
            )
        }
        val rewardEntityToDeleteTest = generateReward(desiredId = 723)
        //
        assertTableSize(6)
        val countDeleted = runBlocking {
            rewardDao.delete(listOf(rewardEntityToDeleteTest))
        }
        assertEquals(0, countDeleted)
        assertTableSize(6)
    }

    @Test
    fun testDeleteOneReward() {
        val rewardEntityToDeleteTest = generateReward(desiredId = 25)
        val rewardsToInsertList = mutableListOf<RewardEntity>()
        rewardsToInsertList.add(rewardEntityToDeleteTest)
        rewardsToInsertList.addAll(generateRewards(50))
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        assertTableSize(50)
        //
        val countDeleted = runBlocking {
            rewardDao.delete(listOf(rewardEntityToDeleteTest))
        }
        assertEquals(1, countDeleted)
        //
        assertTableSize(49)

    }

    @Test
    fun testDeleteMultiRewardOnEmptyTable() {
        //create the test-subject list of items
        val rewardsToDeleteList = generateRewards(17)
        assertTableSize(0)
        //delete test-subject list of items
        val numberOfDeletedRows = runBlocking {
            rewardDao.delete(rewardsToDeleteList)
        }
        assertTableSize(0)
        assertEquals(0, numberOfDeletedRows)
    }

    @Test
    fun testDeleteMultiReward() {
        //insert the test-subject list of items
        val rewardsInserted = generateRewards(17)
        //insert and collect the ids
        val insertedIds = runBlocking {
            rewardDao.insert(rewardsInserted)
        }
        assertNotNull(insertedIds)
        assertEquals(17, insertedIds.size)
        val rewardsToDelete = rewardsInserted.subList(0, 10)

        //delete test-subject list of items
        val numberOfDeletedRows = runBlocking {
            rewardDao.delete(rewardsToDelete)
        }
        assertEquals(10, numberOfDeletedRows)
        assertTableSize(7)
    }

    @Test
    fun testDeleteAllRewardOnEmptyTable() {
        assertTableSize(0)
        val numberOfDeletedRows = runBlocking {
            rewardDao.deleteAllRewards()
        }
        assertEquals(0, numberOfDeletedRows)
        assertTableSize(0)
    }

    @Test
    fun testDeleteAllReward() {
        runBlocking {
            rewardDao.insert(generateRewards(73))
        }
        assertTableSize(73)
        val numberOfDeletedRows = runBlocking {
            rewardDao.deleteAllRewards()
        }
        assertEquals(73, numberOfDeletedRows)
        assertTableSize(0)
    }

    ///////////////////////////////////
    //GETS
    ///////////////////////////////////

    @Test
    fun testGetRewardOnEmptyTable() {
        runBlocking {
            assertTableSize(0)
            val rewardEntity = rewardDao.getReward(75)
            assertNull(rewardEntity)
        }
    }

    @Test
    fun testGetNonExistentReward() {
        runBlocking {
            rewardDao.insert(
                listOf(
                    generateReward(desiredId = 25),
                    generateReward(desiredId = 26),
                    generateReward(desiredId = 27),
                    generateReward(desiredId = 28),
                    generateReward(desiredId = 29)
                )
            )
            assertTableSize(5)
            val rewardEntity = rewardDao.getReward(73)
            assertNull(rewardEntity)
        }
    }

    @Test
    fun testGetOneReward() {
        val rewardEntityInserted = generateReward(
            desiredLevel = 3,
            active = true,
            escaped = false,
            desiredId = 83L,
            yearAcquired = 1989,
            monthAcquired = 3,
            dayAcquired = 7,
            yearEscaped = 1997,
            monthEscaped = 8,
            dayEscaped = 13,
            desiredName = "getOneRewardTest is my name",
            desiredLegsColor = "getOneRewardTest legs color",
            desiredBodyColor = "getOneRewardTest body color",
            desiredArmsColor = "getOneRewardTest arms color"
        )
        val flower = rewardEntityInserted.flower
        val mouth = rewardEntityInserted.mouth
        val legs = rewardEntityInserted.legs
        val arms = rewardEntityInserted.arms
        val eyes = rewardEntityInserted.eyes
        val horns = rewardEntityInserted.horns
        val rewardEntityInsertedTestID = runBlocking {
            rewardDao.insert(listOf(rewardEntityInserted))
        }
        assertNotNull(rewardEntityInsertedTestID)
        assertEquals(83L, rewardEntityInsertedTestID[0])
        //
        runBlocking {
            rewardDao.insert(generateRewards(13))
        }
        assertTableSize(14)
        //
        val rewardEntity = runBlocking {
            rewardDao.getReward(rewardEntityInsertedTestID[0])
        }
        assertNotNull(rewardEntity)
        if (rewardEntity != null) {
            assertEquals(83, rewardEntity.id)
            assertEquals(3, rewardEntity.level)
            assertEquals(flower, rewardEntity.flower)
            assertEquals(mouth, rewardEntity.mouth)
            assertEquals(legs, rewardEntity.legs)
            assertEquals(arms, rewardEntity.arms)
            assertEquals(eyes, rewardEntity.eyes)
            assertEquals(horns, rewardEntity.horns)
            assertEquals(
                GregorianCalendar(1989, 3, 7).timeInMillis,
                rewardEntity.acquisitionDate
            )
            assertEquals(
                GregorianCalendar(1997, 8, 13).timeInMillis,
                rewardEntity.escapingDate
            )
            assertEquals(true, rewardEntity.isActive)
            assertEquals(false, rewardEntity.isEscaped)
            assertEquals("getOneRewardTest is my name", rewardEntity.name)
            assertEquals("getOneRewardTest legs color", rewardEntity.legsColor)
            assertEquals("getOneRewardTest body color", rewardEntity.bodyColor)
            assertEquals("getOneRewardTest arms color", rewardEntity.armsColor)
        }
    }

    ///////////////////////////////////
    //UPDATES
    ///////////////////////////////////

    @Test
    fun testUpdateOneRewardOnEmptyTable() {
        assertTableSize(0)
        val rewardEntityToUpdate = generateReward(desiredId = 73)
        val numberOfUpdatedItems = runBlocking {
            rewardDao.update(listOf(rewardEntityToUpdate))
        }
        assertEquals(0, numberOfUpdatedItems)
        assertTableSize(0)
    }

    @Test
    fun testUpdateNonExistentReward() {
        runBlocking {
            rewardDao.insert(
                listOf(
                    generateReward(desiredId = 25),
                    generateReward(desiredId = 26),
                    generateReward(desiredId = 27),
                    generateReward(desiredId = 28),
                    generateReward(desiredId = 29)
                )
            )
        }
        assertTableSize(5)
        val rewardEntityToUpdate = generateReward(desiredId = 73)
        val numberOfUpdatedItems = runBlocking {
            rewardDao.update(listOf(rewardEntityToUpdate))
        }
        assertEquals(0, numberOfUpdatedItems)
        assertTableSize(5)
    }

    @Test
    fun testUpdateOneReward() {
        val rewardEntity = generateReward(
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
            desiredArmsColor = "updateOneRewardTest arms color"
        )
        val flower = rewardEntity.flower
        val mouth = rewardEntity.mouth
        val legs = rewardEntity.legs
        val arms = rewardEntity.arms
        val eyes = rewardEntity.eyes
        val horns = rewardEntity.horns
        val rewardEntityInsertedTestID = runBlocking {
            rewardDao.insert(listOf(rewardEntity))
        }
        assertNotNull(rewardEntityInsertedTestID)
        assertEquals(43L, rewardEntityInsertedTestID[0])

        runBlocking {
            rewardDao.insert(generateRewards(53, 100))
        }
        assertTableSize(54)
        //
        rewardEntity.acquisitionDate = GregorianCalendar(2019, 6, 16).timeInMillis
        rewardEntity.escapingDate = GregorianCalendar(2020, 5, 22).timeInMillis
        rewardEntity.isActive = false
        rewardEntity.isEscaped = true
        rewardEntity.name = "I have updated my name"
        rewardEntity.legsColor = "#00FF000000"
        rewardEntity.bodyColor = "#0000FF00"
        rewardEntity.armsColor = "#FFFFFFFF"
        //
        val numberOfUpdatedItems = runBlocking {
            rewardDao.update(listOf(rewardEntity))
        }
        assertEquals(1, numberOfUpdatedItems)
        assertTableSize(54)
        val rewardEntityUpdated = runBlocking {
            rewardDao.getReward(rewardEntity.id)
        }
        assertNotNull(rewardEntityUpdated)
        if (rewardEntityUpdated != null) {
            assertEquals(43, rewardEntityUpdated.id)
            assertEquals(5, rewardEntityUpdated.level)
            assertEquals(flower, rewardEntityUpdated.flower)
            assertEquals(mouth, rewardEntityUpdated.mouth)
            assertEquals(legs, rewardEntityUpdated.legs)
            assertEquals(arms, rewardEntityUpdated.arms)
            assertEquals(eyes, rewardEntityUpdated.eyes)
            assertEquals(horns, rewardEntityUpdated.horns)
            assertEquals(
                GregorianCalendar(2019, 6, 16).timeInMillis,
                rewardEntityUpdated.acquisitionDate
            )
            assertEquals(
                GregorianCalendar(2020, 5, 22).timeInMillis,
                rewardEntityUpdated.escapingDate
            )
            assertEquals(false, rewardEntityUpdated.isActive)
            assertEquals(true, rewardEntityUpdated.isEscaped)
            assertEquals("I have updated my name", rewardEntityUpdated.name)
            assertEquals("#00FF000000", rewardEntityUpdated.legsColor)
            assertEquals("#0000FF00", rewardEntityUpdated.bodyColor)
            assertEquals("#FFFFFFFF", rewardEntityUpdated.armsColor)
        }
        //

    }

    @Test
    fun testUpdateMultipleRewards() {
        val rewardsToTestUpdateList = listOf(
            generateReward(desiredId = 13),
            generateReward(desiredId = 17),
            generateReward(desiredId = 23),
            generateReward(desiredId = 37)
        )
        val insertedIds = runBlocking {
            rewardDao.insert(rewardsToTestUpdateList)
        }
        assertEquals(4, insertedIds.size)
        //
        rewardsToTestUpdateList[0].acquisitionDate = GregorianCalendar(2019, 1, 16).timeInMillis
        rewardsToTestUpdateList[0].escapingDate = GregorianCalendar(2020, 1, 22).timeInMillis
        rewardsToTestUpdateList[0].isActive = false
        rewardsToTestUpdateList[0].isEscaped = true
        rewardsToTestUpdateList[0].name = "name updated 0"
        rewardsToTestUpdateList[0].legsColor = "legsColor updated 0"
        rewardsToTestUpdateList[0].bodyColor = "bodyColor updated 0"
        rewardsToTestUpdateList[0].armsColor = "armsColor updated 0"
        //
        rewardsToTestUpdateList[1].acquisitionDate = GregorianCalendar(2019, 2, 16).timeInMillis
        rewardsToTestUpdateList[1].escapingDate = GregorianCalendar(2020, 2, 22).timeInMillis
        rewardsToTestUpdateList[1].isActive = true
        rewardsToTestUpdateList[1].isEscaped = false
        rewardsToTestUpdateList[1].name = "name updated 1"
        rewardsToTestUpdateList[1].legsColor = "legsColor updated 1"
        rewardsToTestUpdateList[1].bodyColor = "bodyColor updated 1"
        rewardsToTestUpdateList[1].armsColor = "armsColor updated 1"
        //
        rewardsToTestUpdateList[2].acquisitionDate = GregorianCalendar(2019, 3, 16).timeInMillis
        rewardsToTestUpdateList[2].escapingDate = GregorianCalendar(2020, 3, 22).timeInMillis
        rewardsToTestUpdateList[2].isActive = true
        rewardsToTestUpdateList[2].isEscaped = true
        rewardsToTestUpdateList[2].name = "name updated 2"
        rewardsToTestUpdateList[2].legsColor = "legsColor updated 2"
        rewardsToTestUpdateList[2].bodyColor = "bodyColor updated 2"
        rewardsToTestUpdateList[2].armsColor = "armsColor updated 2"
        //
        rewardsToTestUpdateList[3].acquisitionDate = GregorianCalendar(2019, 4, 16).timeInMillis
        rewardsToTestUpdateList[3].escapingDate = GregorianCalendar(2020, 4, 22).timeInMillis
        rewardsToTestUpdateList[3].isActive = false
        rewardsToTestUpdateList[3].isEscaped = false
        rewardsToTestUpdateList[3].name = "name updated 3"
        rewardsToTestUpdateList[3].legsColor = "legsColor updated 3"
        rewardsToTestUpdateList[3].bodyColor = "bodyColor updated 3"
        rewardsToTestUpdateList[3].armsColor = "armsColor updated 3"
        //
        val numberOfUpdatedItems = runBlocking {
            rewardDao.update(rewardsToTestUpdateList)
        }
        assertEquals(4, numberOfUpdatedItems)
        //
        val rewardEntityUpdated0 = runBlocking {
            rewardDao.getReward(rewardsToTestUpdateList[0].id)
        }
        assertNotNull(rewardEntityUpdated0)
        if (rewardEntityUpdated0 != null) {
            assertEquals(rewardsToTestUpdateList[0].id, rewardEntityUpdated0.id)
            assertEquals(rewardsToTestUpdateList[0].level, rewardEntityUpdated0.level)
            assertEquals(rewardsToTestUpdateList[0].flower, rewardEntityUpdated0.flower)
            assertEquals(rewardsToTestUpdateList[0].mouth, rewardEntityUpdated0.mouth)
            assertEquals(rewardsToTestUpdateList[0].legs, rewardEntityUpdated0.legs)
            assertEquals(rewardsToTestUpdateList[0].arms, rewardEntityUpdated0.arms)
            assertEquals(rewardsToTestUpdateList[0].eyes, rewardEntityUpdated0.eyes)
            assertEquals(rewardsToTestUpdateList[0].horns, rewardEntityUpdated0.horns)
            assertEquals(
                GregorianCalendar(2019, 1, 16).timeInMillis,
                rewardEntityUpdated0.acquisitionDate
            )
            assertEquals(
                GregorianCalendar(2020, 1, 22).timeInMillis,
                rewardEntityUpdated0.escapingDate
            )
            assertEquals(false, rewardEntityUpdated0.isActive)
            assertEquals(true, rewardEntityUpdated0.isEscaped)
            assertEquals("name updated 0", rewardEntityUpdated0.name)
            assertEquals("legsColor updated 0", rewardEntityUpdated0.legsColor)
            assertEquals("bodyColor updated 0", rewardEntityUpdated0.bodyColor)
            assertEquals("armsColor updated 0", rewardEntityUpdated0.armsColor)
        }
        //
        val rewardEntityUpdated1 = runBlocking {
            rewardDao.getReward(rewardsToTestUpdateList[1].id)
        }
        assertNotNull(rewardEntityUpdated1)
        if (rewardEntityUpdated1 != null) {
            assertEquals(rewardsToTestUpdateList[1].id, rewardEntityUpdated1.id)
            assertEquals(rewardsToTestUpdateList[1].level, rewardEntityUpdated1.level)
            assertEquals(rewardsToTestUpdateList[1].flower, rewardEntityUpdated1.flower)
            assertEquals(rewardsToTestUpdateList[1].mouth, rewardEntityUpdated1.mouth)
            assertEquals(rewardsToTestUpdateList[1].legs, rewardEntityUpdated1.legs)
            assertEquals(rewardsToTestUpdateList[1].arms, rewardEntityUpdated1.arms)
            assertEquals(rewardsToTestUpdateList[1].eyes, rewardEntityUpdated1.eyes)
            assertEquals(rewardsToTestUpdateList[1].horns, rewardEntityUpdated1.horns)
            assertEquals(
                GregorianCalendar(2019, 2, 16).timeInMillis,
                rewardEntityUpdated1.acquisitionDate
            )
            assertEquals(
                GregorianCalendar(2020, 2, 22).timeInMillis,
                rewardEntityUpdated1.escapingDate
            )
            assertEquals(true, rewardEntityUpdated1.isActive)
            assertEquals(false, rewardEntityUpdated1.isEscaped)
            assertEquals("name updated 1", rewardEntityUpdated1.name)
            assertEquals("legsColor updated 1", rewardEntityUpdated1.legsColor)
            assertEquals("bodyColor updated 1", rewardEntityUpdated1.bodyColor)
            assertEquals("armsColor updated 1", rewardEntityUpdated1.armsColor)
        }
        //
        val rewardEntityUpdated2 = runBlocking {
            rewardDao.getReward(rewardsToTestUpdateList[2].id)
        }
        assertNotNull(rewardEntityUpdated2)
        if (rewardEntityUpdated2 != null) {
            assertEquals(rewardsToTestUpdateList[2].id, rewardEntityUpdated2.id)
            assertEquals(rewardsToTestUpdateList[2].level, rewardEntityUpdated2.level)
            assertEquals(rewardsToTestUpdateList[2].flower, rewardEntityUpdated2.flower)
            assertEquals(rewardsToTestUpdateList[2].mouth, rewardEntityUpdated2.mouth)
            assertEquals(rewardsToTestUpdateList[2].legs, rewardEntityUpdated2.legs)
            assertEquals(rewardsToTestUpdateList[2].arms, rewardEntityUpdated2.arms)
            assertEquals(rewardsToTestUpdateList[2].eyes, rewardEntityUpdated2.eyes)
            assertEquals(rewardsToTestUpdateList[2].horns, rewardEntityUpdated2.horns)
            assertEquals(
                GregorianCalendar(2019, 3, 16).timeInMillis,
                rewardEntityUpdated2.acquisitionDate
            )
            assertEquals(
                GregorianCalendar(2020, 3, 22).timeInMillis,
                rewardEntityUpdated2.escapingDate
            )
            assertEquals(true, rewardEntityUpdated2.isActive)
            assertEquals(true, rewardEntityUpdated2.isEscaped)
            assertEquals("name updated 2", rewardEntityUpdated2.name)
            assertEquals("legsColor updated 2", rewardEntityUpdated2.legsColor)
            assertEquals("bodyColor updated 2", rewardEntityUpdated2.bodyColor)
            assertEquals("armsColor updated 2", rewardEntityUpdated2.armsColor)
        }
        //
        val rewardEntityUpdated3 = runBlocking {
            rewardDao.getReward(rewardsToTestUpdateList[3].id)
        }
        assertNotNull(rewardEntityUpdated3)
        if (rewardEntityUpdated3 != null) {
            assertEquals(rewardsToTestUpdateList[3].id, rewardEntityUpdated3.id)
            assertEquals(rewardsToTestUpdateList[3].level, rewardEntityUpdated3.level)
            assertEquals(rewardsToTestUpdateList[3].flower, rewardEntityUpdated3.flower)
            assertEquals(rewardsToTestUpdateList[3].mouth, rewardEntityUpdated3.mouth)
            assertEquals(rewardsToTestUpdateList[3].legs, rewardEntityUpdated3.legs)
            assertEquals(rewardsToTestUpdateList[3].arms, rewardEntityUpdated3.arms)
            assertEquals(rewardsToTestUpdateList[3].eyes, rewardEntityUpdated3.eyes)
            assertEquals(rewardsToTestUpdateList[3].horns, rewardEntityUpdated3.horns)
            assertEquals(
                GregorianCalendar(2019, 4, 16).timeInMillis,
                rewardEntityUpdated3.acquisitionDate
            )
            assertEquals(
                GregorianCalendar(2020, 4, 22).timeInMillis,
                rewardEntityUpdated3.escapingDate
            )
            assertEquals(false, rewardEntityUpdated3.isActive)
            assertEquals(false, rewardEntityUpdated3.isEscaped)
            assertEquals("name updated 3", rewardEntityUpdated3.name)
            assertEquals("legsColor updated 3", rewardEntityUpdated3.legsColor)
            assertEquals("bodyColor updated 3", rewardEntityUpdated3.bodyColor)
            assertEquals("armsColor updated 3", rewardEntityUpdated3.armsColor)
        }
        //

    }

    ///////////////////////////////////
    //GETS ORDERED AND FILTERED
    ///////////////////////////////////
    @Test
    fun testGetAllRewardsActiveAcquisitionDateAscOnEmptyTable() {
        runBlocking {
            assertTrue(rewardDao.getAllRewardsActiveAcquisitionDateAsc().isEmpty())
        }
    }

    @Test
    fun testGetAllRewardsActiveAcquisitionDateAsc() {
        val rewardsToInsertList = listOf(
            generateReward(active = true, yearAcquired = 1987, monthAcquired = 2, dayAcquired = 9),
            generateReward(
                active = false,
                yearAcquired = 1985,
                monthAcquired = 3,
                dayAcquired = 11
            ),
            generateReward(active = true, yearAcquired = 1984, monthAcquired = 4, dayAcquired = 13),
            generateReward(
                active = false,
                yearAcquired = 1983,
                monthAcquired = 5,
                dayAcquired = 15
            ),
            generateReward(active = true, yearAcquired = 1982, monthAcquired = 6, dayAcquired = 17),
            generateReward(active = true, yearAcquired = 1981, monthAcquired = 7, dayAcquired = 19),
            generateReward(active = false, yearAcquired = 1980, monthAcquired = 8, dayAcquired = 21)
        )
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        assertTableSize(7)
        val rewardEntitySorted = runBlocking {
            rewardDao.getAllRewardsActiveAcquisitionDateAsc()
        }
        assertNotNull(rewardEntitySorted)
        if (rewardEntitySorted != null) {
            assertEquals(4, rewardEntitySorted.size)
            var date = 0L
            for (i in rewardEntitySorted.indices) {
                assertEquals(true, rewardEntitySorted[i].isActive)
                assert(rewardEntitySorted[i].acquisitionDate >= date)
                date = rewardEntitySorted[i].acquisitionDate
                assertEquals(date, rewardEntitySorted[i].acquisitionDate)
            }
        }

    }

    @Test
    fun testGetAllRewardsActiveAcquisitionDateDescOnEmptyTable() {
        runBlocking {
            assertTrue(rewardDao.getAllRewardsActiveAcquisitionDateDesc().isEmpty())
        }
    }

    @Test
    fun testGetAllRewardsActiveAcquisitionDateDesc() {

        val rewardsToInsertList = listOf(
            generateReward(active = true, yearAcquired = 1987, monthAcquired = 2, dayAcquired = 9),
            generateReward(
                active = false,
                yearAcquired = 1985,
                monthAcquired = 3,
                dayAcquired = 11
            ),
            generateReward(active = true, yearAcquired = 1984, monthAcquired = 4, dayAcquired = 13),
            generateReward(
                active = false,
                yearAcquired = 1983,
                monthAcquired = 5,
                dayAcquired = 15
            ),
            generateReward(active = true, yearAcquired = 1982, monthAcquired = 6, dayAcquired = 17),
            generateReward(active = true, yearAcquired = 1981, monthAcquired = 7, dayAcquired = 19),
            generateReward(active = false, yearAcquired = 1980, monthAcquired = 8, dayAcquired = 21)
        )
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        assertTableSize(7)
        val rewardEntitySorted = runBlocking {
            rewardDao.getAllRewardsActiveAcquisitionDateDesc()
        }
        assertNotNull(rewardEntitySorted)
        if (rewardEntitySorted != null) {
            assertEquals(4, rewardEntitySorted.size)
            assertEquals(true, rewardEntitySorted[3].isActive)
            var date = rewardEntitySorted[3].acquisitionDate
            for (i in 2 downTo 0) {
                assertEquals(true, rewardEntitySorted[i].isActive)
                assert(rewardEntitySorted[i].acquisitionDate <= date)
                date = rewardEntitySorted[i].acquisitionDate
                assertEquals(date, rewardEntitySorted[i].acquisitionDate)
            }
        }

    }

    @Test
    fun testGetAllRewardsActiveLevelAscOnEmptyTable() {
        runBlocking {
            assertTrue(rewardDao.getAllRewardsActiveLevelAsc().isEmpty())
        }
    }

    @Test
    fun testGetAllRewardsActiveLevelAsc() {

        val rewardsToInsertList = mutableListOf<RewardEntity>()
        rewardsToInsertList.addAll(generateRewards(42, 1, desiredLevel = 2, active = true))
        rewardsToInsertList.addAll(generateRewards(51, 100, desiredLevel = 3, active = true))
        rewardsToInsertList.addAll(generateRewards(67, 200, desiredLevel = 4, active = true))
        rewardsToInsertList.addAll(generateRewards(83, 300, desiredLevel = 5, active = true))
        rewardsToInsertList.addAll(generateRewards(21, 400, desiredLevel = 2, active = false))
        rewardsToInsertList.addAll(generateRewards(34, 500, desiredLevel = 3, active = false))
        rewardsToInsertList.addAll(generateRewards(47, 600, desiredLevel = 4, active = false))
        rewardsToInsertList.addAll(generateRewards(59, 700, desiredLevel = 5, active = false))

        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        assertTableSize(404)
        val rewardEntitySorted = runBlocking {
            rewardDao.getAllRewardsActiveLevelAsc()
        }
        assertNotNull(rewardEntitySorted)
        if (rewardEntitySorted != null) {
            assertEquals(243, rewardEntitySorted.size)
            var level = 0
            for (i in rewardEntitySorted.indices) {
                assertEquals(true, rewardEntitySorted[i].isActive)
                assert(rewardEntitySorted[i].level <= level)
                level = rewardEntitySorted[i].level
            }
        }
        //

    }

    @Test
    fun testGetAllRewardsActiveLevelDescOnEmptyTable() {
        runBlocking {
            assertTrue(rewardDao.getAllRewardsActiveLevelDesc().isEmpty())
        }
    }

    @Test
    fun testGetAllRewardsActiveLevelDesc() {

        val rewardsToInsertList = mutableListOf<RewardEntity>()
        rewardsToInsertList.addAll(generateRewards(29, 1, desiredLevel = 2, active = true))
        rewardsToInsertList.addAll(generateRewards(19, 100, desiredLevel = 3, active = true))
        rewardsToInsertList.addAll(generateRewards(42, 200, desiredLevel = 4, active = true))
        rewardsToInsertList.addAll(generateRewards(81, 300, desiredLevel = 5, active = true))
        rewardsToInsertList.addAll(generateRewards(8, 400, desiredLevel = 2, active = false))
        rewardsToInsertList.addAll(generateRewards(17, 500, desiredLevel = 3, active = false))
        rewardsToInsertList.addAll(generateRewards(38, 600, desiredLevel = 4, active = false))
        rewardsToInsertList.addAll(generateRewards(91, 700, desiredLevel = 5, active = false))
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        assertTableSize(325)

        val rewardEntitySorted = runBlocking {
            rewardDao.getAllRewardsActiveLevelDesc()
        }
        assertNotNull(rewardEntitySorted)
        if (rewardEntitySorted != null) {
            assertEquals(171, rewardEntitySorted.size)
            assertEquals(true, rewardEntitySorted[rewardEntitySorted.size - 1].isActive)
            var level = rewardEntitySorted[rewardEntitySorted.size - 1].level
            for (i in (rewardEntitySorted.size - 1) downTo 0) {
                assertEquals(true, rewardEntitySorted[i].isActive)
                assert(rewardEntitySorted[i].level <= level)
                level = rewardEntitySorted[i].level
                assertEquals(level, rewardEntitySorted[i].level)
            }
        }
        //

    }

    @Test
    fun testGetAllRewardsNotEscapedAcquisitionDatDescOnEmptyTable() {
        runBlocking {
            assertTrue(rewardDao.getAllRewardsNotEscapedAcquisitionDatDesc().isEmpty())
        }
    }

    @Test
    fun testGetAllRewardsNotEscapedAcquisitionDatDesc() {

        val rewardsToInsertList = listOf(
            generateReward(escaped = true, yearAcquired = 1987, monthAcquired = 2, dayAcquired = 9),
            generateReward(
                escaped = false,
                yearAcquired = 1985,
                monthAcquired = 3,
                dayAcquired = 11
            ),
            generateReward(
                escaped = true,
                yearAcquired = 1984,
                monthAcquired = 4,
                dayAcquired = 13
            ),
            generateReward(
                escaped = false,
                yearAcquired = 1983,
                monthAcquired = 5,
                dayAcquired = 15
            ),
            generateReward(
                escaped = true,
                yearAcquired = 1982,
                monthAcquired = 6,
                dayAcquired = 17
            ),
            generateReward(
                escaped = true,
                yearAcquired = 1981,
                monthAcquired = 7,
                dayAcquired = 19
            ),
            generateReward(
                escaped = false,
                yearAcquired = 1980,
                monthAcquired = 8,
                dayAcquired = 21
            )
        )
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        assertTableSize(7)
        val rewardEntitySorted = runBlocking {
            rewardDao.getAllRewardsNotEscapedAcquisitionDatDesc()
        }
        assertNotNull(rewardEntitySorted)
        if (rewardEntitySorted != null) {
            assertEquals(3, rewardEntitySorted.size)
            assertEquals(true, rewardEntitySorted[2].isActive)
            assertEquals(false, rewardEntitySorted[2].isEscaped)
            var date = rewardEntitySorted[2].acquisitionDate
            for (i in 1 downTo 0) {
                assertEquals(true, rewardEntitySorted[i].isActive)
                assertEquals(false, rewardEntitySorted[i].isEscaped)
                assert(rewardEntitySorted[i].acquisitionDate < date)
                date = rewardEntitySorted[i].acquisitionDate
                assertEquals(date, rewardEntitySorted[i].acquisitionDate)
            }
        }
        //

    }

    @Test
    fun testGetAllRewardsEscapedAcquisitionDateDescOnEmptyTable() {
        runBlocking {
            assertTrue(rewardDao.getAllRewardsEscapedAcquisitionDateDesc().isEmpty())
        }
    }

    @Test
    fun testGetAllRewardsEscapedAcquisitionDateDesc() {

        val rewardsToInsertList = listOf(
            generateReward(escaped = true, yearAcquired = 1987, monthAcquired = 2, dayAcquired = 9),
            generateReward(
                escaped = false,
                yearAcquired = 1985,
                monthAcquired = 3,
                dayAcquired = 11
            ),
            generateReward(
                escaped = true,
                yearAcquired = 1984,
                monthAcquired = 4,
                dayAcquired = 13
            ),
            generateReward(
                escaped = false,
                yearAcquired = 1983,
                monthAcquired = 5,
                dayAcquired = 15
            ),
            generateReward(
                escaped = true,
                yearAcquired = 1982,
                monthAcquired = 6,
                dayAcquired = 17
            ),
            generateReward(
                escaped = true,
                yearAcquired = 1981,
                monthAcquired = 7,
                dayAcquired = 19
            ),
            generateReward(
                escaped = false,
                yearAcquired = 1980,
                monthAcquired = 8,
                dayAcquired = 21
            )
        )
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        assertTableSize(7)
        val rewardEntitySorted = runBlocking {
            rewardDao.getAllRewardsEscapedAcquisitionDateDesc()
        }
        assertNotNull(rewardEntitySorted)
        if (rewardEntitySorted != null) {
            assertEquals(4, rewardEntitySorted.size)
            assertEquals(true, rewardEntitySorted[rewardEntitySorted.size - 1].isActive)
            assertEquals(true, rewardEntitySorted[rewardEntitySorted.size - 1].isEscaped)
            var date = rewardEntitySorted[rewardEntitySorted.size - 1].acquisitionDate
            for (i in (rewardEntitySorted.size - 1) downTo 0) {
                assertEquals(true, rewardEntitySorted[i].isActive)
                assertEquals(true, rewardEntitySorted[i].isEscaped)
                assert(rewardEntitySorted[i].acquisitionDate < date)
                date = rewardEntitySorted[i].acquisitionDate
                assertEquals(date, rewardEntitySorted[i].acquisitionDate)
            }
        }
        //

    }

    @Test
    fun testGetAllRewardsOfSpecificLevelNotActiveOnEmptyTable() {
        runBlocking {
            assertTrue(rewardDao.getAllRewardsOfSpecificLevelNotActive(3).isEmpty())
        }
    }

    @Test
    fun testGetAllRewardsOfSpecificLevelNotActive() {

        val rewardsToInsertList = mutableListOf<RewardEntity>()
        rewardsToInsertList.addAll(generateRewards(5, 1, 3, active = false, escaped = false))
        rewardsToInsertList.addAll(generateRewards(3, 10, 3, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(3, 20, 3, active = true, escaped = true))
        rewardsToInsertList.addAll(generateRewards(6, 30, 4, active = false, escaped = false))
        rewardsToInsertList.addAll(generateRewards(7, 40, 4, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(7, 50, 4, active = true, escaped = true))
        rewardsToInsertList.addAll(generateRewards(2, 60, 5, active = true, escaped = true))
        //
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        assertTableSize(33)
        //
        val rewardEntitySorted3 = runBlocking {
            rewardDao.getAllRewardsOfSpecificLevelNotActive(3)
        }
        assertNotNull(rewardEntitySorted3)
        if (rewardEntitySorted3 != null) {
            assertEquals(5, rewardEntitySorted3.size)
            for (rewardEntity in rewardEntitySorted3) {
                assertEquals(false, rewardEntity.isActive)
                assertEquals(false, rewardEntity.isEscaped)
            }
        }
        //
        val rewardEntitySorted4 = runBlocking {
            rewardDao.getAllRewardsOfSpecificLevelNotActive(4)
        }
        assertNotNull(rewardEntitySorted4)
        if (rewardEntitySorted4 != null) {
            assertEquals(6, rewardEntitySorted4.size)
            for (rewardEntity in rewardEntitySorted4) {
                assertEquals(false, rewardEntity.isActive)
                assertEquals(false, rewardEntity.isEscaped)
            }
        }
        //
        val rewardEntitySorted5 = runBlocking {
            rewardDao.getAllRewardsOfSpecificLevelNotActive(5)
        }
        assertNotNull(rewardEntitySorted5)
        if (rewardEntitySorted5 != null) {
            assertEquals(0, rewardEntitySorted5.size)
        }
        //

    }

    @Test
    fun testGetAllRewardsOfSpecificLevelNotActiveOrEscapedOnEmptyTable() {
        runBlocking {
            assertTrue(rewardDao.getAllRewardsOfSpecificLevelNotActiveOrEscaped(2).isEmpty())
        }
    }

    @Test
    fun testGetAllRewardsOfSPecificLevelNotActiveOrEscaped() {

        val rewardsToInsertList = mutableListOf<RewardEntity>()
        rewardsToInsertList.addAll(generateRewards(5, 1, 3, active = false, escaped = false))
        rewardsToInsertList.addAll(generateRewards(7, 10, 3, active = true, escaped = true))
        rewardsToInsertList.addAll(generateRewards(6, 20, 4, active = false, escaped = false))
        rewardsToInsertList.addAll(generateRewards(3, 30, 4, active = true, escaped = true))
        rewardsToInsertList.addAll(generateRewards(8, 40, 4, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(9, 50, 3, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(2, 60, 5, active = true, escaped = false))
        //
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        assertTableSize(40)
        //
        val rewardEntitySorted3 = runBlocking {
            rewardDao.getAllRewardsOfSpecificLevelNotActiveOrEscaped(3)
        }
        assertNotNull(rewardEntitySorted3)
        if (rewardEntitySorted3 != null) {
            assertEquals(12, rewardEntitySorted3.size)
            for (rewardEntity in rewardEntitySorted3) {
                val desiredState =
                    (rewardEntity.isActive && rewardEntity.isEscaped) || (!rewardEntity.isActive && !rewardEntity.isEscaped)
                assertEquals(true, desiredState)
            }
        }
        //
        val rewardEntitySorted4 = runBlocking {
            rewardDao.getAllRewardsOfSpecificLevelNotActiveOrEscaped(4)
        }
        assertNotNull(rewardEntitySorted4)
        if (rewardEntitySorted4 != null) {
            assertEquals(9, rewardEntitySorted4.size)
            for (rewardEntity in rewardEntitySorted4) {
                val desiredState =
                    (rewardEntity.isActive && rewardEntity.isEscaped) || (!rewardEntity.isActive && !rewardEntity.isEscaped)
                assertEquals(true, desiredState)
            }
        }
        //
        val rewardEntitySorted5 = runBlocking {
            rewardDao.getAllRewardsOfSpecificLevelNotActiveOrEscaped(5)
        }
        assertNotNull(rewardEntitySorted5)
        if (rewardEntitySorted5 != null) {
            assertEquals(0, rewardEntitySorted5.size)
        }
        //

    }

    ///////////////////////////////////
    //COUNTS
    ///////////////////////////////////
    @Test
    fun testCountRewardsOnEmptyTable() {
        runBlocking {
            assertEquals(0, rewardDao.getNumberOfRows())
        }
    }

    @Test
    fun testCountRewards() {
        runBlocking {
            rewardDao.insert(generateRewards(125))
        }
        val totalRewards = runBlocking {
            rewardDao.getNumberOfRows()
        }
        assertEquals(125, totalRewards)
        assertTableSize(125)

    }

    @Test
    fun testCountRewardsActiveNotEscapedRewardsForLevelOnEmptyTable() {
        runBlocking {
            assertEquals(0, rewardDao.getNumberOfActiveNotEscapedRewardsForLevel(4))
        }
    }

    @Test
    fun testCountRewardsActiveNotEscapedRewardsForLevel() {

        val rewardsToInsertList = mutableListOf<RewardEntity>()
        rewardsToInsertList.addAll(generateRewards(3, 1, 1, active = true, escaped = true))
        rewardsToInsertList.addAll(generateRewards(5, 10, 3, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(7, 25, 3, active = true, escaped = true))
        rewardsToInsertList.addAll(generateRewards(9, 35, 5, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(13, 45, 5, active = true, escaped = true))

        assertEquals(37, rewardsToInsertList.size)

        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        assertTableSize(37)
        val numberLevel1 = runBlocking {
            rewardDao.getNumberOfActiveNotEscapedRewardsForLevel(1)
        }
        assertEquals(0, numberLevel1)
        //
        val numberLevel3 = runBlocking {
            rewardDao.getNumberOfActiveNotEscapedRewardsForLevel(3)
        }
        assertEquals(5, numberLevel3)
        //
        val numberLevel5 = runBlocking {
            rewardDao.getNumberOfActiveNotEscapedRewardsForLevel(5)
        }
        assertEquals(9, numberLevel5)

    }

    @Test
    fun testCountRewardsEscapedRewardsForLevelOnEmptyTable() {
        runBlocking {
            assertEquals(0, rewardDao.getNumberOfEscapedRewardsForLevel(1))
        }
    }

    @Test
    fun testCountRewardsEscapedRewardsForLevel() {

        val rewardsToInsertList = mutableListOf<RewardEntity>()
        rewardsToInsertList.addAll(generateRewards(3, 1, 1, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(5, 10, 3, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(7, 20, 3, active = true, escaped = true))
        rewardsToInsertList.addAll(generateRewards(9, 30, 5, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(13, 40, 5, active = true, escaped = true))

        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        assertTableSize(37)
        val numberLevel1 = runBlocking {
            rewardDao.getNumberOfEscapedRewardsForLevel(1)
        }
        assertEquals(0, numberLevel1)
        //
        val numberLevel3 = runBlocking {
            rewardDao.getNumberOfEscapedRewardsForLevel(3)
        }
        assertEquals(7, numberLevel3)
        //
        val numberLevel5 = runBlocking {
            rewardDao.getNumberOfEscapedRewardsForLevel(5)
        }
        assertEquals(13, numberLevel5)

    }

}
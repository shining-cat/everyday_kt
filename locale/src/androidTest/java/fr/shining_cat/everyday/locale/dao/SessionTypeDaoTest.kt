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
import androidx.test.platform.app.InstrumentationRegistry
import fr.shining_cat.everyday.locale.EveryDayRoomDatabase
import fr.shining_cat.everyday.locale.entities.SessionTypeEntity
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class SessionTypeDaoTest {

    //set the testing environment to use Main thread instead of background one
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sessionTypeDao: SessionTypeDao

    @Before
    fun setupEmptyTable() {
        tearDown()
        EveryDayRoomDatabase.TEST_MODE = true
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        sessionTypeDao = EveryDayRoomDatabase.getInstance(appContext).sessionTypeDao()
        emptyTableAndCheck()
    }

    /////////////////////////////
    //  UTILS
    /////////////////////////////
    private fun emptyTableAndCheck() {
        runBlocking {
            sessionTypeDao.deleteAllSessionTypes()
        }
        assertTableSize(0)
    }

    private fun assertTableSize(expectedCount: Int) {
        val count = runBlocking {
            sessionTypeDao.getNumberOfRows()
        }
        assertEquals(expectedCount, count)
    }

    @After
    fun tearDown() {
        EveryDayRoomDatabase.closeAndDestroy()
    }

    private fun generateSessionType(
        desiredId: Long = -1L,
        desiredName: String = "session type name",
        desiredDescription: String = "session type description",
        desiredColor: String = "session type color",
        desiredLastEditTime: Long = 890L
    ): SessionTypeEntity {
        val returnEntity = SessionTypeEntity(
            name = desiredName,
            description = desiredDescription,
            color = desiredColor,
            lastEditTime = desiredLastEditTime
        )
        if (desiredId != -1L) {
            returnEntity.id = desiredId
        }
        return returnEntity
    }

    private fun generateSessionTypes(
        numberOfEntities: Int = 1,
        startingId: Long = 1L,
        desiredId: Long = -1L,
        desiredName: String = "session type name",
        desiredDescription: String = "session type description",
        desiredColor: String = "session type color",
        desiredLastEditTime: Long = 890L
    ): List<SessionTypeEntity> {
        val returnList = mutableListOf<SessionTypeEntity>()
        for (i in startingId until (startingId + numberOfEntities)) {
            val oneSession = generateSessionType(
                desiredId = i.toLong(),
                desiredName = desiredName,
                desiredDescription = desiredDescription,
                desiredColor = desiredColor,
                desiredLastEditTime = desiredLastEditTime + i * 10
            )
            returnList.add(
                oneSession
            )
        }
        return returnList
    }

    /////////////////////////////
    //  INSERTS
    /////////////////////////////
    @Test
    fun testInsertSessionType() {
        insertAndCheckSessionTypeEntities()
        assertTableSize(20)
    }

    @Test
    fun testInsertWithConflict() {
        insertAndCheckSessionTypeEntities()
        assertTableSize(20)
        //insert same entities again to cause conflict
        insertAndCheckSessionTypeEntities()
        assertTableSize(20)
    }

    private fun insertAndCheckSessionTypeEntities() {
        val sessionTypeToInsertList = generateSessionTypes(20)
        assertEquals(20, sessionTypeToInsertList.size)
        val insertedIds = runBlocking {
            sessionTypeDao.insert(sessionTypeToInsertList)
        }
        assertEquals(20, insertedIds.size)
    }

    ///////////////////////////////////
    //DELETES
    ///////////////////////////////////

    @Test
    fun testDeleteSessionTypeFromEmptyTable() {
        val sessionTypeEntityToDeleteTest = generateSessionType(desiredId = 25)
        //
        assertTableSize(0)
        val countDeleted = runBlocking {
            sessionTypeDao.delete(sessionTypeEntityToDeleteTest)
        }
        assertEquals(0, countDeleted)
        assertTableSize(0)
    }

    @Test
    fun testDeleteNonExistentSessionType() {
        runBlocking {
            sessionTypeDao.insert(
                listOf(
                    generateSessionType(desiredId = 25),
                    generateSessionType(desiredId = 26),
                    generateSessionType(desiredId = 27),
                    generateSessionType(desiredId = 28),
                    generateSessionType(desiredId = 29),
                    generateSessionType(desiredId = 30)
                )
            )
        }
        val sessionTypeEntityToDeleteTest = generateSessionType(desiredId = 723)
        //
        assertTableSize(6)
        val countDeleted = runBlocking {
            sessionTypeDao.delete(sessionTypeEntityToDeleteTest)
        }
        assertEquals(0, countDeleted)
        assertTableSize(6)
    }

    @Test
    fun testDeleteOneSessionType() {
        val sessionTypeEntityToDeleteTest = generateSessionType(desiredId = 25)
        val sessionTypesToInsertList = mutableListOf<SessionTypeEntity>()
        sessionTypesToInsertList.add(sessionTypeEntityToDeleteTest)
        sessionTypesToInsertList.addAll(generateSessionTypes(50))
        runBlocking {
            sessionTypeDao.insert(sessionTypesToInsertList)
        }
        assertTableSize(50)
        //
        val countDeleted = runBlocking {
            sessionTypeDao.delete(sessionTypeEntityToDeleteTest)
        }
        assertEquals(1, countDeleted)
        //
        assertTableSize(49)

    }

    @Test
    fun testDeleteAllSessionTypeOnEmptyTable() {
        assertTableSize(0)
        val numberOfDeletedRows = runBlocking {
            sessionTypeDao.deleteAllSessionTypes()
        }
        assertEquals(0, numberOfDeletedRows)
        assertTableSize(0)
    }

    @Test
    fun testDeleteAllSessionType() {
        runBlocking {
            sessionTypeDao.insert(generateSessionTypes(73))
        }
        assertTableSize(73)
        val numberOfDeletedRows = runBlocking {
            sessionTypeDao.deleteAllSessionTypes()
        }
        assertEquals(73, numberOfDeletedRows)
        assertTableSize(0)
    }

    ///////////////////////////////////
    //GETS
    ///////////////////////////////////

    @Test
    fun testGetSessionTypesOnEmptyTable() {
        runBlocking {
            assertTableSize(0)
            val sessionTypeEntities = sessionTypeDao.getAllSessionTypesLastEditTimeDesc()
            Assert.assertTrue(sessionTypeEntities.isEmpty())
        }
    }

    @Test
    fun testGetSessionTypes() {
        runBlocking {
            sessionTypeDao.insert(generateSessionTypes(37))
            assertTableSize(37)
            val sessionTypeEntities = sessionTypeDao.getAllSessionTypesLastEditTimeDesc()
            assertEquals(37, sessionTypeEntities.size)
        }
    }

    @Test
    fun testGetSessionTypesOrderIsCorrect() {
        runBlocking {
            sessionTypeDao.insert(generateSessionTypes(37))
            assertTableSize(37)
            val sessionTypeEntities = sessionTypeDao.getAllSessionTypesLastEditTimeDesc()
            assertEquals(37, sessionTypeEntities.size)
            var lastEditTime = System.currentTimeMillis()
            for (sessionTypeEntity in sessionTypeEntities) {
                assertTrue(sessionTypeEntity.lastEditTime <= lastEditTime)
                lastEditTime = sessionTypeEntity.lastEditTime
            }
        }
    }

    ///////////////////////////////////
    //UPDATES
    ///////////////////////////////////

    @Test
    fun testUpdateOneSessionTypeOnEmptyTable() {
        assertTableSize(0)
        val sessionTypeEntityToUpdate = generateSessionType(desiredId = 73)
        val numberOfUpdatedItems = runBlocking {
            sessionTypeDao.update(sessionTypeEntityToUpdate)
        }
        assertEquals(0, numberOfUpdatedItems)
        assertTableSize(0)
    }

    @Test
    fun testUpdateNonExistentSessionType() {
        runBlocking {
            sessionTypeDao.insert(
                listOf(
                    generateSessionType(desiredId = 25),
                    generateSessionType(desiredId = 26),
                    generateSessionType(desiredId = 27),
                    generateSessionType(desiredId = 28),
                    generateSessionType(desiredId = 29)
                )
            )
        }
        assertTableSize(5)
        val sessionTypeEntityToUpdate = generateSessionType(desiredId = 73)
        val numberOfUpdatedItems = runBlocking {
            sessionTypeDao.update(sessionTypeEntityToUpdate)
        }
        assertEquals(0, numberOfUpdatedItems)
        assertTableSize(5)
    }

    @Test
    fun testUpdateOneSessionType() {
        val sessionTypeEntity = generateSessionType(
            desiredId = 43L,
            desiredName = "before update name",
            desiredDescription = "before update description",
            desiredColor = "before update color",
            desiredLastEditTime = 23L
        )

        val sessionTypeEntityInsertedTestID = runBlocking {
            sessionTypeDao.insert(listOf(sessionTypeEntity))
        }
        Assert.assertNotNull(sessionTypeEntityInsertedTestID)
        assertEquals(43L, sessionTypeEntityInsertedTestID[0])

        runBlocking {
            sessionTypeDao.insert(generateSessionTypes(53, 100))
        }
        assertTableSize(54)
        //
        sessionTypeEntity.name = "after update name"
        sessionTypeEntity.description = "after update description"
        sessionTypeEntity.color = "after update color"
        sessionTypeEntity.lastEditTime = 64L
        //
        val numberOfUpdatedItems = runBlocking {
            sessionTypeDao.update(sessionTypeEntity)
        }
        assertEquals(1, numberOfUpdatedItems)
        assertTableSize(54)
        val sessionTypeEntityUpdated = runBlocking {
            sessionTypeDao.getAllSessionTypesLastEditTimeDesc().filter { it.id == 43L }[0]
        }
        Assert.assertNotNull(sessionTypeEntityUpdated)
        assertEquals(
            "after update name",
            sessionTypeEntity.name
        )
        assertEquals(
            "after update description",
            sessionTypeEntity.description
        )
        assertEquals(
            "after update color",
            sessionTypeEntity.color
        )
        assertEquals(64L, sessionTypeEntity.lastEditTime)
    }
}
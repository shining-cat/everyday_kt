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
import fr.shining_cat.everyday.locale.entities.SessionPresetEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SessionPresetDaoTest {

    // set the testing environment to use Main thread instead of background one
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sessionPresetDao: SessionPresetDao

    @Before
    fun setupEmptyTable() {
        tearDown()
        EveryDayRoomDatabase.TEST_MODE = true
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        sessionPresetDao = EveryDayRoomDatabase.getInstance(appContext).sessionPresetDao()
        emptyTableAndCheck()
    }

    // ///////////////////////////
    //  UTILS
    // ///////////////////////////
    private fun emptyTableAndCheck() {
        runBlocking {
            sessionPresetDao.deleteAllSessionPresets()
        }
        assertTableSize(0)
    }

    private fun assertTableSize(expectedCount: Int) {
        val count = runBlocking {
            sessionPresetDao.getNumberOfRows()
        }
        assertEquals(expectedCount, count)
    }

    @After
    fun tearDown() {
        EveryDayRoomDatabase.closeAndDestroy()
    }

    private fun generateSessionPreset(
        desiredId: Long = -1L,
        desiredDuration: Long = 123L,
        desiredStartAndEndSoundUri: String = "start end sound",
        desiredIntermediateIntervalLength: Long = 456L,
        desiredStartCountdownLength: Long = 789L,
        desiredIntermediateIntervalRandom: Boolean = false,
        desiredIntermediateIntervalSoundUri: String = "interval sound",
        desiredAudioGuideSoundUri: String = "audio guide sound",
        desiredVibration: Boolean = false,
        desiredLastEditTime: Long = 890L,
        sessionTypeId: Long = 901L,
        sessionPresetType: String = "session preset default type",
        startAndEndSoundName: String = "start and end sound name default",
        audioGuideSoundAlbumName: String = "sound album name default",
        audioGuideSoundArtistName: String = "sound artist name default",
        audioGuideSoundTitle: String = "sound title name default",
        intermediateIntervalSoundName: String = "interval sound name default",
    ): SessionPresetEntity {
        val returnEntity = if (desiredId != -1L) {
            SessionPresetEntity(
                id = desiredId,
                duration = desiredDuration,
                startAndEndSoundUri = desiredStartAndEndSoundUri,
                intermediateIntervalLength = desiredIntermediateIntervalLength,
                startCountdownLength = desiredStartCountdownLength,
                intermediateIntervalRandom = desiredIntermediateIntervalRandom,
                intermediateIntervalSoundUri = desiredIntermediateIntervalSoundUri,
                audioGuideSoundUri = desiredAudioGuideSoundUri,
                vibration = desiredVibration,
                lastEditTime = desiredLastEditTime,
                sessionTypeId = sessionTypeId,
                sessionPresetType = sessionPresetType,
                startAndEndSoundName = startAndEndSoundName,
                audioGuideSoundAlbumName = audioGuideSoundAlbumName,
                audioGuideSoundArtistName = audioGuideSoundArtistName,
                audioGuideSoundTitle = audioGuideSoundTitle,
                intermediateIntervalSoundName = intermediateIntervalSoundName
            )
        }
        else {
            SessionPresetEntity(
                duration = desiredDuration,
                startAndEndSoundUri = desiredStartAndEndSoundUri,
                intermediateIntervalLength = desiredIntermediateIntervalLength,
                startCountdownLength = desiredStartCountdownLength,
                intermediateIntervalRandom = desiredIntermediateIntervalRandom,
                intermediateIntervalSoundUri = desiredIntermediateIntervalSoundUri,
                audioGuideSoundUri = desiredAudioGuideSoundUri,
                vibration = desiredVibration,
                lastEditTime = desiredLastEditTime,
                sessionTypeId = sessionTypeId,
                sessionPresetType = sessionPresetType,
                startAndEndSoundName = startAndEndSoundName,
                audioGuideSoundAlbumName = audioGuideSoundAlbumName,
                audioGuideSoundArtistName = audioGuideSoundArtistName,
                audioGuideSoundTitle = audioGuideSoundTitle,
                intermediateIntervalSoundName = intermediateIntervalSoundName
            )
        }
        return returnEntity
    }

    private fun generateSessionPresets(
        numberOfEntities: Int = 1,
        startingId: Long = 1L,
        desiredDuration: Long = 123L,
        desiredStartAndEndSoundUri: String = "start end sound",
        desiredIntermediateIntervalLength: Long = 456L,
        desiredStartCountdownLength: Long = 789L,
        desiredIntermediateIntervalRandom: Boolean = false,
        desiredIntermediateIntervalSoundUri: String = "interval sound",
        desiredAudioGuideSoundUri: String = "audio guide sound",
        desiredVibration: Boolean = false,
        desiredLastEditTime: Long = 890L
    ): List<SessionPresetEntity> {
        val returnList = mutableListOf<SessionPresetEntity>()
        for (i in startingId until (startingId + numberOfEntities)) {
            val oneSession = generateSessionPreset(
                desiredId = i.toLong(),
                desiredDuration = desiredDuration,
                desiredStartAndEndSoundUri = desiredStartAndEndSoundUri,
                desiredIntermediateIntervalLength = desiredIntermediateIntervalLength,
                desiredStartCountdownLength = desiredStartCountdownLength,
                desiredIntermediateIntervalRandom = desiredIntermediateIntervalRandom,
                desiredIntermediateIntervalSoundUri = desiredIntermediateIntervalSoundUri,
                desiredAudioGuideSoundUri = desiredAudioGuideSoundUri,
                desiredVibration = desiredVibration,
                desiredLastEditTime = desiredLastEditTime + i * 10
            )
            returnList.add(
                oneSession
            )
        }
        return returnList
    }

    // ///////////////////////////
    //  INSERTS
    // ///////////////////////////
    @Test
    fun testInsertSessionPreset() {
        insertAndCheckSessionPresetEntities()
        assertTableSize(20)
    }

    @Test
    fun testInsertWithConflict() {
        insertAndCheckSessionPresetEntities()
        assertTableSize(20)
        // insert same entities again to cause conflict
        insertAndCheckSessionPresetEntities()
        assertTableSize(20)
    }

    private fun insertAndCheckSessionPresetEntities() {
        val sessionPresetToInsertList = generateSessionPresets(20)
        assertEquals(20, sessionPresetToInsertList.size)
        val insertedIds = runBlocking {
            sessionPresetDao.insert(sessionPresetToInsertList)
        }
        assertEquals(20, insertedIds.size)
    }

    // /////////////////////////////////
    // DELETES
    // /////////////////////////////////

    @Test
    fun testDeleteSessionPresetFromEmptyTable() {
        val sessionPresetEntityToDeleteTest = generateSessionPreset(desiredId = 25)
        //
        assertTableSize(0)
        val countDeleted = runBlocking {
            sessionPresetDao.delete(sessionPresetEntityToDeleteTest)
        }
        assertEquals(0, countDeleted)
        assertTableSize(0)
    }

    @Test
    fun testDeleteNonExistentSessionPreset() {
        runBlocking {
            sessionPresetDao.insert(
                listOf(
                    generateSessionPreset(desiredId = 25),
                    generateSessionPreset(desiredId = 26),
                    generateSessionPreset(desiredId = 27),
                    generateSessionPreset(desiredId = 28),
                    generateSessionPreset(desiredId = 29),
                    generateSessionPreset(desiredId = 30)
                )
            )
        }
        val sessionPresetEntityToDeleteTest = generateSessionPreset(desiredId = 723)
        //
        assertTableSize(6)
        val countDeleted = runBlocking {
            sessionPresetDao.delete(sessionPresetEntityToDeleteTest)
        }
        assertEquals(0, countDeleted)
        assertTableSize(6)
    }

    @Test
    fun testDeleteOneSessionPreset() {
        val sessionPresetEntityToDeleteTest = generateSessionPreset(desiredId = 25)
        val sessionPresetsToInsertList = mutableListOf<SessionPresetEntity>()
        sessionPresetsToInsertList.add(sessionPresetEntityToDeleteTest)
        sessionPresetsToInsertList.addAll(generateSessionPresets(50))
        runBlocking {
            sessionPresetDao.insert(sessionPresetsToInsertList)
        }
        assertTableSize(50)
        //
        val countDeleted = runBlocking {
            sessionPresetDao.delete(sessionPresetEntityToDeleteTest)
        }
        assertEquals(1, countDeleted)
        //
        assertTableSize(49)
    }

    @Test
    fun testDeleteAllSessionPresetOnEmptyTable() {
        assertTableSize(0)
        val numberOfDeletedRows = runBlocking {
            sessionPresetDao.deleteAllSessionPresets()
        }
        assertEquals(0, numberOfDeletedRows)
        assertTableSize(0)
    }

    @Test
    fun testDeleteAllSessionPreset() {
        runBlocking {
            sessionPresetDao.insert(generateSessionPresets(73))
        }
        assertTableSize(73)
        val numberOfDeletedRows = runBlocking {
            sessionPresetDao.deleteAllSessionPresets()
        }
        assertEquals(73, numberOfDeletedRows)
        assertTableSize(0)
    }

    // /////////////////////////////////
    // GETS
    // /////////////////////////////////

    @Test
    fun testGetSessionPresetsOnEmptyTable() {
        runBlocking {
            assertTableSize(0)
            val sessionPresetEntities = sessionPresetDao.getAllSessionPresetsLastEditTimeDesc()
            Assert.assertTrue(sessionPresetEntities.isEmpty())
        }
    }

    @Test
    fun testGetSessionPresets() {
        runBlocking {
            sessionPresetDao.insert(generateSessionPresets(37))
            assertTableSize(37)
            val sessionPresetEntities = sessionPresetDao.getAllSessionPresetsLastEditTimeDesc()
            assertEquals(37, sessionPresetEntities.size)
        }
    }

    @Test
    fun testGetSessionPresetsOrderIsCorrect() {
        runBlocking {
            sessionPresetDao.insert(generateSessionPresets(37))
            assertTableSize(37)
            val sessionPresetEntities = sessionPresetDao.getAllSessionPresetsLastEditTimeDesc()
            assertEquals(37, sessionPresetEntities.size)
            var lastEditTime = System.currentTimeMillis()
            for (sessionPresetEntity in sessionPresetEntities) {
                assertTrue(sessionPresetEntity.lastEditTime <= lastEditTime)
                lastEditTime = sessionPresetEntity.lastEditTime
            }
        }
    }

    // /////////////////////////////////
    // UPDATES
    // /////////////////////////////////

    @Test
    fun testUpdateOneSessionPresetOnEmptyTable() {
        assertTableSize(0)
        val sessionPresetEntityToUpdate = generateSessionPreset(desiredId = 73)
        val numberOfUpdatedItems = runBlocking {
            sessionPresetDao.update(sessionPresetEntityToUpdate)
        }
        assertEquals(0, numberOfUpdatedItems)
        assertTableSize(0)
    }

    @Test
    fun testUpdateNonExistentSessionPreset() {
        runBlocking {
            sessionPresetDao.insert(
                listOf(
                    generateSessionPreset(desiredId = 25),
                    generateSessionPreset(desiredId = 26),
                    generateSessionPreset(desiredId = 27),
                    generateSessionPreset(desiredId = 28),
                    generateSessionPreset(desiredId = 29)
                )
            )
        }
        assertTableSize(5)
        val sessionPresetEntityToUpdate = generateSessionPreset(desiredId = 73)
        val numberOfUpdatedItems = runBlocking {
            sessionPresetDao.update(sessionPresetEntityToUpdate)
        }
        assertEquals(0, numberOfUpdatedItems)
        assertTableSize(5)
    }

    @Test
    fun testUpdateOneSessionPreset() {
        val sessionPresetEntity = generateSessionPreset(
            desiredId = 43,
            desiredDuration = 1234L,
            desiredStartAndEndSoundUri = "before update desiredStartAndEndSoundUri",
            desiredIntermediateIntervalLength = 2345L,
            desiredStartCountdownLength = 3456L,
            desiredIntermediateIntervalRandom = true,
            desiredIntermediateIntervalSoundUri = "before update desiredIntermediateIntervalSoundUri",
            desiredAudioGuideSoundUri = "before update desiredAudioGuideSoundUri",
            desiredVibration = false
        )

        val sessionPresetEntityInsertedTestID = runBlocking {
            sessionPresetDao.insert(listOf(sessionPresetEntity))
        }
        Assert.assertNotNull(sessionPresetEntityInsertedTestID)
        assertEquals(43L, sessionPresetEntityInsertedTestID[0])

        runBlocking {
            sessionPresetDao.insert(generateSessionPresets(53, 100))
        }
        assertTableSize(54)
        //
        val updatingSessionPresetEntity = generateSessionPreset(
            desiredId = 43L,
            desiredDuration = 4321L,
            desiredStartAndEndSoundUri = "after update desiredStartAndEndSoundUri",
            desiredIntermediateIntervalLength = 5432L,
            desiredStartCountdownLength = 6543L,
            desiredIntermediateIntervalRandom = false,
            desiredIntermediateIntervalSoundUri = "after update desiredIntermediateIntervalSoundUri",
            desiredAudioGuideSoundUri = "after update desiredAudioGuideSoundUri",
            desiredVibration = true,
            desiredLastEditTime = 74L
        )
        //
        val numberOfUpdatedItems = runBlocking {
            sessionPresetDao.update(updatingSessionPresetEntity)
        }
        assertEquals(1, numberOfUpdatedItems)
        assertTableSize(54)
        val sessionPresetEntityUpdated = runBlocking {
            sessionPresetDao.getAllSessionPresetsLastEditTimeDesc().filter {it.id == 43L}[0]
        }
        Assert.assertNotNull(sessionPresetEntityUpdated)
        assertEquals(4321L, sessionPresetEntityUpdated.duration)
        assertEquals(
            "after update desiredStartAndEndSoundUri", sessionPresetEntityUpdated.startAndEndSoundUri
        )
        assertEquals(5432L, sessionPresetEntityUpdated.intermediateIntervalLength)
        assertEquals(6543L, sessionPresetEntityUpdated.startCountdownLength)
        assertEquals(false, sessionPresetEntityUpdated.intermediateIntervalRandom)
        assertEquals(
            "after update desiredIntermediateIntervalSoundUri", sessionPresetEntityUpdated.intermediateIntervalSoundUri
        )
        assertEquals(
            "after update desiredAudioGuideSoundUri", sessionPresetEntityUpdated.audioGuideSoundUri
        )
        assertEquals(true, sessionPresetEntityUpdated.vibration)
        assertEquals(74L, sessionPresetEntityUpdated.lastEditTime)
    }
}
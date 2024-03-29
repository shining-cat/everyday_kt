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

package fr.shining_cat.everyday.repository.converters

import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.locale.entities.RewardEntity
import fr.shining_cat.everyday.models.reward.Level
import fr.shining_cat.everyday.models.reward.Reward
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class RewardConverterTest {

    @MockK
    private lateinit var mockLogger: Logger

    private lateinit var rewardConverter: RewardConverter

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        assertNotNull(mockLogger)
        rewardConverter = RewardConverter(mockLogger)
    }

    val reward = Reward(
        id = 29,
        flowerKey = 0,
        mouthKey = 1,
        legsKey = 2,
        armsKey = 3,
        eyesKey = 4,
        hornsKey = 5,
        level = Level.fromKey(3),
        acquisitionDate = convertDateToMilliSinceEpoch(2000, 5, 13, 1, 2, 3),
        escapingDate = convertDateToMilliSinceEpoch(2001, 6, 25, 1, 2, 3),
        isActive = true,
        isEscaped = false,
        name = "this is my name",
        legsColor = "#FF000000",
        bodyColor = "#00FF0000",
        armsColor = "#0000FF00"
    )

    val rewardEntity = RewardEntity(
        id = 29,
        flower = 0,
        mouth = 1,
        legs = 2,
        arms = 3,
        eyes = 4,
        horns = 5,
        level = 3,
        acquisitionDate = convertDateToMilliSinceEpoch(2000, 5, 13, 1, 2, 3),
        escapingDate = convertDateToMilliSinceEpoch(2001, 6, 25, 1, 2, 3),
        isActive = true,
        isEscaped = false,
        name = "this is my name",
        legsColor = "#FF000000",
        bodyColor = "#00FF0000",
        armsColor = "#0000FF00"
    )

    val rewardNoId = Reward(
        id = -1L,
        flowerKey = 0,
        mouthKey = 1,
        legsKey = 2,
        armsKey = 3,
        eyesKey = 4,
        hornsKey = 5,
        level = Level.fromKey(3),
        acquisitionDate = convertDateToMilliSinceEpoch(2000, 5, 13, 1, 2, 3),
        escapingDate = convertDateToMilliSinceEpoch(2001, 6, 25, 1, 2, 3),
        isActive = true,
        isEscaped = false,
        name = "this is my name",
        legsColor = "#FF000000",
        bodyColor = "#00FF0000",
        armsColor = "#0000FF00"
    )

    val rewardEntityNoId = RewardEntity(
        id = null,
        flower = 0,
        mouth = 1,
        legs = 2,
        arms = 3,
        eyes = 4,
        horns = 5,
        level = 3,
        acquisitionDate = convertDateToMilliSinceEpoch(2000, 5, 13, 1, 2, 3),
        escapingDate = convertDateToMilliSinceEpoch(2001, 6, 25, 1, 2, 3),
        isActive = true,
        isEscaped = false,
        name = "this is my name",
        legsColor = "#FF000000",
        bodyColor = "#00FF0000",
        armsColor = "#0000FF00"
    )

    // ////////////////////////////////
    @Test
    fun convertModelToEntity() {
        val convertedModel = runBlocking {
            rewardConverter.convertModelToEntity(reward)
        }
        assertEquals(
            rewardEntity, convertedModel
        )
    }

    @Test
    fun convertEntitytoModel() {
        val convertedEntity = runBlocking {
            rewardConverter.convertEntitytoModel(rewardEntity)
        }
        assertEquals(
            reward, convertedEntity
        )
    }

    @Test
    fun convertModelToEntityNoID() {
        val convertedModel = runBlocking {
            rewardConverter.convertModelToEntity(rewardNoId)
        }
        assertEquals(
            rewardEntityNoId, convertedModel
        )
    }

    @Test
    fun convertEntitytoModelNoId() {
        val convertedEntity = runBlocking {
            rewardConverter.convertEntitytoModel(rewardEntityNoId)
        }
        assertEquals(
            rewardNoId, convertedEntity
        )
    }

    private fun convertDateToMilliSinceEpoch(
        year: Int,
        month: Int,
        dayOfMonth: Int,
        hourOfDay: Int,
        minutes: Int,
        seconds: Int
    ): Long {
        val zone = ZoneId.systemDefault()
        val rules = zone.rules
        val offset = rules.getOffset(Instant.now())

        val dateAsLong = LocalDateTime.of(
            year, month, dayOfMonth, hourOfDay, minutes, seconds
        ).toInstant(offset).toEpochMilli()
        return dateAsLong
    }
}
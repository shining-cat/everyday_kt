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
import fr.shining_cat.everyday.locale.entities.SessionTypeEntity
import fr.shining_cat.everyday.models.SessionType
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class SessionTypeConverterTest {

    @MockK
    private lateinit var mockLogger: Logger

    private lateinit var sessionTypeConverter: SessionTypeConverter

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        assertNotNull(mockLogger)
        sessionTypeConverter = SessionTypeConverter(mockLogger)
    }

    val sessionType = SessionType(
        id = 123L,
        name = "name",
        description = "description",
        color = "color",
        lastEditTime = 234L
    )

    val sessionTypeEntity = SessionTypeEntity(
        id = 123L,
        name = "name",
        description = "description",
        color = "color",
        lastEditTime = 234L
    )

    // ////////////////////////////////
    @Test
    fun convertModelToEntity() {
        val convertedModel = runBlocking {
            sessionTypeConverter.convertModelToEntity(sessionType)
        }
        assertEquals(sessionTypeEntity, convertedModel)
    }

    @Test
    fun convertEntitytoModel() {
        val convertedEntity = runBlocking {
            sessionTypeConverter.convertEntitytoModel(sessionTypeEntity)
        }
        assertEquals(sessionType, convertedEntity)
    }
}

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
import fr.shining_cat.everyday.locale.entities.SessionPresetEntity
import fr.shining_cat.everyday.models.SessionPreset
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import kotlin.math.exp

class SessionPresetConverterTest {

    @MockK
    private lateinit var mockLogger: Logger

    private lateinit var sessionPresetConverter: SessionPresetConverter

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        assertNotNull(mockLogger)
        sessionPresetConverter = SessionPresetConverter(mockLogger)
    }

    ////////////////////////
    // testing AudioSessionPreset conversions
    @Test
    fun `convertModelToEntity audio`() {
        val model = SessionPreset.AudioSessionPreset(
            id = 123L,
            startCountdownLength = 456L,
            startAndEndSoundUriString = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            duration = 234L,
            audioGuideSoundUriString = "audioGuideSoundUri",
            audioGuideSoundArtistName = "audioGuideSoundArtistName",
            audioGuideSoundAlbumName = "audioGuideSoundAlbumName",
            audioGuideSoundTitle = "audioGuideSoundFileName",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L
        )
        val convertedModel = runBlocking {
            sessionPresetConverter.convertModelToEntity(model)
        }
        val expectedEntity = SessionPresetEntity(
            id = 123L,
            startCountdownLength = 456L,
            startAndEndSoundUri = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = -1L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUri = "",
            intermediateIntervalSoundName = "",
            duration = 234L,
            audioGuideSoundUri = "audioGuideSoundUri",
            audioGuideSoundArtistName = "audioGuideSoundArtistName",
            audioGuideSoundAlbumName = "audioGuideSoundAlbumName",
            audioGuideSoundTitle = "audioGuideSoundFileName",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L,
            sessionPresetType = SessionPresetConverter.SessionPresetType.AUDIO.name
        )
        assertEquals(
            expectedEntity,
            convertedModel
        )
    }

    @Test
    fun `convertModelToEntity No Id audio`() {
        val model = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = 456L,
            startAndEndSoundUriString = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            duration = 234L,
            audioGuideSoundUriString = "audioGuideSoundUri",
            audioGuideSoundArtistName = "audioGuideSoundArtistName",
            audioGuideSoundAlbumName = "audioGuideSoundAlbumName",
            audioGuideSoundTitle = "audioGuideSoundFileName",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L
        )
        val convertedModel = runBlocking {
            sessionPresetConverter.convertModelToEntity(model)
        }
        val expectedEntity = SessionPresetEntity(
            id = null,
            startCountdownLength = 456L,
            startAndEndSoundUri = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = -1L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUri = "",
            intermediateIntervalSoundName = "",
            duration = 234L,
            audioGuideSoundUri = "audioGuideSoundUri",
            audioGuideSoundArtistName = "audioGuideSoundArtistName",
            audioGuideSoundAlbumName = "audioGuideSoundAlbumName",
            audioGuideSoundTitle = "audioGuideSoundFileName",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L,
            sessionPresetType = SessionPresetConverter.SessionPresetType.AUDIO.name
        )
        assertEquals(
            expectedEntity,
            convertedModel
        )
    }

    @Test
    fun `convertEntitytoModel audio`() {
        val entity = SessionPresetEntity(
            id = 123L,
            startCountdownLength = 456L,
            startAndEndSoundUri = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = -1L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUri = "",
            intermediateIntervalSoundName = "",
            duration = 234L,
            audioGuideSoundUri = "audioGuideSoundUri",
            audioGuideSoundArtistName = "audioGuideSoundArtistName",
            audioGuideSoundAlbumName = "audioGuideSoundAlbumName",
            audioGuideSoundTitle = "audioGuideSoundFileName",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L,
            sessionPresetType = SessionPresetConverter.SessionPresetType.AUDIO.name
        )
        val convertedEntity = runBlocking {
            sessionPresetConverter.convertEntityToModel(entity)
        }
        val expectedModel = SessionPreset.AudioSessionPreset(
            id = 123L,
            startCountdownLength = 456L,
            startAndEndSoundUriString = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            duration = 234L,
            audioGuideSoundUriString = "audioGuideSoundUri",
            audioGuideSoundArtistName = "audioGuideSoundArtistName",
            audioGuideSoundAlbumName = "audioGuideSoundAlbumName",
            audioGuideSoundTitle = "audioGuideSoundFileName",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L
        )
        assertEquals(
            expectedModel,
            convertedEntity
        )
    }

    @Test
    fun `convertEntitytoModel No Id audio`() {
        val entity = SessionPresetEntity(
            id = null,
            startCountdownLength = 456L,
            startAndEndSoundUri = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = -1L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUri = "",
            intermediateIntervalSoundName = "",
            duration = 234L,
            audioGuideSoundUri = "audioGuideSoundUri",
            audioGuideSoundArtistName = "audioGuideSoundArtistName",
            audioGuideSoundAlbumName = "audioGuideSoundAlbumName",
            audioGuideSoundTitle = "audioGuideSoundFileName",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L,
            sessionPresetType = SessionPresetConverter.SessionPresetType.AUDIO.name
        )
        val convertedEntity = runBlocking {
            sessionPresetConverter.convertEntityToModel(entity)
        }
        val expectedModel = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = 456L,
            startAndEndSoundUriString = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            duration = 234L,
            audioGuideSoundUriString = "audioGuideSoundUri",
            audioGuideSoundArtistName = "audioGuideSoundArtistName",
            audioGuideSoundAlbumName = "audioGuideSoundAlbumName",
            audioGuideSoundTitle = "audioGuideSoundFileName",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L
        )
        assertEquals(
            expectedModel,
            convertedEntity
        )
    }

    ////////////////////////
    // testing AudioFreeSessionPreset conversions
    @Test
    fun `convertModelToEntity audio free`() {
        val model = SessionPreset.AudioFreeSessionPreset(
            id = 123L,
            startCountdownLength = 456L,
            startAndEndSoundUriString = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L
        )
        val convertedModel = runBlocking {
            sessionPresetConverter.convertModelToEntity(model)
        }
        val expectedEntity = SessionPresetEntity(
            id = 123L,
            startCountdownLength = 456L,
            startAndEndSoundUri = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = -1L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUri = "",
            intermediateIntervalSoundName = "",
            duration = -1L,
            audioGuideSoundUri = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L,
            sessionPresetType = SessionPresetConverter.SessionPresetType.AUDIO_FREE.name
        )
        assertEquals(
            expectedEntity,
            convertedModel
        )
    }

    @Test
    fun `convertModelToEntity No Id audio free`() {
        val model = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = 456L,
            startAndEndSoundUriString = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L
        )
        val convertedModel = runBlocking {
            sessionPresetConverter.convertModelToEntity(model)
        }
        val expectedEntity = SessionPresetEntity(
            id = null,
            startCountdownLength = 456L,
            startAndEndSoundUri = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = -1L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUri = "",
            intermediateIntervalSoundName = "",
            duration = -1L,
            audioGuideSoundUri = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L,
            sessionPresetType = SessionPresetConverter.SessionPresetType.AUDIO_FREE.name
        )
        assertEquals(
            expectedEntity,
            convertedModel
        )
    }

    @Test
    fun `convertEntitytoModel audio free`() {
        val entity = SessionPresetEntity(
            id = 123L,
            startCountdownLength = 456L,
            startAndEndSoundUri = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = -1L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUri = "",
            intermediateIntervalSoundName = "",
            duration = -1L,
            audioGuideSoundUri = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L,
            sessionPresetType = SessionPresetConverter.SessionPresetType.AUDIO_FREE.name
        )
        val convertedEntity = runBlocking {
            sessionPresetConverter.convertEntityToModel(entity)
        }
        val expectedModel = SessionPreset.AudioFreeSessionPreset(
            id = 123L,
            startCountdownLength = 456L,
            startAndEndSoundUriString = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L
        )
        assertEquals(
            expectedModel,
            convertedEntity
        )
    }

    @Test
    fun `convertEntitytoModel No Id audio free`() {
        val entity = SessionPresetEntity(
            id = null,
            startCountdownLength = 456L,
            startAndEndSoundUri = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = -1L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUri = "",
            intermediateIntervalSoundName = "",
            duration = -1L,
            audioGuideSoundUri = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L,
            sessionPresetType = SessionPresetConverter.SessionPresetType.AUDIO_FREE.name
        )
        val convertedEntity = runBlocking {
            sessionPresetConverter.convertEntityToModel(entity)
        }
        val expectedModel = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = 456L,
            startAndEndSoundUriString = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L
        )
        assertEquals(
            expectedModel,
            convertedEntity
        )
    }

    ////////////////////////
    // testing AudioFreeSessionPreset conversions
    @Test
    fun `convertModelToEntity timed`() {
        val model = SessionPreset.TimedSessionPreset(
            id = 123L,
            startCountdownLength = 456L,
            startAndEndSoundUriString = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = 345L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUriString = "intermediateIntervalSoundUri",
            intermediateIntervalSoundName = "intermediateIntervalSoundName",
            duration = 234L,
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L
        )
        val convertedModel = runBlocking {
            sessionPresetConverter.convertModelToEntity(model)
        }
        val expectedEntity = SessionPresetEntity(
            id = 123L,
            startCountdownLength = 456L,
            startAndEndSoundUri = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = 345L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUri = "intermediateIntervalSoundUri",
            intermediateIntervalSoundName = "intermediateIntervalSoundName",
            duration = 234L,
            audioGuideSoundUri = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L,
            sessionPresetType = SessionPresetConverter.SessionPresetType.TIMED.name
        )
        assertEquals(
            expectedEntity,
            convertedModel
        )
    }

    @Test
    fun `convertModelToEntity No Id timed`() {
        val model = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = 456L,
            startAndEndSoundUriString = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = 345L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUriString = "intermediateIntervalSoundUri",
            intermediateIntervalSoundName = "intermediateIntervalSoundName",
            duration = 234L,
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L
        )
        val convertedModel = runBlocking {
            sessionPresetConverter.convertModelToEntity(model)
        }
        val expectedEntity = SessionPresetEntity(
            id = null,
            startCountdownLength = 456L,
            startAndEndSoundUri = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = 345L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUri = "intermediateIntervalSoundUri",
            intermediateIntervalSoundName = "intermediateIntervalSoundName",
            duration = 234L,
            audioGuideSoundUri = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L,
            sessionPresetType = SessionPresetConverter.SessionPresetType.TIMED.name
        )
        assertEquals(
            expectedEntity,
            convertedModel
        )
    }

    @Test
    fun `convertEntitytoModel timed`() {
        val entity = SessionPresetEntity(
            id = 123L,
            startCountdownLength = 456L,
            startAndEndSoundUri = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = 345L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUri = "intermediateIntervalSoundUri",
            intermediateIntervalSoundName = "intermediateIntervalSoundName",
            duration = 234L,
            audioGuideSoundUri = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L,
            sessionPresetType = SessionPresetConverter.SessionPresetType.TIMED.name
        )
        val convertedEntity = runBlocking {
            sessionPresetConverter.convertEntityToModel(entity)
        }
        val expectedModel = SessionPreset.TimedSessionPreset(
            id = 123L,
            startCountdownLength = 456L,
            startAndEndSoundUriString = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = 345L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUriString = "intermediateIntervalSoundUri",
            intermediateIntervalSoundName = "intermediateIntervalSoundName",
            duration = 234L,
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L
        )
        assertEquals(
            expectedModel,
            convertedEntity
        )
    }

    @Test
    fun `convertEntitytoModel No Id timed`() {
        val entity = SessionPresetEntity(
            id = null,
            startCountdownLength = 456L,
            startAndEndSoundUri = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = 345L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUri = "intermediateIntervalSoundUri",
            intermediateIntervalSoundName = "intermediateIntervalSoundName",
            duration = 234L,
            audioGuideSoundUri = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L,
            sessionPresetType = SessionPresetConverter.SessionPresetType.TIMED.name
        )
        val convertedEntity = runBlocking {
            sessionPresetConverter.convertEntityToModel(entity)
        }
        val expectedModel = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = 456L,
            startAndEndSoundUriString = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = 345L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUriString = "intermediateIntervalSoundUri",
            intermediateIntervalSoundName = "intermediateIntervalSoundName",
            duration = 234L,
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L
        )
        assertEquals(
            expectedModel,
            convertedEntity
        )
    }

    ////////////////////////
    // testing AudioFreeSessionPreset conversions
    @Test
    fun `convertModelToEntity timed free`() {
        val model = SessionPreset.TimedFreeSessionPreset(
            id = 123L,
            startCountdownLength = 456L,
            startAndEndSoundUriString = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = 345L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUriString = "intermediateIntervalSoundUri",
            intermediateIntervalSoundName = "intermediateIntervalSoundName",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L
        )

        val convertedModel = runBlocking {
            sessionPresetConverter.convertModelToEntity(model)
        }
        val expectedEntity = SessionPresetEntity(
            id = 123L,
            startCountdownLength = 456L,
            startAndEndSoundUri = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = 345L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUri = "intermediateIntervalSoundUri",
            intermediateIntervalSoundName = "intermediateIntervalSoundName",
            duration = -1L,
            audioGuideSoundUri = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L,
            sessionPresetType = SessionPresetConverter.SessionPresetType.TIMED_FREE.name
        )
        assertEquals(
            expectedEntity,
            convertedModel
        )
    }

    @Test
    fun `convertModelToEntity No Id timed free`() {
        val model = SessionPreset.TimedFreeSessionPreset(
            id = -1L,
            startCountdownLength = 456L,
            startAndEndSoundUriString = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = 345L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUriString = "intermediateIntervalSoundUri",
            intermediateIntervalSoundName = "intermediateIntervalSoundName",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L
        )

        val convertedModel = runBlocking {
            sessionPresetConverter.convertModelToEntity(model)
        }
        val expectedEntity = SessionPresetEntity(
            id = null,
            startCountdownLength = 456L,
            startAndEndSoundUri = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = 345L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUri = "intermediateIntervalSoundUri",
            intermediateIntervalSoundName = "intermediateIntervalSoundName",
            duration = -1L,
            audioGuideSoundUri = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L,
            sessionPresetType = SessionPresetConverter.SessionPresetType.TIMED_FREE.name
        )
        assertEquals(
            expectedEntity,
            convertedModel
        )
    }

    @Test
    fun `convertEntitytoModel timed free`() {
        val entity = SessionPresetEntity(
            id = 123L,
            startCountdownLength = 456L,
            startAndEndSoundUri = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = 345L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUri = "intermediateIntervalSoundUri",
            intermediateIntervalSoundName = "intermediateIntervalSoundName",
            duration = -1L,
            audioGuideSoundUri = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L,
            sessionPresetType = SessionPresetConverter.SessionPresetType.TIMED_FREE.name
        )
        val convertedEntity = runBlocking {
            sessionPresetConverter.convertEntityToModel(entity)
        }
        val expectedModel = SessionPreset.TimedFreeSessionPreset(
            id = 123L,
            startCountdownLength = 456L,
            startAndEndSoundUriString = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = 345L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUriString = "intermediateIntervalSoundUri",
            intermediateIntervalSoundName = "intermediateIntervalSoundName",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L
        )

        assertEquals(
            expectedModel,
            convertedEntity
        )
    }

    @Test
    fun `convertEntitytoModel No Id timed free`() {
        val entity = SessionPresetEntity(
            id = null,
            startCountdownLength = 456L,
            startAndEndSoundUri = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = 345L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUri = "intermediateIntervalSoundUri",
            intermediateIntervalSoundName = "intermediateIntervalSoundName",
            duration = -1L,
            audioGuideSoundUri = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L,
            sessionPresetType = SessionPresetConverter.SessionPresetType.TIMED_FREE.name
        )
        val convertedEntity = runBlocking {
            sessionPresetConverter.convertEntityToModel(entity)
        }
        val expectedModel = SessionPreset.TimedFreeSessionPreset(
            id = -1L,
            startCountdownLength = 456L,
            startAndEndSoundUriString = "startAndEndSoundUri",
            startAndEndSoundName = "startAndEndSoundName",
            intermediateIntervalLength = 345L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUriString = "intermediateIntervalSoundUri",
            intermediateIntervalSoundName = "intermediateIntervalSoundName",
            vibration = true,
            sessionTypeId = 678,
            lastEditTime = 567L
        )

        assertEquals(
            expectedModel,
            convertedEntity
        )
    }
}
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

    val sessionPreset = SessionPreset(
        id = 123L,
        duration = 234L,
        startAndEndSoundUri = "startAndEndSoundUri",
        intermediateIntervalLength = 345L,
        startCountdownLength = 456L,
        intermediateIntervalRandom = true,
        intermediateIntervalSoundUri = "intermediateIntervalSoundUri",
        audioGuideSoundUri = "audioGuideSoundUri",
        vibration = true,
        lastEditTime = 567L,
        sessionTypeId = 678L
    )

    val sessionPresetEntity = SessionPresetEntity(
        id = 123L,
        duration = 234L,
        startAndEndSoundUri = "startAndEndSoundUri",
        intermediateIntervalLength = 345L,
        startCountdownLength = 456L,
        intermediateIntervalRandom = true,
        intermediateIntervalSoundUri = "intermediateIntervalSoundUri",
        audioGuideSoundUri = "audioGuideSoundUri",
        vibration = true,
        lastEditTime = 567L,
        sessionTypeId = 678L
    )

    //////////////////////////////////
    @Test
    fun convertModelToEntity() {
        val convertedModel = runBlocking {
            sessionPresetConverter.convertModelToEntity(sessionPreset)
        }
        assertEquals(sessionPresetEntity, convertedModel)
    }


    @Test
    fun convertEntitytoModel() {
        val convertedEntity = runBlocking {
            sessionPresetConverter.convertEntitytoModel(sessionPresetEntity)
        }
        assertEquals(sessionPreset, convertedEntity)
    }
}
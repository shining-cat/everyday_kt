package fr.shining_cat.everyday.repository.converter

import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.locale.entities.SessionPresetEntity
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.testutils.AbstractBaseTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SessionPresetConverterTest : AbstractBaseTest() {

    @Mock
    private lateinit var mockLogger: Logger

    private lateinit var sessionPresetConverter: SessionPresetConverter


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        assertNotNull(mockLogger)
        sessionPresetConverter = SessionPresetConverter(mockLogger)
    }

    /**
     * See [Memory leak in mockito-inline...](https://github.com/mockito/mockito/issues/1614)
     */
    @After
    fun clearMocks() {
        Mockito.framework().clearInlineMocks()
    }

    val sessionPreset = SessionPreset(
        id = 123L,
        duration =234L,
        startAndEndSoundUri ="startAndEndSoundUri",
        intermediateIntervalLength =345L,
        startCountdownLength =456L,
        intermediateIntervalRandom = true,
        intermediateIntervalSoundUri = "intermediateIntervalSoundUri",
        audioGuideSoundUri = "audioGuideSoundUri",
        vibration = true,
        lastEditTime = 567L
    )

    val sessionPresetEntity = SessionPresetEntity(
        id = 123L,
        duration =234L,
        startAndEndSoundUri ="startAndEndSoundUri",
        intermediateIntervalLength =345L,
        startCountdownLength =456L,
        intermediateIntervalRandom = true,
        intermediateIntervalSoundUri = "intermediateIntervalSoundUri",
        audioGuideSoundUri = "audioGuideSoundUri",
        vibration = true,
        lastEditTime = 567L
    )

    //////////////////////////////////
    @Test
    fun convertModelToEntity() {
        val convertedModel = sessionPresetConverter.convertModelToEntity(sessionPreset)
        assertEquals(sessionPresetEntity, convertedModel)
    }


    @Test
    fun convertEntitytoModel() {
        val convertedEntity = sessionPresetConverter.convertEntitytoModel(sessionPresetEntity)
        assertEquals(sessionPreset, convertedEntity)
    }
}
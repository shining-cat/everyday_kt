package fr.shining_cat.everyday.repository.converter

import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.locale.entities.SessionTypeEntity
import fr.shining_cat.everyday.models.SessionType
import fr.shining_cat.everyday.testutils.AbstractBaseTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SessionTypeConverterTest : AbstractBaseTest() {

    @Mock
    private lateinit var mockLogger: Logger

    private lateinit var sessionTypeConverter: SessionTypeConverter


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        assertNotNull(mockLogger)
        sessionTypeConverter = SessionTypeConverter(mockLogger)
    }

    /**
     * See [Memory leak in mockito-inline...](https://github.com/mockito/mockito/issues/1614)
     */
    @After
    fun clearMocks() {
        Mockito.framework().clearInlineMocks()
    }

    val sessionType = SessionType(
        id = 123L,
        name = "name",
        description ="description",
        color ="color",
        lastEditTime = 234L
    )

    val sessionTypeEntity = SessionTypeEntity(
        id = 123L,
        name = "name",
        description ="description",
        color ="color",
        lastEditTime = 234L
    )

    //////////////////////////////////
    @Test
    fun convertModelToEntity() {
        runBlocking {
            val convertedModel = sessionTypeConverter.convertModelToEntity(sessionType)
            assertEquals(sessionTypeEntity, convertedModel)
        }
    }


    @Test
    fun convertEntitytoModel() {
        runBlocking {
            val convertedEntity = sessionTypeConverter.convertEntitytoModel(sessionTypeEntity)
            assertEquals(sessionType, convertedEntity)
        }
    }
}
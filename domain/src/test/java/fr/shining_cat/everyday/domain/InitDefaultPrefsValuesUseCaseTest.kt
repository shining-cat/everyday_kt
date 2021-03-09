package fr.shining_cat.everyday.domain

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class InitDefaultPrefsValuesUseCaseTest {

    @MockK
    private lateinit var mockSharedPrefsHelper: SharedPrefsHelper

    @MockK
    private lateinit var mockLogger: Logger

    @MockK
    private lateinit var mockContext: Context

    @MockK
    private lateinit var mockDefaultRingtoneUri: Uri

    @MockK
    private lateinit var mockResources: Resources

    private lateinit var initDefaultPrefsValuesUseCase: InitDefaultPrefsValuesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Assert.assertNotNull(mockSharedPrefsHelper)

        initDefaultPrefsValuesUseCase = InitDefaultPrefsValuesUseCase(
            mockSharedPrefsHelper,
            mockLogger
        )
    }

    ////////////////////////////////
    //NOTIFICATION TEXT
    @Test
    fun `test checkNotificationTextInit no saved value sets default`() {
        val deviceDefaultRingtoneTitle = "mocked ringtone title"
        coEvery { mockSharedPrefsHelper.getNotificationText() } returns ""
        coEvery { mockSharedPrefsHelper.setNotificationText(any()) } answers {}
        coEvery { mockSharedPrefsHelper.getNotificationSoundUri() } returns "not blank uri"
        coEvery { mockSharedPrefsHelper.getNotificationSoundTitle() } returns "not blank title"
        coEvery { mockContext.resources } returns mockResources
        val mockedString = "mocked string from resources"
        coEvery { mockResources.getString(any()) } returns mockedString
        runBlocking { initDefaultPrefsValuesUseCase.execute(mockContext, mockDefaultRingtoneUri, deviceDefaultRingtoneTitle) }
        coVerify(exactly = 1) {
            mockSharedPrefsHelper.getNotificationText()
        }
        coVerify(exactly = 1) {
            mockContext.resources
        }
        coVerify(exactly = 1) {
            mockResources.getString(R.string.notificationsPreferences_notification_text_default_text)
        }
        coVerify(exactly = 1) {
            mockSharedPrefsHelper.setNotificationText(mockedString)
        }
    }

    @Test
    fun `test checkNotificationTextInit saved value does nothing`() {
        val deviceDefaultRingtoneTitle = "mocked ringtone title"
        coEvery { mockSharedPrefsHelper.getNotificationText() } returns "already saved notification text"
        coEvery { mockSharedPrefsHelper.getNotificationSoundUri() } returns "not blank uri"
        coEvery { mockSharedPrefsHelper.getNotificationSoundTitle() } returns "not blank title"
        runBlocking { initDefaultPrefsValuesUseCase.execute(mockContext, mockDefaultRingtoneUri, deviceDefaultRingtoneTitle) }
        coVerify(exactly = 1) {
            mockSharedPrefsHelper.getNotificationText()
        }
        coVerify(exactly = 0) {
            mockContext.resources
        }
        coVerify(exactly = 0) {
            mockResources.getString(R.string.notificationsPreferences_notification_text_default_text)
        }
        coVerify(exactly = 0) {
            mockSharedPrefsHelper.setNotificationText(any())
        }
    }

    ////////////////////////////////
    //NOTIFICATION SOUND URI
    @Test
    fun `test checkNotificationSoundDefaultUriSet no saved value sets default`() {
        val mockedString = "mocked ringtone Uri to string"
        val deviceDefaultRingtoneTitle = "mocked ringtone title"
        coEvery { mockSharedPrefsHelper.getNotificationText() } returns "not blank text"
        coEvery { mockSharedPrefsHelper.getNotificationSoundUri() } returns ""
        coEvery { mockSharedPrefsHelper.getNotificationSoundTitle() } returns "not blank title"
        coEvery { mockSharedPrefsHelper.setNotificationSoundUri(any()) } answers {}
        coEvery { mockDefaultRingtoneUri.toString() } returns mockedString
        runBlocking { initDefaultPrefsValuesUseCase.execute(mockContext, mockDefaultRingtoneUri, deviceDefaultRingtoneTitle) }
        coVerify(exactly = 1) {
            mockSharedPrefsHelper.getNotificationSoundUri()
        }
        coVerify(exactly = 1) {
            mockSharedPrefsHelper.setNotificationSoundUri(mockedString)
        }
    }

    @Test
    fun `test checkNotificationSoundDefaultUriSet saved value does nothing`() {
        val deviceDefaultRingtoneTitle = "mocked ringtone title"
        coEvery { mockSharedPrefsHelper.getNotificationText() } returns "not blank text"
        coEvery { mockSharedPrefsHelper.getNotificationSoundUri() } returns "not blank sound uri as string"
        coEvery { mockSharedPrefsHelper.getNotificationSoundTitle() } returns "not blank title"
        runBlocking { initDefaultPrefsValuesUseCase.execute(mockContext, mockDefaultRingtoneUri, deviceDefaultRingtoneTitle) }
        coVerify(exactly = 1) {
            mockSharedPrefsHelper.getNotificationSoundUri()
        }
        coVerify(exactly = 0) {
            mockSharedPrefsHelper.setNotificationSoundUri(any())
        }
    }

    ////////////////////////////////
    //NOTIFICATION SOUND TITLE
    @Test
    fun `test checkNotificationSoundDefaultTitleSet no saved value sets default`() {
        val deviceDefaultRingtoneTitle = "mocked ringtone title"
        coEvery { mockSharedPrefsHelper.getNotificationText() } returns "not blank text"
        coEvery { mockSharedPrefsHelper.getNotificationSoundUri() } returns "not blank sound uri as string"
        coEvery { mockSharedPrefsHelper.getNotificationSoundTitle() } returns ""
        coEvery { mockSharedPrefsHelper.setNotificationSoundTitle(any()) } answers {}
        runBlocking { initDefaultPrefsValuesUseCase.execute(mockContext, mockDefaultRingtoneUri, deviceDefaultRingtoneTitle) }
        coVerify(exactly = 1) {
            mockSharedPrefsHelper.getNotificationSoundTitle()
        }
        coVerify(exactly = 1) {
            mockSharedPrefsHelper.setNotificationSoundTitle(deviceDefaultRingtoneTitle)
        }
    }

    @Test
    fun `test checkNotificationSoundDefaultTitleSet saved value does nothing`() {
        val deviceDefaultRingtoneTitle = "mocked ringtone title"
        coEvery { mockSharedPrefsHelper.getNotificationText() } returns "not blank text"
        coEvery { mockSharedPrefsHelper.getNotificationSoundUri() } returns "not blank sound uri as string"
        coEvery { mockSharedPrefsHelper.getNotificationSoundTitle() } returns "not blank title"
        runBlocking { initDefaultPrefsValuesUseCase.execute(mockContext, mockDefaultRingtoneUri, deviceDefaultRingtoneTitle) }
        coVerify(exactly = 1) {
            mockSharedPrefsHelper.getNotificationSoundTitle()
        }
        coVerify(exactly = 0) {
            mockSharedPrefsHelper.setNotificationSoundTitle(any())
        }
    }
}
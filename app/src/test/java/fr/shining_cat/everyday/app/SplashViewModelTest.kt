package fr.shining_cat.everyday.app

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import fr.shining_cat.everyday.app.viewmodels.SplashViewModel
import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.domain.InitDefaultPrefsValuesUseCase
import fr.shining_cat.everyday.navigation.Destination
import fr.shining_cat.everyday.testutils.MainCoroutineScopeRule
import fr.shining_cat.everyday.testutils.extensions.getValueForTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SplashViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @MockK
    private lateinit var mockLogger: Logger

    @MockK
    private lateinit var mockInitDefaultPrefsValuesUseCase: InitDefaultPrefsValuesUseCase

    @MockK
    private lateinit var mockContext: Context

    @MockK
    private lateinit var mockRingtoneUri: Uri

    @MockK
    private lateinit var mockRingtone: Ringtone

    private lateinit var splashViewModel: SplashViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coEvery { mockLogger.d(any(), any()) } answers {}
        coEvery { mockLogger.e(any(), any()) } answers {}

        splashViewModel = SplashViewModel(
            mockInitDefaultPrefsValuesUseCase,
            mockLogger
        )
    }

    @Test
    fun `test loadConfInit success`() {
        coEvery { mockInitDefaultPrefsValuesUseCase.execute(any(), any(), any()) } answers {}
        mockkStatic(RingtoneManager::class)
        coEvery { RingtoneManager.getActualDefaultRingtoneUri(mockContext, any()) } returns mockRingtoneUri
        coEvery { RingtoneManager.getRingtone(mockContext, mockRingtoneUri) } returns mockRingtone
        coEvery { mockRingtone.getTitle(mockContext) } returns "ringtone mock title"

        runBlocking {
            splashViewModel.loadConfInit(mockContext)
        }

        coVerify(exactly = 1) { RingtoneManager.getActualDefaultRingtoneUri(mockContext, any()) }
        coVerify(exactly = 1) { RingtoneManager.getRingtone(mockContext, mockRingtoneUri) }
        coVerify(exactly = 1) { mockInitDefaultPrefsValuesUseCase.execute(any(), any(), any()) }
        assertTrue(splashViewModel.initLiveData.getValueForTest() is Destination.HomeDestination)
    }
}
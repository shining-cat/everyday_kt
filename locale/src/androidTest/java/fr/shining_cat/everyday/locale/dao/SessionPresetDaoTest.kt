package fr.shining_cat.everyday.locale.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import fr.shining_cat.everyday.locale.EveryDayRoomDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SessionPresetDaoTest{

    //set the testing environment to use Main thread instead of background one
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
    private fun emptyTableAndCheck() {
        runBlocking {
            sessionPresetDao.deleteAllSessionPresets()
        }
        checkTotalCountIs(0)
    }

    private fun checkTotalCountIs(expectedCount: Int) {
        val count = runBlocking {
            sessionPresetDao.getNumberOfRows()
        }
        assertEquals(expectedCount, count)
    }

    @After
    fun tearDown() {
        EveryDayRoomDatabase.closeAndDestroy()
    }

    /////////////////////////////


}
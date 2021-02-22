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

package fr.shining_cat.everyday.locale

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule

//@RunWith(AndroidJUnit4ClassRunner::class)
class EveryDayRoomDatabaseTest {

//        TODO("For now we don't need tests on the database instance")
//set the testing environment to use Main thread instead of background one
@get:Rule
val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var context: Context

    @Before
    fun setupTable() {
        //We can't use inMemory Databases to test createFromAsset (see https://developer.android.com/training/data-storage/room/prepopulate)
        //so We will use a real DB here, and we need to flush it afterward
        context = ApplicationProvider.getApplicationContext<Context>()
    }
    @After
    fun deleteDatabase() {
        context.deleteDatabase(EveryDayRoomDatabase.DATABASE_NAME)
    }
}
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
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.shining_cat.everyday.locale.dao.RewardDao
import fr.shining_cat.everyday.locale.dao.SessionPresetDao
import fr.shining_cat.everyday.locale.dao.SessionRecordDao
import fr.shining_cat.everyday.locale.dao.SessionTypeDao
import fr.shining_cat.everyday.locale.entities.RewardEntity
import fr.shining_cat.everyday.locale.entities.SessionPresetEntity
import fr.shining_cat.everyday.locale.entities.SessionRecordEntity
import fr.shining_cat.everyday.locale.entities.SessionTypeEntity

@Database(
    entities = [SessionRecordEntity::class, RewardEntity::class, SessionPresetEntity::class, SessionTypeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EveryDayRoomDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAOs.
     */
    abstract fun sessionRecordDao(): SessionRecordDao

    abstract fun rewardDao(): RewardDao

    abstract fun sessionPresetDao(): SessionPresetDao

    abstract fun sessionTypeDao(): SessionTypeDao

    companion object {

        var TEST_MODE = false
        val DATABASE_NAME = "everyday_database.db"

        // singleton to prevent having multiple instances of the database opened at the same time :
        @Volatile
        private var INSTANCE: EveryDayRoomDatabase? = null

        fun getInstance(context: Context): EveryDayRoomDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {
                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE
                // If instance is `null` make a new database instance.
                if (instance == null) {
                    if (TEST_MODE) {
                        instance = Room.inMemoryDatabaseBuilder(
                            context,
                            EveryDayRoomDatabase::class.java
                        ).fallbackToDestructiveMigration().build()
                    } else {
                        instance = Room.databaseBuilder(
                            context,
                            EveryDayRoomDatabase::class.java,
                            DATABASE_NAME
                        )
                            // TODO: handle migration:
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this lesson. You can learn more about
                            // migration with Room in this blog post:
                            // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                            .fallbackToDestructiveMigration().build()
                    }
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }
                // Return instance; smart cast to be non-null.
                return instance
            }
        }

        fun closeAndDestroy() {
            INSTANCE?.close()
            INSTANCE = null
        }
    }
}
package fr.shining_cat.everyday.localdata

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import fr.shining_cat.everyday.localdata.dao.RewardDao
import fr.shining_cat.everyday.localdata.dao.SessionRecordDao
import fr.shining_cat.everyday.localdata.dto.SessionDTO
import fr.shining_cat.everyday.model.Reward
import fr.shining_cat.everyday.model.SessionRecord

@Database(entities = [SessionDTO::class, Reward::class ], version = 1, exportSchema = false)
abstract class EveryDayRoomDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAOs.
     */
    abstract fun sessionRecordDao(): SessionRecordDao

    abstract fun rewardDao(): RewardDao

    companion object {

        //singleton to prevent having multiple instances of the database opened at the same time :
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
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EveryDayRoomDatabase::class.java,
                        "everyday_database"
                    )
                        //TODO: handle migration:
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this lesson. You can learn more about
                        // migration with Room in this blog post:
                        // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                        .fallbackToDestructiveMigration()
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }
                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }
}

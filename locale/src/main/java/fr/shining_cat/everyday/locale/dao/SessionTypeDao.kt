package fr.shining_cat.everyday.locale.dao

import androidx.room.*
import fr.shining_cat.everyday.locale.entities.SessionTypeEntity
import fr.shining_cat.everyday.locale.entities.SessionTypeEntityColumnNames.LAST_EDIT_DATE
import fr.shining_cat.everyday.locale.entities.SessionTypeEntityColumnNames.SESSION_TYPE_ID
import fr.shining_cat.everyday.locale.entities.SessionTypeTable.SESSION_TYPE_TABLE_NAME

@Dao
abstract class SessionTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(sessionTypes: List<SessionTypeEntity>): Array<Long>

    @Update
    abstract suspend fun update(sessionType: SessionTypeEntity): Int

    @Delete
    abstract suspend fun delete(sessionTypes: SessionTypeEntity): Int

    @Query("DELETE FROM $SESSION_TYPE_TABLE_NAME")
    abstract suspend fun deleteAllSessionTypes(): Int

    @Query("SELECT * from $SESSION_TYPE_TABLE_NAME ORDER BY $LAST_EDIT_DATE DESC")
    abstract suspend fun getAllSessionTypesLastEditTimeDesc(): List<SessionTypeEntity>

    @Query("SELECT COUNT($SESSION_TYPE_ID) FROM $SESSION_TYPE_TABLE_NAME")
    abstract suspend fun getNumberOfRows(): Int

}
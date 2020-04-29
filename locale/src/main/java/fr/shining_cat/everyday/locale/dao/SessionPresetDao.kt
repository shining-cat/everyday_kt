package fr.shining_cat.everyday.locale.dao

import androidx.room.*
import fr.shining_cat.everyday.locale.entities.SessionPresetEntity
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.LAST_EDIT_DATE
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.SESSION_PRESET_ID
import fr.shining_cat.everyday.locale.entities.SessionPresetTable.SESSION_PRESET_TABLE_NAME

@Dao
abstract class SessionPresetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(sessionPresets: List<SessionPresetEntity>): Array<Long>

    @Update
    abstract suspend fun update(sessionPreset: SessionPresetEntity): Int

    @Delete
    abstract suspend fun delete(sessionPresets: SessionPresetEntity): Int

    @Query("DELETE FROM $SESSION_PRESET_TABLE_NAME")
    abstract suspend fun deleteAllSessionPresets(): Int

    @Query("SELECT * from $SESSION_PRESET_TABLE_NAME ORDER BY $LAST_EDIT_DATE DESC")
    abstract suspend fun getAllSessionPresetsLastEditTimeDesc(): List<SessionPresetEntity>

    @Query("SELECT COUNT($SESSION_PRESET_ID) FROM $SESSION_PRESET_TABLE_NAME")
    abstract suspend fun getNumberOfRows(): Int

}
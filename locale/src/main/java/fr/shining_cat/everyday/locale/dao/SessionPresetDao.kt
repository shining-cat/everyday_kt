package fr.shining_cat.everyday.locale.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.shining_cat.everyday.locale.entities.SessionPresetEntity
import fr.shining_cat.everyday.locale.entities.SessionPresetEntityColumnNames.SESSION_PRESET_ID
import fr.shining_cat.everyday.locale.entities.SessionPresetTable.SESSION_PRESET_TABLE_NAME

@Dao
abstract class SessionPresetDao {

    @Insert
    abstract suspend fun insert(sessionPresets: List<SessionPresetEntity>): Array<Long>

    @Update
    abstract suspend fun updateSessionPreset(sessionPreset: SessionPresetEntity): Int

    @Delete
    abstract suspend fun deleteSessionPreset(reward: SessionPresetEntity): Int

    @Query("SELECT * from $SESSION_PRESET_TABLE_NAME ORDER BY $SESSION_PRESET_ID ASC")
    abstract fun getSessionPresets(): LiveData<SessionPresetEntity?>

}
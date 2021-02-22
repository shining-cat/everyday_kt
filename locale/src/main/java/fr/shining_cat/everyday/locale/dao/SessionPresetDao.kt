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

package fr.shining_cat.everyday.locale.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

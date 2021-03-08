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
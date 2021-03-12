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

package fr.shining_cat.everyday.locale.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.shining_cat.everyday.locale.entities.SessionTypeEntityColumnNames.COLOR
import fr.shining_cat.everyday.locale.entities.SessionTypeEntityColumnNames.DESCRIPTION
import fr.shining_cat.everyday.locale.entities.SessionTypeEntityColumnNames.LAST_EDIT_DATE
import fr.shining_cat.everyday.locale.entities.SessionTypeEntityColumnNames.NAME
import fr.shining_cat.everyday.locale.entities.SessionTypeEntityColumnNames.SESSION_TYPE_ID
import fr.shining_cat.everyday.locale.entities.SessionTypeTable.SESSION_TYPE_TABLE_NAME

@Entity(tableName = SESSION_TYPE_TABLE_NAME)
data class SessionTypeEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = SESSION_TYPE_ID) val id: Long = 0L,
    //
    @ColumnInfo(name = NAME) val name: String,
    @ColumnInfo(name = DESCRIPTION) val description: String,
    @ColumnInfo(name = COLOR) val color: String,
    @ColumnInfo(name = LAST_EDIT_DATE) val lastEditTime: Long
)

object SessionTypeTable {

    const val SESSION_TYPE_TABLE_NAME = "sessions_types"
}

object SessionTypeEntityColumnNames {

    const val SESSION_TYPE_ID = "sessionTypeId"
    const val NAME = "name"
    const val DESCRIPTION = "duration"
    const val COLOR = "startAndEndSound"
    const val LAST_EDIT_DATE = "lastEditDate"
}
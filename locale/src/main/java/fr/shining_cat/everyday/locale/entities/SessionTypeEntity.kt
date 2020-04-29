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
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = SESSION_TYPE_ID)
    var id: Long = 0L,
    //
    @ColumnInfo(name = NAME)
    var name: String,
    @ColumnInfo(name = DESCRIPTION)
    var description: String,
    @ColumnInfo(name = COLOR)
    var color: String,
    @ColumnInfo(name = LAST_EDIT_DATE)
    var lastEditTime: Long
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
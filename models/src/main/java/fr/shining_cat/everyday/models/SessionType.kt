package fr.shining_cat.everyday.models

data class SessionType(
    var id: Long = 0L,
    var name: String,
    var description: String,
    var color: String,
    var lastEditTime: Long
)
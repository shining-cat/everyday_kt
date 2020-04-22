package fr.shining_cat.everyday.models


data class SessionRecord(
    var id: Long = 0L,
    //
    var startMood: Mood,
    var endMood: Mood,
    //
    var notes: String,
    var realDuration: Long,
    var pausesCount: Int,
    var realDurationVsPlanned: RealDurationVsPlanned,
    var guideMp3: String
)
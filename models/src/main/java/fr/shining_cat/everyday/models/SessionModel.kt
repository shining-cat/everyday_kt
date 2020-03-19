package fr.shining_cat.everyday.models


data class SessionModel(
    var id: Long = 0L,
    //
    var startMood: Mood,
    var endMood: Mood,
    //
    var notes: String,
    var realDuration: Long,
    var pausesCount: Int,
    var realDurationVsPlanned: RealDurationVsPlanned, //<0 if real < planned; =0 if real = planned; >0 if real > planned  (obtained via Long.compare(real, planned)
    var guideMp3: String)

enum class RealDurationVsPlanned{
    EQUAL,
    REAL_SHORTER,
    REAL_LONGER
}
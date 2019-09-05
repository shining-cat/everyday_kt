package fr.shining_cat.everyday.model

data class MoodRecord(
    val timeOfRecord: Long,
    //values range from 1 (WORST) to 5 (BEST), 0 is for NOT SET
    val bodyValue: Int,
    val thoughtsValue: Int,
    val feelingsValue: Int,
    val globalValue: Int,
    //
    var notes: String? = null,
    var sessionRealDuration: Long = 0,
    var pausesCount: Int = 0,
    var realDurationVsPlanned: Int = 0, //<0 if real < planned; =0 if real = planned; >0 if real > planned  (obtained via Long.compare(real, planned)
    var guideMp3: String? = null){

    override fun toString() = "MOOD : timeStamp = " + timeOfRecord +
                                    "\n\tBody = " + bodyValue +
                                    "\n\tThoughts = " + thoughtsValue +
                                    "\n\tFeelings = " + feelingsValue +
                                    "\n\tGlobal = " + globalValue +
                                    "\n\tNotes : " + notes +
                                    "\n\tMP3 : " + guideMp3 +
                                    "\n\tReal duration = " + sessionRealDuration +
                                    "\n\treal Vs planned = " + realDurationVsPlanned +
                                    "\n\tPauses : " + pausesCount

}
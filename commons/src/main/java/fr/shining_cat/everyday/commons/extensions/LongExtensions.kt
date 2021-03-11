package fr.shining_cat.everyday.commons.extensions

import java.util.concurrent.TimeUnit

fun Long.formatDurationMsAsHhMmSsString(formatString: String = "%02d:%02d:%02d"): String {
    val initialLengthHours = TimeUnit.MILLISECONDS.toHours(this)
    val initialLengthMinutes = TimeUnit.MILLISECONDS.toMinutes(this)
    val initialLengthSeconds = TimeUnit.MILLISECONDS.toSeconds(this)
    val resultMinutes = (initialLengthMinutes - TimeUnit.HOURS.toMinutes(initialLengthHours))
    val resultSeconds = (initialLengthSeconds - (TimeUnit.HOURS.toSeconds(initialLengthHours) + TimeUnit.MINUTES.toSeconds(resultMinutes)))
    return formatString.format(initialLengthHours, resultMinutes, resultSeconds)
}
fun Long.formatDurationMsAsMmSsString(formatString: String = "%02d:%02d"): String {
    val initialLengthMinutes = TimeUnit.MILLISECONDS.toMinutes(this)
    val initialLengthSeconds = TimeUnit.MILLISECONDS.toSeconds(this)
    val resultSeconds = (initialLengthSeconds - TimeUnit.MINUTES.toSeconds(initialLengthMinutes))
    return formatString.format(initialLengthMinutes, resultSeconds)
}
fun Long.formatDurationMsAsSsString(formatString: String = "%02d"): String {
    val resultSeconds = TimeUnit.MILLISECONDS.toSeconds(this)
    return formatString.format(resultSeconds)
}
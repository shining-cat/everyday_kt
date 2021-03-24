package fr.shining_cat.everyday.commons.extensions

import fr.shining_cat.everyday.commons.Constants
import java.util.concurrent.TimeUnit

// TODO: write tests for these extensions
fun Long.autoFormatDurationMsAsSmallestHhMmSsString(
    formatStringFull: String = "%1\$02d:%2\$02d:%3\$02d",
    formatStringNoHours: String = "%1\$02d:%2\$02ds",
    formatStringNoHoursNoMinutes: String = "%02d"
): String {
    return when {
        this < Constants.ONE_MINUTE_AS_MS -> {
            this.formatDurationMsAsSsString(formatStringNoHoursNoMinutes)
        }

        this < Constants.ONE_HOUR_AS_MS -> {
            this.formatDurationMsAsMmSsString(formatStringNoHours)
        }

        else -> {
            this.formatDurationMsAsHhMmSsString(formatStringFull)
        }
    }
}

// TODO: build the rests of each unit with a modulo rather than the chain of subtraction : val restOfMinutes = minutes % 60
fun Long.formatDurationMsAsHhMmSsString(formatString: String = "%02d:%02d:%02d"): String {
    val initialLengthHours = TimeUnit.MILLISECONDS.toHours(this)
    val initialLengthMinutes = TimeUnit.MILLISECONDS.toMinutes(this)
    val initialLengthSeconds = TimeUnit.MILLISECONDS.toSeconds(this)
    val resultMinutes = (initialLengthMinutes - TimeUnit.HOURS.toMinutes(initialLengthHours))
    val resultSeconds = (initialLengthSeconds - (TimeUnit.HOURS.toSeconds(initialLengthHours) + TimeUnit.MINUTES.toSeconds(resultMinutes)))
    return formatString.format(
        initialLengthHours,
        resultMinutes,
        resultSeconds
    )
}

fun Long.formatDurationMsAsMmSsString(formatString: String = "%02d:%02d"): String {
    val initialLengthMinutes = TimeUnit.MILLISECONDS.toMinutes(this)
    val initialLengthSeconds = TimeUnit.MILLISECONDS.toSeconds(this)
    val resultSeconds = (initialLengthSeconds - TimeUnit.MINUTES.toSeconds(initialLengthMinutes))
    return formatString.format(
        initialLengthMinutes,
        resultSeconds
    )
}

fun Long.formatDurationMsAsSsString(formatString: String = "%02d"): String {
    val resultSeconds = TimeUnit.MILLISECONDS.toSeconds(this)
    return formatString.format(resultSeconds)
}
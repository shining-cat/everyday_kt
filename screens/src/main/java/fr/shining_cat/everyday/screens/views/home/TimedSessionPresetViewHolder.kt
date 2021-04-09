package fr.shining_cat.everyday.screens.views.home

import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.extensions.autoFormatDurationMsAsSmallestHhMmSsString
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.databinding.ItemTimedSessionPresetViewHolderBinding

class TimedSessionPresetViewHolder(
    private val itemTimedSessionPresetViewHolderBinding: ItemTimedSessionPresetViewHolderBinding,
    private val logger: Logger
) : AbstractSessionPresetViewHolder(
    itemTimedSessionPresetViewHolderBinding.root,
    logger
) {

    private val LOG_TAG = TimedSessionPresetViewHolder::class.java.name

    override fun bindView(sessionPreset: SessionPreset) {
        val resources = itemView.resources
        itemTimedSessionPresetViewHolderBinding.timedSessionDurationValue.text = sessionPreset.duration.autoFormatDurationMsAsSmallestHhMmSsString(
            resources.getString(R.string.duration_format_hours_minutes_seconds_short),
            resources.getString(R.string.duration_format_hours_minutes_no_seconds_short),
            resources.getString(R.string.duration_format_hours_no_minutes_no_seconds_short),
            resources.getString(R.string.duration_format_minutes_seconds_short),
            resources.getString(R.string.duration_format_minutes_no_seconds_short),
            resources.getString(R.string.duration_format_seconds_short)
        )
        //
        itemTimedSessionPresetViewHolderBinding.timedSessionIntervalValue.text = if (sessionPreset.intermediateIntervalLength == 0L) {
            resources.getString(R.string.generic_string_NONE)
        } else {
            sessionPreset.intermediateIntervalLength.autoFormatDurationMsAsSmallestHhMmSsString(
                resources.getString(R.string.duration_format_hours_minutes_seconds_short),
                resources.getString(R.string.duration_format_hours_minutes_no_seconds_short),
                resources.getString(R.string.duration_format_hours_no_minutes_no_seconds_short),
                resources.getString(R.string.duration_format_minutes_seconds_short),
                resources.getString(R.string.duration_format_minutes_no_seconds_short),
                resources.getString(R.string.duration_format_seconds_short)
            )
        }
    }
}
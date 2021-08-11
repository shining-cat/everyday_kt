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

package fr.shining_cat.everyday.screens.views.home.sessionpresetviewholders

import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.extensions.autoFormatDurationMsAsSmallestHhMmSsString
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.databinding.ItemTimedFreeSessionPresetViewHolderBinding

class TimedFreeSessionPresetViewHolder(

    private val itemTimedFreeSessionPresetViewHolderBinding: ItemTimedFreeSessionPresetViewHolderBinding,
    private val logger: Logger
): AbstractSessionPresetViewHolder(
    itemTimedFreeSessionPresetViewHolderBinding.root, logger
) {

    private val LOG_TAG = TimedFreeSessionPresetViewHolder::class.java.name

    override fun bindView(sessionPreset: SessionPreset) {
        val resources = itemView.resources
        itemTimedFreeSessionPresetViewHolderBinding.timedFreeSessionIntervalValue.text = when {
            sessionPreset.intermediateIntervalRandom -> {
                resources.getString(R.string.interval_random_short)
            }

            sessionPreset.intermediateIntervalLength == 0L -> {
                resources.getString(R.string.generic_string_NONE)
            }

            else -> {
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
}
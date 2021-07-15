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
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.screens.databinding.ItemAudioFreeSessionPresetViewHolderBinding

class AudioFreeSessionPresetViewHolder(
    private val itemAudioSessionPresetViewHolderBinding: ItemAudioFreeSessionPresetViewHolderBinding,
    private val logger: Logger
) : AbstractSessionPresetViewHolder(
    itemAudioSessionPresetViewHolderBinding.root,
    logger
) {

    private val LOG_TAG = AudioFreeSessionPresetViewHolder::class.java.name

    override fun bindView(sessionPreset: SessionPreset) {
        // we have nothing to bind here yet
    }
}
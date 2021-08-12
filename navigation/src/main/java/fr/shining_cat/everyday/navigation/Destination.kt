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

package fr.shining_cat.everyday.navigation

import android.os.Bundle
import fr.shining_cat.everyday.models.SessionPreset

sealed class Destination {

    class HomeDestination: Destination()

    class SettingsDestination: Destination()

    class SessionDestination(private val sessionPreset: SessionPreset): Destination() {

        fun toExtra(): Bundle {
            return Bundle().apply {
                putParcelable(
                    EXTRA_SESSION_PRESET, sessionPreset
                )
            }
        }

        companion object {

            private val EXTRA_SESSION_PRESET = "extra.session.preset"

            fun getParametersFromExtra(extra: Bundle): Params {
                return Params(
                    extra.getParcelable(EXTRA_SESSION_PRESET)
                )
            }
        }

        data class Params(
            val sessionPreset: SessionPreset?
        )
    }
}
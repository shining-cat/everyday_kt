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

import android.content.Context
import android.content.Intent

object Actions {

    fun openDestination(
        context: Context,
        destination: Destination
    ): Intent {
        return when (destination) {
            is Destination.HomeDestination -> intentForHome(context)
            is Destination.SessionDestination -> intentForSession(context, destination)
            is Destination.SettingsDestination -> intentForSettings(context)
        }
    }

    private fun internalIntent(
        context: Context,
        action: String
    ) = Intent(action).setPackage(context.packageName)

    private fun intentForHome(context: Context) = internalIntent(
        context, "fr.shining_cat.everyday.screens.views.ScreenActivity"
    )

    private fun intentForSession(
        context: Context,
        destination: Destination.SessionDestination
    ): Intent {
        val intent = internalIntent(
            context, "fr.shining_cat.everyday.session.views.SessionActivity"
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtras(destination.toExtra())
        return intent
    }

    private fun intentForSettings(context: Context) = internalIntent(
        context, "fr.shining_cat.everyday.settings.views.SettingsActivity"
    )
}
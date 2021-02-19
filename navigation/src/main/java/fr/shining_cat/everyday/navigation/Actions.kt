package fr.shining_cat.everyday.navigation

import android.content.Context
import android.content.Intent


object Actions {

    fun openDestination(context: Context, destination: Destination): Intent {
        return when (destination) {
            is Destination.HomeDestination -> intentForHome(context)
            is Destination.SessionDestination -> intentForSession(context)
            is Destination.SettingsDestination -> intentForSettings(context)

        }
    }

    private fun internalIntent(context: Context, action: String) =
        Intent(action).setPackage(context.packageName)


    private fun intentForHome(context: Context) =
        internalIntent(context, "fr.shining_cat.everyday.screens.views.ScreenActivity")

    private fun intentForSession(context: Context) =
        internalIntent(context, "fr.shining_cat.everyday.session.view.SessionActivity")

    private fun intentForSettings(context: Context) =
        internalIntent(context, "fr.shining_cat.everyday.settings.views.SettingsActivity")


}

package fr.shining_cat.everyday.navigation

import android.content.Context
import android.content.Intent


object Actions {

    fun openDestination(context: Context, destination: Destination): Intent {
        return when (destination) {
            is Destination.HomeDestination -> intentForHomeCreation(context)
            is Destination.SessionDestination -> intentForSessionCreation(context)
            is Destination.SettingsDestination -> intentForSettingsCreation(context)

        }
    }

    private fun internalIntent(context: Context, action: String) =
        Intent(action).setPackage(context.packageName)


    private fun intentForHomeCreation(context: Context) =
        internalIntent(context, "fr.shining_cat.everyday.screens.views.ScreenActivity")

    private fun intentForSessionCreation(context: Context) =
        internalIntent(context, "fr.shining_cat.everyday.session.view.SessionActivity")

    private fun intentForSettingsCreation(context: Context) =
        internalIntent(context, "fr.shining_cat.everyday.settings.views.SettingsActivity")


}

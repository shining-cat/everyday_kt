package fr.shining_cat.everyday.navigation

import android.content.Context
import android.content.Intent

object IntentFactory {

    fun openHome(context: Context) =internalIntent(context, "home package name.open")



    private fun internalIntent(context: Context, action: String) = Intent(action).setPackage(context.packageName)
}
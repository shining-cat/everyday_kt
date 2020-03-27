package fr.shining_cat.everyday.commons.helpers

import android.content.SharedPreferences

object SharedPrefsHelperSettings {
    const val NAME = "everyday.prefs"

    const val KEY_VAR = "var"

}

class SharedPrefsHelper(private val sharedPreferences: SharedPreferences) {

//    fun getVAR(): String {
//        return sharedPreferences.getString(
//            SharedPrefsHelperSettings.KEY_VAR,
//            "return_a_default_value_from_Constants_file"
//        )
//            ?: "return_a_default_value_from_Constants_file"
//    }
//
//    fun setVAR(inputValue: "create an enum") {
//        sharedPreferences.edit()
//            .putString(SharedPrefsHelperSettings.KEY_VAR, inputValue.value)
//            .apply()
//    }
}
package fr.shining_cat.everyday.commons.helpers

import android.content.SharedPreferences

object SharedPrefsHelperSettings {
    const val NAME = "everyday.prefs"

    const val KEY_VALUE = "value"

}

class SharedPrefsHelper(private val sharedPreferences: SharedPreferences) {

//    fun getValue(): String {
//        return sharedPreferences.getString(
//            SharedPrefsHelperSettings.KEY_VALUE,
//            "return_a_default_value_from_Constants_file"
//        )
//            ?: "return_a_default_value_from_Constants_file"
//    }
//
//    fun setValue(inputValue: "create an enum") {
//        sharedPreferences.edit()
//            .putString(SharedPrefsHelperSettings.KEY_VALUE, inputValue.value)
//            .apply()
//    }
}
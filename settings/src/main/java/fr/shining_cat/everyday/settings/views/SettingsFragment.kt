package fr.shining_cat.everyday.settings.views

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import fr.shining_cat.everyday.settings.R


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

}

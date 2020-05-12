package fr.shining_cat.everyday.settings.views

import android.content.Context
import android.widget.TextView
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceViewHolder

class PreferenceCategoryLongSummary(
    context: Context?
) : PreferenceCategory(context) {

    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        super.onBindViewHolder(holder)
        val summary = holder?.findViewById(android.R.id.summary) as TextView
        summary.isSingleLine = false
        summary.maxLines = 5
    }
}
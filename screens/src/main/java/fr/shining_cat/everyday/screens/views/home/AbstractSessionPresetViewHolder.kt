package fr.shining_cat.everyday.screens.views.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.models.SessionPreset

abstract class AbstractSessionPresetViewHolder(
    private val abstractStripRootView: View,
    private val logger: Logger
) : RecyclerView.ViewHolder(abstractStripRootView) {

    private val LOG_TAG = AbstractSessionPresetViewHolder::class.java.name

    abstract fun bindView(sessionPreset: SessionPreset)
}
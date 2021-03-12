package fr.shining_cat.everyday.screens.views.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SessionPresetItemDecoration(private val spaceSize: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            top = spaceSize
            left = 0
            right = 0
            bottom = 0
        }
    }
}
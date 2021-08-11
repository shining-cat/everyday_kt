package fr.shining_cat.everyday.commons.ui

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import fr.shining_cat.everyday.commons.Logger

abstract class SwipeInRecyclerViewCallback(
    private val rightIcon: Drawable?,
    private val leftIcon: Drawable?,
    private val backgroundColor: Int?,
    private val logger: Logger
) : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {

    private val LOG_TAG = SwipeInRecyclerViewCallback::class.java.name

    private val intrinsicWidth = rightIcon?.intrinsicWidth ?: 24
    private val intrinsicHeight = rightIcon?.intrinsicHeight ?: 24
    private val background = ColorDrawable()
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    // onMove is for the dragging feature, not used here
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(
                c,
                itemView.right + dX,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat()
            )
            super.onChildDraw(
                c,
                recyclerView,
                viewHolder,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )
            return
        }

        // Draw the background if a color has been provided
        if (backgroundColor != null) {
            background.color = backgroundColor
            background.setBounds(
                itemView.left,
                itemView.top,
                itemView.right,
                itemView.bottom
            )
            background.draw(c)
        }
        // Calculate common attributes of both side icons
        val iconTopBorder = itemView.top + (itemHeight - intrinsicHeight) / 2
        val iconBottomBorder = iconTopBorder + intrinsicHeight
        val iconMargin = (itemHeight - intrinsicHeight) / 2

        if (dX > 0) { // dragging to the right -> show icon on the left
            val iconLeftBorder = itemView.left + iconMargin
            val iconRightBorder = itemView.left + intrinsicWidth + iconMargin
            // Draw the left side icon
            leftIcon?.setBounds(
                iconLeftBorder,
                iconTopBorder,
                iconRightBorder,
                iconBottomBorder
            )
            leftIcon?.draw(c)
        }
        else { // dragging to the left -> show icon on the right
            val iconLeftBorder = itemView.right - iconMargin - intrinsicWidth
            val iconRightBorder = itemView.right - iconMargin
            // apply right side icon bounds
            rightIcon?.setBounds(
                iconLeftBorder,
                iconTopBorder,
                iconRightBorder,
                iconBottomBorder
            )
            // Draw the right side icon
            rightIcon?.draw(c)
        }

        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    private fun clearCanvas(
        c: Canvas?,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float
    ) {
        c?.drawRect(
            left,
            top,
            right,
            bottom,
            clearPaint
        )
    }
}
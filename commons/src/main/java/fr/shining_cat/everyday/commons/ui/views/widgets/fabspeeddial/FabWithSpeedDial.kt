/*
 * Copyright (c) 2021. Shining-cat - Shiva Bernhard
 * This file is part of Everyday.
 *
 *     Everyday is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, version 3 of the License.
 *
 *     Everyday is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Everyday.  If not, see <https://www.gnu.org/licenses/>.
 */

package fr.shining_cat.everyday.commons.ui.views.widgets.fabspeeddial

import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import fr.shining_cat.everyday.commons.Constants.Companion.FAST_ANIMATION_DURATION_MILLIS
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.R
import fr.shining_cat.everyday.commons.databinding.LayoutWidgetFabSpeedDialBinding
import fr.shining_cat.everyday.commons.extensions.animateAlpha
import java.security.InvalidParameterException
import kotlin.math.max

class FabWithSpeedDial @kotlin.jvm.JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): ConstraintLayout(
    context,
    attrs,
    defStyle
) {

    private val LOG_TAG = FabWithSpeedDial::class.java.name

    private val layoutWidgetFabSpeedDialBinding: LayoutWidgetFabSpeedDialBinding = LayoutWidgetFabSpeedDialBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    private var speedDialItemViews = mutableListOf<FabSpeedDialItemView>()
    private var speedDialVisibleItemViews = mutableListOf<FabSpeedDialItemView>()
    private var isExpandingOrExpanded = false
    private var logger: Logger? = null

    init {
        //set fab initial icon
        layoutWidgetFabSpeedDialBinding.fab.icon = ContextCompat.getDrawable(
            context,
            R.drawable.plus
        )
        layoutWidgetFabSpeedDialBinding.fab.setOnClickListener {
            if (!isExpandingOrExpanded) expand() else collapse()
        }
    }

    fun setSpeedDialItems(
        items: List<FabSpeedDialItem>,
        logger: Logger? = null
    ) {
        this.logger = logger
        //check list size
        if (items.size < 3 || items.size > 6) throw InvalidParameterException("Material states that a FAB speed dial should contain at least 3 and no more than 6 actions")
        //build speed dial item views
        buildSpeedDialItemViews(items)
        //tie items apparition/disappearance to each other for cascade effect:
        chainSpeedDialItemsViewsTogether()
    }

    private fun buildSpeedDialItemViews(items: List<FabSpeedDialItem>) {
        for (item in items) {
            //create
            val speedDialItemView = FabSpeedDialItemView(
                item,
                logger,
                context

            )
            //store
            speedDialItemViews.add(speedDialItemView)
        }
    }

    private fun chainSpeedDialItemsViewsTogether() {
        for ((index, item) in speedDialItemViews.withIndex()) {
            //set a unique id to item as it is needed later
            if (item.id == -1) item.id = (FabSpeedDialItemView::class.java.name + index.toString()).hashCode()
            if (index > 0) {
                val precedingItem = speedDialItemViews[index - 1]
                //appearing from bottom to top => tie the apparition effect of this item to the appear-complete of the previous one in the list
                precedingItem.setListenerAppear {
                    appearOneItem(
                        item,
                        precedingItem
                    )
                }
                //disappearing from top to bottom => tie the disappearing effect of this item to the disappear-complete of the previous one in the list
                item.setListenerDisappear {
                    onSpeedDialItemViewDisappearComplete(item)
                    precedingItem.disappear()
                }
            }
            else {
                //plug listener to last disappearing item
                item.setListenerDisappear {onSpeedDialItemViewDisappearComplete(item)}
            }
        }
    }

    private fun appearOneItem(
        item: FabSpeedDialItemView,
        onTopOfView: View
    ) {
        if (!speedDialVisibleItemViews.contains(item)) {
            speedDialVisibleItemViews.add(item)
            //
            layoutWidgetFabSpeedDialBinding.fabContainer.addView(item)
            //positioning can only be done after view is added
            //position items relative to each other
            val layoutParams = item.layoutParams as LayoutParams
            layoutParams.endToEnd = layoutWidgetFabSpeedDialBinding.fab.id //all items will be vertically aligned on Main Fab
            layoutParams.bottomToTop = onTopOfView.id //put item on top of previous one
            layoutParams.bottomMargin = context.resources.getDimensionPixelSize(R.dimen.three_quarter_margin)
            item.layoutParams = layoutParams
        }
        //
        item.appear()
    }

    private fun onSpeedDialItemViewDisappearComplete(item: FabSpeedDialItemView) {
        layoutWidgetFabSpeedDialBinding.fabContainer.removeView(item)
        speedDialVisibleItemViews.remove(item)
    }

    private fun instantCollapse() {
        //TODO: build instant collapse
        collapse()
    }

    fun appear() {
        this.rootView.animateAlpha(
            fromAlpha = 0f,
            toAlpha = 1f,
            duration = FAST_ANIMATION_DURATION_MILLIS
        ).start()
    }

    fun disappear() {
        if (isExpandingOrExpanded) {
            instantCollapse()
        }
        this.rootView.animateAlpha(
            fromAlpha = 1f,
            toAlpha = 0f,
            duration = FAST_ANIMATION_DURATION_MILLIS
        ).start()
    }

    private fun expand() {
        isExpandingOrExpanded = true
        //launch appearance cascade of speed dial items
        launchAppearingCascade()
        //set fab icon and launch animation
        layoutWidgetFabSpeedDialBinding.fab.icon = ContextCompat.getDrawable(
            context,
            R.drawable.animated_plus
        )
        animateFab()
    }

    private fun launchAppearingCascade() {
        //if there are already visible speed dial items, prepare cascade from the first non-visible one
        val startingIndex = max(
            0,
            speedDialVisibleItemViews.size - 1
        )
        logger?.e(
            LOG_TAG,
            "launchAppearingCascade::will start at index : $startingIndex"
        )
        val referentViewForVerticalPosition = if (startingIndex == 0) {
            layoutWidgetFabSpeedDialBinding.fab as View //start up positioning reference is the main fab
        }
        else {
            speedDialItemViews[startingIndex - 1] as View
        }
        if (startingIndex < speedDialItemViews.size) {
            appearOneItem(
                speedDialItemViews[startingIndex],
                referentViewForVerticalPosition
            )
        }
    }

    private fun collapse() {
        logger?.e(
            LOG_TAG,
            "collapse::will start at index : ${speedDialItemViews.size - 1}"
        )
        isExpandingOrExpanded = false
        //the first item that should disappear is the top-most one
        if (speedDialVisibleItemViews.isNotEmpty()) speedDialVisibleItemViews.last().disappear()
        //set fab icon and launch animation
        layoutWidgetFabSpeedDialBinding.fab.icon = ContextCompat.getDrawable(
            context,
            R.drawable.animated_minus
        )
        animateFab()
    }

    private fun animateFab() {
        val drawable: Drawable = layoutWidgetFabSpeedDialBinding.fab.icon
        if (drawable is Animatable) {
            (drawable as Animatable).start()
        }
    }
}
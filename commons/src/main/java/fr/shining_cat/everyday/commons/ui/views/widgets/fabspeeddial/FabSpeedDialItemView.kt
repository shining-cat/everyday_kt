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

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import fr.shining_cat.everyday.commons.Constants.Companion.FAST_ANIMATION_DURATION_MILLIS
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.databinding.LayoutWidgetFabSpeedDialItemBinding
import fr.shining_cat.everyday.commons.extensions.animateAlpha

class FabSpeedDialItemView @kotlin.jvm.JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(
    context,
    attrs,
    defStyle
) {

    private val LOG_TAG = FabSpeedDialItemView::class.java.name
    private var logger: Logger? = null
    private var halfAnimationDurationMillis = FAST_ANIMATION_DURATION_MILLIS

    fun interface FabSpeedDialItemAppearListener {

        fun onAppearComplete()
    }

    private var listenerAppearListener: FabSpeedDialItemAppearListener? = null
    fun setListenerAppear(listener: FabSpeedDialItemAppearListener) {
        this.listenerAppearListener = listener
    }

    fun interface FabSpeedDialItemDisappear {

        fun onDisappearComplete()
    }

    private var listenerDisappear: FabSpeedDialItemDisappear? = null
    fun setListenerDisappear(listener: FabSpeedDialItemDisappear) {
        this.listenerDisappear = listener
    }

    private var hasLabel = false
    private var currentAnimation: ObjectAnimator? = null

    // this is used to prevent the dispatch of disappear animation end to listener when it is canceled while running by a call to appear()
    private var isAppearing = false

    // this is used to prevent the dispatch of appear animation end to listener when it is canceled while running by a call to disappear()
    private var isDisappearing = false

    private val layoutWidgetFabSpeedDialItemBinding: LayoutWidgetFabSpeedDialItemBinding = LayoutWidgetFabSpeedDialItemBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setup(
        fabSpeedDialItem: FabSpeedDialItem,
        animationDurationMillis: Long,
        logger: Logger? = null
    ) {
        setUpUi(fabSpeedDialItem)
        // layoutWidgetFabSpeedDialItemBinding.root.setOnClickListener(fabSpeedDialItem.clickListener)
        this.halfAnimationDurationMillis = animationDurationMillis
        this.logger = logger
    }

    private fun setUpUi(speedDialItem: FabSpeedDialItem) {
        val context = layoutWidgetFabSpeedDialItemBinding.root.context

        layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemIcon.setOnClickListener {
            speedDialItem.clickListener.onClick(layoutWidgetFabSpeedDialItemBinding.root)
        }
        layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemIcon.icon = ContextCompat.getDrawable(
            context,
            speedDialItem.iconDrawable
        )
        hasLabel = speedDialItem.label.isNotBlank()
        if (hasLabel) {
            layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemLabel.text = speedDialItem.label
            layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemLabel.setOnClickListener {
                speedDialItem.clickListener.onClick(layoutWidgetFabSpeedDialItemBinding.root)
            }
            layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemLabel.visibility = VISIBLE
        } else {
            layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemLabel.visibility = GONE
        }
        layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemIcon.alpha = 0f
        layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemLabel.alpha = 0f
    }

    fun appear() {
        // prevent cumulative call, if called again while running, do not accept new call, just let previous one complete normally
        if (isAppearing) return
        // this is used to prevent the dispatch of disappear animation end to listener when it is canceled below
        isAppearing = true
        // now if an animation is already running, it can only be disappear(), so we cancel it because this new call takes precedence
        if (currentAnimation?.isRunning == true) currentAnimation?.cancel()
        // start by hiding item to allow for appear animation
        layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemIcon.alpha = 0f
        layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemLabel.alpha = 0f
        // build appear animation
        currentAnimation =
            layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemIcon.animateAlpha(
                fromAlpha = layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemIcon.alpha,
                toAlpha = 1f,
                duration = halfAnimationDurationMillis,
                onStart = null,
                onEnd = {
                    if (hasLabel) {
                        layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemLabel.animateAlpha(
                            fromAlpha = layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemLabel.alpha,
                            toAlpha = 1f,
                            duration = halfAnimationDurationMillis,
                            onStart = null,
                            onEnd = {
                                isAppearing = false
                                if (!isDisappearing) listenerAppearListener?.onAppearComplete()
                            }
                        ).start()
                    } else {
                        isAppearing = false
                        if (!isDisappearing) listenerAppearListener?.onAppearComplete()
                    }
                }
            )
        // launch appear animation
        currentAnimation?.start()
    }

    fun disappear() {
        // prevent cumulative call, if called again while running, do not accept new call, just let previous one complete normally
        if (isDisappearing) return
        // this is used to prevent the dispatch of appear animation end to listener when it is canceled below
        isDisappearing = true
        // now if an animation is already running, it can only be appear(), so we cancel it because this new call takes precedence
        if (currentAnimation?.isRunning == true) currentAnimation?.cancel()
        // build disappear animation
        currentAnimation =
            layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemIcon.animateAlpha(
                fromAlpha = layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemIcon.alpha,
                toAlpha = 0f,
                duration = halfAnimationDurationMillis,
                onStart = null,
                onEnd = {
                    if (hasLabel) {
                        layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemLabel.animateAlpha(
                            fromAlpha = layoutWidgetFabSpeedDialItemBinding.fabSpeedDialItemLabel.alpha,
                            toAlpha = 0f,
                            duration = halfAnimationDurationMillis,
                            onStart = null,
                            onEnd = {
                                isDisappearing = false
                                if (!isAppearing) listenerDisappear?.onDisappearComplete()
                            }
                        ).start()
                    } else {
                        isDisappearing = false
                        if (!isAppearing) listenerDisappear?.onDisappearComplete()
                    }
                }
            )
        // launch disappear animation
        currentAnimation?.start()
    }
}
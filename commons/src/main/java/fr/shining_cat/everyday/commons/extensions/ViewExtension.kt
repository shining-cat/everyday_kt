package fr.shining_cat.everyday.commons.extensions

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

fun View.animateToTranslationX(
    toPosition: Float,
    duration: Long = 0L,
    onStart: (() -> Unit)? = null,
    onEnd: (() -> Unit)? = null
): ObjectAnimator {
    val translation = ObjectAnimator.ofFloat(
        this,
        "translationX",
        toPosition
    )

    translation.duration = duration
    translation.addListener(object: Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {}

        override fun onAnimationEnd(animation: Animator?) {
            onEnd?.invoke()
        }

        override fun onAnimationCancel(animation: Animator?) {}

        override fun onAnimationStart(animation: Animator?) {
            onStart?.invoke()
        }
    })
    return translation
}

fun View.animateAlpha(
    fromAlpha: Float,
    toAlpha: Float,
    duration: Long = 0L,
    onStart: (() -> Unit)? = null,
    onEnd: (() -> Unit)? = null
): ObjectAnimator {
    val fade = ObjectAnimator.ofFloat(
        this,
        "alpha",
        fromAlpha,
        toAlpha
    )
    fade.duration = duration
    fade.addListener(object: Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {}

        override fun onAnimationEnd(animation: Animator?) {
            onEnd?.invoke()
        }

        override fun onAnimationCancel(animation: Animator?) {}

        override fun onAnimationStart(animation: Animator?) {
            onStart?.invoke()
        }
    })
    return fade
}

fun View.animateScale(
    toScaleX: Float,
    toScaleY: Float,
    duration: Long = 0L,
    onStart: (() -> Unit)? = null,
    onEnd: (() -> Unit)? = null
): AnimatorSet {
    val scalingX = ObjectAnimator.ofFloat(
        this,
        "scaleX",
        toScaleX
    )
    val scalingY = ObjectAnimator.ofFloat(
        this,
        "scaleY",
        toScaleY
    )
    scalingX.duration = duration
    scalingY.duration = duration
    val animatorSet = AnimatorSet()
    animatorSet.addListener(object: Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {}

        override fun onAnimationEnd(animation: Animator?) {
            onEnd?.invoke()
        }

        override fun onAnimationCancel(animation: Animator?) {}

        override fun onAnimationStart(animation: Animator?) {
            onStart?.invoke()
        }
    })
    animatorSet.play(scalingX).with(scalingY)
    return animatorSet
}
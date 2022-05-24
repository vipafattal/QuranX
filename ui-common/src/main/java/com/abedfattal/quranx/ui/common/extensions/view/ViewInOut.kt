package com.abedfattal.quranx.ui.common.extensions.view

import android.view.View
import android.view.ViewPropertyAnimator

const val ANIMATE_OUT_TOP = -1
const val ANIMATE_OUT_BOTTOM = 1

fun View.animateOut(location: Int,duration: Long = 300L): ViewPropertyAnimator {
    val translationYAnimation = animate()
        .setDuration(duration)
        .translationY(height.toFloat() * location)
     translationYAnimation
        .start()
    return translationYAnimation
}

fun View.animateIn(duration: Long = 400L) {
    animate()
        .setDuration(duration)
        .translationY(0f)
        .start()
}

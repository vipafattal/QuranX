package com.abedfattal.quranx.ui.common.extensions.view

import android.animation.ValueAnimator
import com.abedfattal.quranx.ui.common.R
import com.google.android.material.card.MaterialCardView


fun MaterialCardView.animateElevation(dy: Int) {
    if (dy > 0) animateElevation(false)
    else animateElevation(true)
}


fun MaterialCardView.animateElevation(removeElevation: Boolean) {
    val elevation = resources.getDimension(R.dimen.toolbar_elevation)
    var endElevation = elevation
    var startElevation = 0f

    if (removeElevation) {
        startElevation = elevation
        endElevation = 0f
    }
    if (cardElevation != endElevation)
        ValueAnimator().apply {
            duration = 250
            setFloatValues(startElevation, endElevation)
            setTarget(this)
            start()
        }.addUpdateListener {
            cardElevation = it.animatedValue as Float
        }
}

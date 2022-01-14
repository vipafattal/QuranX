package com.abedfattal.quranx.ui.common.extensions

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import androidx.annotation.StringRes

fun TextView.animateText(@StringRes newText: Int) {
    animateText(context.getString(newText))
}

fun TextView.animateText(newText: CharSequence) {
    AlphaAnimation(1.0f, 0.0f).run {
        duration = 200
        repeatCount = 1
        repeatMode = Animation.REVERSE

        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {}

            override fun onAnimationRepeat(animation: Animation?) {
                text = newText
            }
        })

        startAnimation(this)
    }
}
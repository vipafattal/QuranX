package com.abedfattal.quranx.ui.common.extensions

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import androidx.annotation.StringRes

fun TextView.animateText(@StringRes newText: Int) {
    animateText(context.getString(newText))
}

fun TextView.animateText(newText: CharSequence) {
    AlphaAnimation(0f, 1f).run {
        duration = 250
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                text = newText
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })

        startAnimation(this)
    }
}
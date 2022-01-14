package com.abedfattal.quranx.ui.library.utils

import android.annotation.SuppressLint
import android.text.method.ScrollingMovementMethod
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

object ScrollTextUtils {
    @SuppressLint("ClickableViewAccessibility")
    fun enableScroll(view: View) {

        view.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            if (event.action and MotionEvent.ACTION_MASK == MotionEvent.ACTION_UP)
                v.parent.requestDisallowInterceptTouchEvent(
                false
            )

            false
        }
    }
}
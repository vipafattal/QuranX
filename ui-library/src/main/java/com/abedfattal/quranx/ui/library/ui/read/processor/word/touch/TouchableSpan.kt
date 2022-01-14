package com.abedfattal.quranx.ui.library.ui.read.processor.word.touch

import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance
import android.view.MotionEvent
import android.text.TextPaint
import android.view.View

/**
 * If an object of this type is attached to the text of a TextView
 * with a movement method of LinkTouchMovementMethod, the affected spans of
 * text can be selected.  If touched, the [.onTouch] method will
 * be called.
 */
abstract class TouchableSpan : CharacterStyle(), UpdateAppearance {
    /**
     * Performs the touch action associated with this span.
     */
    abstract fun onTouch(widget: View?, m: MotionEvent?): Boolean

    /**
     * Could make the text underlined or change link color.
     */
    override fun updateDrawState(ds: TextPaint) {
        ds.isUnderlineText = false
        ds.isAntiAlias = true
    }
}
package com.abedfattal.quranx.ui.library.ui.read.processor.word.touch

import android.text.Selection
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.widget.TextView

class LinkTouchMovementMethod : LinkMovementMethod() {
    override fun onTouchEvent(
        widget: TextView, buffer: Spannable,
        event: MotionEvent
    ): Boolean {
        val action = event.action
        if (action == MotionEvent.ACTION_UP) {
            var x = event.x.toInt()
            var y = event.y.toInt()
            x -= widget.totalPaddingLeft
            y -= widget.totalPaddingTop
            x += widget.scrollX
            y += widget.scrollY
            val layout = widget.layout
            val line = layout.getLineForVertical(y)
            val off = layout.getOffsetForHorizontal(line, x.toFloat())
            val link: Array<com.abedfattal.quranx.ui.library.ui.read.processor.word.touch.TouchableSpan> =
                buffer.getSpans(
                    off,
                    off,
                    com.abedfattal.quranx.ui.library.ui.read.processor.word.touch.TouchableSpan::class.java
                )
            if (link.size != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onTouch(widget, event)
                } else if (action == MotionEvent.ACTION_DOWN) {
                    link[0].onTouch(widget, event)
                    Selection.setSelection(
                        buffer,
                        buffer.getSpanStart(link[0]),
                        buffer.getSpanEnd(link[0])
                    )
                }
                return true
            } else {
                Selection.removeSelection(buffer)
            }
        }
        return super.onTouchEvent(widget, buffer, event)
    }
}
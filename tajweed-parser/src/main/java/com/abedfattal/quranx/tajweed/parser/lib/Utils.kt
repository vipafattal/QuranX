package com.abedfattal.quranx.tajweed.parser.lib

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan

internal fun SpannableStringBuilder.setSplitSpan(ayahText: String, color: String) {
    val start = this.length
    append(ayahText)
    val end = this.length

    setSpan(
        ForegroundColorSpan(Color.parseColor(color)),
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}
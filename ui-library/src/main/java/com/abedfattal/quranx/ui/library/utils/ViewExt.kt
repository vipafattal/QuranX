package com.abedfattal.quranx.ui.library.utils

import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.StringRes
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ReadLibrary

fun TextView.setTextSizeFromType(@StringRes type: Int,wight:Float = 1f) {
    val textDimenRes = when (type) {
        R.string.small_font_size -> com.intuit.ssp.R.dimen._16ssp
        R.string.medium_font_size -> com.intuit.ssp.R.dimen._19ssp
        R.string.large_font_size -> com.intuit.ssp.R.dimen._25ssp
        R.string.x_large_font_size -> com.intuit.ssp.R.dimen._29ssp
        else -> throw IllegalArgumentException("Unknown text size type")
    }

    setTextSize(
        TypedValue.COMPLEX_UNIT_PX,
        ReadLibrary.app.resources.getDimension(textDimenRes) * wight
    )
}
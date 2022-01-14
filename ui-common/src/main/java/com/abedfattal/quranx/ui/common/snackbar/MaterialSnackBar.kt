package com.abedfattal.quranx.ui.common.snackbar

import android.view.ViewGroup
import com.abedfattal.quranx.ui.common.R
import com.abedfattal.quranx.ui.common.dp
import com.abedfattal.quranx.ui.common.extensions.drawableOf
import com.google.android.material.snackbar.Snackbar


fun Snackbar.material(isWithBottom: Boolean = false): Snackbar {
    view.background = drawableOf(R.drawable.bg_snackbar)
    val params = view.layoutParams as ViewGroup.MarginLayoutParams

    params.setMargins(
        params.leftMargin + dp(12),
        params.topMargin,
        params.rightMargin + dp(12),
        if (isWithBottom)
            params.bottomMargin + dp(105)
        else
            params.bottomMargin + dp(12)
    )
    view.layoutParams = params

    return this
}





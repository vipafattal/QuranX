package com.abedfattal.quranx.ui.common.extensions.view

import android.graphics.Color
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.abedfattal.quranx.ui.common.R
import com.abedfattal.quranx.ui.common.extensions.colorOf
import com.abedfattal.quranx.ui.common.extensions.colorAccent

fun SwipeRefreshLayout.setup() {
    setColorSchemeColors(Color.WHITE)
    setProgressBackgroundColorSchemeColor(colorOf(R.color.colorAccent))
}
package com.abedfattal.quranx.ui.common.snackbar

import android.widget.TextView
import com.abedfattal.quranx.ui.common.R
import com.abedfattal.quranx.ui.common.extensions.colorOf
import com.google.android.material.snackbar.Snackbar

fun Snackbar.setSnackbarTextColor(textColor: Int) {

    val tv: TextView = view.findViewById(com.google.android.material.R.id.snackbar_text)
    tv.setTextColor(colorOf(textColor))

}
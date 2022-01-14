package com.abedfattal.quranx.ui.common.extensions

import androidx.core.view.ViewCompat
import com.abedfattal.quranx.ui.common.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.radiobutton.MaterialRadioButton

/**
 * Created by  on
 */

fun MaterialButton.enabled() {
    isEnabled = true
    ViewCompat.setElevation(this, resources.getDimension(R.dimen.buttons_elevation))
    setBackgroundColor(colorOf(R.color.colorAccent))
    setTextColor(colorOf(android.R.color.white))
}

fun MaterialButton.disabled() {
    isEnabled = false
    ViewCompat.setElevation(this, 0f)
    setBackgroundColor(colorOf(R.color.colorTextSecondary))
    setTextColor(colorOf(R.color.colorPrimaryDark))
}

fun MaterialRadioButton.disabled() {
    isEnabled = false

    setTextColor(colorOf(R.color.colorTextSecondary))
}

fun MaterialButton.textButtonEnabled() {
    isEnabled = true
    setTextColor(colorOf(R.color.colorAccent))
}

fun MaterialButton.textButtonDisabled() {
    isEnabled = false
    setTextColor(colorOf(R.color.colorTextSecondary))
}
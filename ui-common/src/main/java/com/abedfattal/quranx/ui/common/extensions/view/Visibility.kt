package com.abedfattal.quranx.ui.common.extensions.view

import android.view.View
import androidx.core.view.ViewCompat


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}


infix fun View.scale(value: Float) {
    scaleX = value
    scaleY = value
}

fun View.scale(X: Float, Y: Float) {
    scaleX = X
    scaleY = Y
}



infix fun View.applyTransitionName(name:String){
    ViewCompat.setTransitionName(this,name)
}
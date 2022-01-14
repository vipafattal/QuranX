package com.abedfattal.quranx.ui.common.extensions.view

import android.view.View
import android.view.ViewGroup

/**
 * control all child visibility of ViewGroup
 */

fun ViewGroup.allChildesInvisible() {
    for (child in 0 until childCount) {
        getChildAt(child).invisible()
    }
}


fun ViewGroup.allChildesGone() {
    for (child in 0 until childCount) {
        getChildAt(child).gone()
    }
}

fun ViewGroup.allChildesVisibleExcept(v: View) {
    for (i in 0 until this.childCount) {
        val child = this.getChildAt(i)
        if (child == v)
            continue
        else
            child.visible()
    }
}

fun ViewGroup.allChildesGoneExcept(v: View) {
    for (i in 0 until this.childCount) {
        val child = this.getChildAt(i)
        if (child == v)
            continue
        else
            child.gone()
    }
}

fun ViewGroup.allChildesInvisibleExcept(v: View) {
    for (i in 0 until this.childCount) {
        val child = this.getChildAt(i)

        if (child == v)
            continue
        else
            child.invisible()
    }
}


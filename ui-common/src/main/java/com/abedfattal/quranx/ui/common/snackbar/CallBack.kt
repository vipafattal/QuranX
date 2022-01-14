package com.abedfattal.quranx.ui.common.snackbar

import com.abedfattal.quranx.ui.common.unitFun
import com.google.android.material.snackbar.Snackbar


inline fun Snackbar.onDismissed(crossinline block: unitFun) {
    addCallback(object : Snackbar.Callback() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            super.onDismissed(transientBottomBar, event)
            block()
        }

    })
}

inline fun Snackbar.onShown(crossinline block: unitFun) {
    addCallback(object : Snackbar.Callback() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            super.onDismissed(transientBottomBar, event)
            block()
        }
    })
}
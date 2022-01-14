package com.abedfattal.quranx.ui.common.snackbar

import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.abedfattal.quranx.ui.common.extensions.colorAccent
import com.abedfattal.quranx.ui.common.unitFun
import com.google.android.material.snackbar.Snackbar

/**
 * show Action with snackbar with dismiss on action text clicked
 */
fun Snackbar.withAction(actionText: String = "Dismiss",
                        actionTextColor: Int? = null
): Snackbar {

    setActionTextColor(actionTextColor ?: context.colorAccent)
    setAction(actionText) {
        dismiss()
    }

    return this
}

/**
 * show Action with snackbar with Lambda on action text clicked
 */
inline fun Snackbar.withAction(
    @StringRes actionText: Int,
    @ColorInt actionTextColor: Int? = null,
    crossinline onActionClick: unitFun
): Snackbar {

    setActionTextColor(actionTextColor ?: context.colorAccent)
    setAction(actionText) {
        onActionClick.invoke()
    }

    return this
}
/*
we can write in one function but if we want to make nullable lambda we will get an error:
inline fun Snackbar.showAction(actionText: String = "Dismiss",
                               actionTextColor: Int? = null,
                               crossinline onActionClick: unitFun?) {
    setActionTextColor(actionTextColor ?: getThemeAccentColor(context))
    setAction(actionText) {
        onActionClick.invoke() ?: dismiss()
    }
}
 */
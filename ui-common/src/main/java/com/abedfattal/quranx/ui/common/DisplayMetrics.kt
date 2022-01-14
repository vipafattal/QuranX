package com.abedfattal.quranx.ui.common

import android.util.DisplayMetrics
import android.util.TypedValue


/**
 * Defines a scale factor to use for scaling what'imageItem displayed
 */
// Set the screen width and height

private var dm: DisplayMetrics = CommonUI.context.resources.displayMetrics

val screenWidth: Int = dm.widthPixels
val screenHeight: Int = dm.heightPixels


fun density() = dm.density

fun dp(px: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px,
            CommonUI.context.resources.displayMetrics)

fun dp(px: Int):Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px.toFloat(),
            CommonUI.context.resources.displayMetrics).toInt()

fun pixelsToSp(px: Float): Float {
    val scaledDensity = CommonUI.context.resources.displayMetrics.scaledDensity
    return px / scaledDensity
}

/*    fun density(): Float {
        val x = dm.widthPixels
        val y = dm.heightPixels
        if (x < y) {
            val xr = dm.widthPixels / 320.0f
            val yr = dm.heightPixels / 480.0f
            return Math.initMin(xr, yr)
        } else {//landscape
            val xr = dm.widthPixels / 480.0f
            val yr = dm.heightPixels / 320.0f
            return Math.initMin(xr, yr)
        }
    }*/



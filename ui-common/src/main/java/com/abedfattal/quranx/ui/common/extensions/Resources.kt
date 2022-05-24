package com.abedfattal.quranx.ui.common.extensions

import android.content.Context
import android.graphics.Typeface
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.abedfattal.quranx.ui.common.CommonUI

fun colorOf(colorID: Int, context: Context = CommonUI.context): Int =
    ContextCompat.getColor(context, colorID)

fun drawableOf(@DrawableRes drawableImg: Int, context: Context = CommonUI.context) =
    ContextCompat.getDrawable(context, drawableImg)

fun stringer(stringID: Int, mContext: Context = CommonUI.context): String =
    mContext.resources.getString(stringID)

fun stringify(stringID: Int, mContext: Context = CommonUI.context): String =
    mContext.resources.getString(stringID)

fun fontOf(fontID: Int, context: Context = CommonUI.context): Typeface? =
    ResourcesCompat.getFont(context, fontID)


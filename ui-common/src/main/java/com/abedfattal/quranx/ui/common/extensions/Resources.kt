package com.abedfattal.quranx.ui.common.extensions

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.abedfattal.quranx.ui.common.CommonUI

fun colorOf(colorID: Int, context: Context = CommonUI.context): Int =
    ContextCompat.getColor(context, colorID)

fun drawableOf(@DrawableRes drawableImg: Int, context: Context = CommonUI.context) =
    ContextCompat.getDrawable(context, drawableImg)

fun stringer(stringID: Int, mContext: Context = CommonUI.context): String =
    mContext.resources.getString(stringID)

fun stringify(stringID: Int, mContext: Context = CommonUI.context): String =
    mContext.resources.getString(stringID)
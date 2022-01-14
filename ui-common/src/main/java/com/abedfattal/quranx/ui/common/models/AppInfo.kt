package com.abedfattal.quranx.ui.common.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class AppInfo(@StringRes val appName: Int, val googlePlayLink:String, @DrawableRes val bookShortcut:Int, val mainActivityPath:String)
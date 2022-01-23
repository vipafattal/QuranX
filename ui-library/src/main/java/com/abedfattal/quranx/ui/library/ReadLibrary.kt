package com.abedfattal.quranx.ui.library

import android.annotation.SuppressLint
import android.content.Context
import com.abedfattal.quranx.ui.common.CommonUI
import com.abedfattal.quranx.ui.common.preferences.AppPreferences


@SuppressLint("StaticFieldLeak")
object ReadLibrary {

    internal lateinit var app: Context
        private set

    internal val appPreferences: AppPreferences by lazy { AppPreferences(context = app) }
    internal var userId: String? = null
    internal var mainActivityPath: String = ""
        private set

    fun init(context: Context, mainActivityPath: String) {
        app = context
        this.mainActivityPath = mainActivityPath
        CommonUI.init(context)
    }


    const val MANAGE_LIBRARY_FRAGMENT = 0
    const val READ_LIBRARY_FRAGMENT = 1
}
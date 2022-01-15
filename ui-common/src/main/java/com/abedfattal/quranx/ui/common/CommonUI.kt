package com.abedfattal.quranx.ui.common

import android.annotation.SuppressLint
import android.content.Context
import com.abedfattal.quranx.ui.common.preferences.AppPreferences

@SuppressLint("StaticFieldLeak")
object CommonUI {

    val preferences: AppPreferences by lazy { AppPreferences(context = context) }

    internal lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

}
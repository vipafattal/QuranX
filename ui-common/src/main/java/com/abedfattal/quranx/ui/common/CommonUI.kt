package com.abedfattal.quranx.ui.common

import android.annotation.SuppressLint
import android.content.Context
import com.abedfattal.quranx.ui.common.preferences.AppPreferences

class CommonUI internal constructor(mContext: Context) {

    init {
        context = mContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        internal lateinit var context: Context
            private set

        val userPreferences: AppPreferences by lazy { AppPreferences() }
    }

}
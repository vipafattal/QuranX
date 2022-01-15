package com.abedfattal.quranx.ui.library

import android.annotation.SuppressLint
import android.content.Context
import com.abedfattal.quranx.ui.common.CommonUI
import com.abedfattal.quranx.ui.common.preferences.AppPreferences
import com.abedfattal.quranx.ui.library.framework.di.KoinModules
import org.koin.core.context.loadKoinModules


@SuppressLint("StaticFieldLeak")
object ReadLibrary {

    internal lateinit var app: Context
        private set

    internal val appPreferences: AppPreferences by lazy { AppPreferences(context = app) }
    var userId: String? = null
    var mainActivityPath: String = ""
        private set

    fun init(context: Context, mainActivityPath: String) {
        app = context
        this.mainActivityPath = mainActivityPath
        CommonUI.init(context)
        initInjection()
    }

    private fun initInjection() {
        loadKoinModules(KoinModules.modules)
    }

    const val MANAGE_LIBRARY_FRAGMENT = 0
    const val READ_LIBRARY_FRAGMENT = 1
}
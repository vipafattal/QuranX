package com.abedfattal.quranx.ui.library

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.abedfattal.quranx.core.QuranXCore
import com.abedfattal.quranx.core.framework.db.DBConfiguration
import com.abedfattal.quranx.ui.common.CommonUI
import com.abedfattal.quranx.ui.common.models.AppInfo
import com.abedfattal.quranx.ui.common.preferences.AppPreferences
import com.abedfattal.quranx.ui.library.framework.di.KoinModules
import org.koin.core.context.loadKoinModules


@SuppressLint("StaticFieldLeak")
object ReadLibrary {

    internal lateinit var app: Context
        private set

    internal val appPreferences: AppPreferences by lazy{ AppPreferences(context = app) }
    var userId: String? = null
    internal lateinit var appInfo: AppInfo



    fun init(context: Context, @StringRes  appName: Int,  googlePlayLink:String, @DrawableRes bookShortcut:Int,  mainActivityPath:String) {
        app = context
        this.appInfo = AppInfo(appName, googlePlayLink, bookShortcut, mainActivityPath)
        CommonUI.init(context, appInfo)
        initInjection()
    }

    private fun initInjection() {
        loadKoinModules(KoinModules.modules)
    }

    const val MANAGE_LIBRARY_FRAGMENT = 0
    const val READ_LIBRARY_FRAGMENT = 1
}
package com.abedfattal.quranx.ui.library

import android.annotation.SuppressLint
import android.content.Context
import com.abedfattal.quranx.ui.common.preferences.AppPreferences
import com.abedfattal.quranx.ui.library.config.LibraryConfig


@SuppressLint("StaticFieldLeak")
object ReadLibrary {

    internal val tempPreferences: AppPreferences by lazy { AppPreferences("library-quran-Temp") }
    internal lateinit var app: Context private set
    internal var userId: String? = null
    internal var mainActivityPath: String = ""
        private set

    internal lateinit var libraryConfig: LibraryConfig

    fun init(
        context: Context,
        mainActivityPath: String,
        libraryConfig: LibraryConfig = LibraryConfig()
    ) {
        app = context
        this.mainActivityPath = mainActivityPath
        this.libraryConfig = libraryConfig
    }




}
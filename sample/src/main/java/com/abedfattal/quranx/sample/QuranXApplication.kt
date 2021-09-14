package com.abedfattal.quranx.sample

import android.app.Application
import com.abedfattal.quranx.core.ReadLibrary

class QuranXApplication  : Application() {
    override fun onCreate() {
        super.onCreate()
        ReadLibrary.init(this)
    }
}
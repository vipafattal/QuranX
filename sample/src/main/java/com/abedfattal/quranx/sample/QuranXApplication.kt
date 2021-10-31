package com.abedfattal.quranx.sample

import android.app.Application
import com.abedfattal.quranx.core.QuranXCore
import com.abedfattal.quranx.core.framework.db.DBConfiguration

class QuranXApplication  : Application() {
    override fun onCreate() {
        super.onCreate()
        //val databaseConfig = DBConfiguration(databaseName = "sample-db",prepackagedDatabase = "default-mushaf-db")
        QuranXCore.init(this)
    }
}
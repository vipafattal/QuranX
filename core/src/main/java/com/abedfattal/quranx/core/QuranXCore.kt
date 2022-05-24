package com.abedfattal.quranx.core

import android.annotation.SuppressLint
import android.content.Context
import com.abedfattal.quranx.core.framework.db.DBConfiguration


/**
 * An initializer for library configuration.
 * Must be initialized before using any functionality from the package,
 * mainly it's good pattern to be call [init] inside your [android.app.Application.onCreate] subclass.
 *
 * */
@SuppressLint("StaticFieldLeak")
object QuranXCore {

    internal lateinit var app: Context
        private set

    internal lateinit var configuration: DBConfiguration
        private set


    internal fun init(mContext: Context) {
        app = mContext
        configuration = DBConfiguration()
    }

    private fun dbConfig(dbConfig:DBConfiguration){
        configuration  = dbConfig
    }

}
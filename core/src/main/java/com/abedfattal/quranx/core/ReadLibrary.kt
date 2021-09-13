package com.abedfattal.quranx.core

import android.annotation.SuppressLint
import android.content.Context


/**
 * An initializer for library configuration.
 * Must be initialized before using any functionality from the package,
 * mainly it's good pattern to be call [init] inside your [android.app.Application.onCreate] subclass.
 *
 * */
@SuppressLint("StaticFieldLeak")
object ReadLibrary {

    internal lateinit var app: Context
        private set

    fun init(mContext: Context) {
        app = mContext
    }
}
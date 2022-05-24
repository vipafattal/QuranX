package com.abedfattal.quranx.ui.common

import android.app.PendingIntent
import android.os.Build

fun getPendingIntentFlags(isMutable: Boolean = false) =
    when {
        isMutable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE

        !isMutable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ->
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

        else -> PendingIntent.FLAG_UPDATE_CURRENT
    }
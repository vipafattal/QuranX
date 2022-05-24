package com.abedfattal.quranx.ui.common

import android.content.Context
import androidx.startup.Initializer

class CommonUILibraryInitializer : Initializer<CommonUI> {
    override fun create(context: Context): CommonUI {
        return CommonUI(context)
    }
    override fun dependencies(): List<Class<out Initializer<CommonUI>>> {
        // No dependencies on other libraries.
        return emptyList()
    }
}
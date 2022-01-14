package com.abedfattal.quranx.ui.library.ui.read.menus

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.annotation.MenuRes

abstract class ReadingMenu(
    @MenuRes
    private val menuRes: Int,
    protected val menu: Menu,
    protected val inflater: MenuInflater,
) {
    init {
        inflater.inflate(menuRes, menu)
    }

    abstract fun buildMenu()

   protected fun findItem(id:Int)=menu.findItem(id)
}
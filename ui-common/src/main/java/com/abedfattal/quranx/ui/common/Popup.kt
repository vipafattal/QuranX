package com.abedfattal.quranx.ui.common

import android.view.Gravity
import android.view.View
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu

fun View.createPopup(
    @MenuRes popupMenuId: Int,
    showPopup: Boolean = true,
    popupGravity: Int = Gravity.END,
    onItemClickListener: (Int) -> Unit
): PopupMenu {
    val popup = PopupMenu(
        context,
        this,
        popupGravity,
        android.R.attr.popupMenuStyle,
        R.style.popupMenu
    )
    popup.inflate(popupMenuId)
    popup.setOnMenuItemClickListener { menuItem ->
        onItemClickListener(menuItem.itemId)
        true
    }

    if (showPopup) popup.show()

    return popup
}

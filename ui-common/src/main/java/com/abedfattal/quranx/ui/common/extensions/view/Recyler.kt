package com.abedfattal.quranx.ui.common.extensions.view

import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.ui.common.R
import com.abedfattal.quranx.ui.common.extensions.colorOf

inline fun RecyclerView.onScroll(crossinline action: (dx: Int, dy: Int) -> Unit) {

    addOnScrollListener(object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            action(dx, dy)
        }
    })
}

fun RecyclerView.addDividerDecoration(@RecyclerView.Orientation orientation: Int) {
    val dividerItemDecoration = DividerItemDecoration(context, orientation)
    DrawableCompat.setTint(dividerItemDecoration.drawable!!, colorOf(R.color.colorSecondaryItems))
    addItemDecoration(dividerItemDecoration)
}
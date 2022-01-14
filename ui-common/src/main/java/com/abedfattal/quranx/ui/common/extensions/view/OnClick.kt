package com.abedfattal.quranx.ui.common.extensions.view

import android.view.View


inline fun <T : View> T.onClick(crossinline block: T.() -> Unit) {
    setOnClickListener {
        block()
    }
}


inline fun onClicks(vararg views: View, crossinline block: (View) -> Unit) {

    val clickListener = View.OnClickListener { block.invoke(it) }

    for (view in views)
        view.setOnClickListener(clickListener)
}



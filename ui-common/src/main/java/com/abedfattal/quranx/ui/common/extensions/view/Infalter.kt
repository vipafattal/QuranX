package com.abedfattal.quranx.ui.common.extensions.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

val ViewGroup.inflater: LayoutInflater
    get() = LayoutInflater.from(context)


infix fun ViewGroup.inflater(layoutRes: Int): View {
    return inflater.inflate(layoutRes, this, false)
}
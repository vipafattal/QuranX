package com.abedfattal.quranx.sample.utils

import androidx.lifecycle.*


inline fun <T> LiveData<T>.observer(owner: LifecycleOwner, crossinline doOnObserver: (T) -> Unit) =
    observe(owner, Observer {
        doOnObserver(it)
    })

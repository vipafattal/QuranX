package com.abedfattal.quranx.sample.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*


inline fun <reified  T : ViewModel> Fragment.viewModelOf() =
    ViewModelProvider(activity!!).get(T::class.java)

inline fun <reified  T : ViewModel> AppCompatActivity.viewModelOf() =
    ViewModelProvider(this).get(T::class.java)

fun <T : ViewModel> AppCompatActivity.viewModelOf(
    viewModelClass: Class<T>,
    factoryViewModel: ViewModelProvider.Factory
) =
    ViewModelProvider(this, factoryViewModel).get(viewModelClass)

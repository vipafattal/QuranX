package com.abedfattal.quranx.ui.common

import androidx.annotation.StringRes
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

inline fun PreferenceFragmentCompat.onPreferencesClick(
    @StringRes key: Int,
    crossinline doOnClick: unitFun
) {
    findPreference<Preference>(getString(key))!!.setOnPreferenceClickListener {
        doOnClick()
        true
    }
}

inline fun Preference.onPreferencesClick(
    crossinline doOnClick: unitFun
) {
    setOnPreferenceClickListener {
        doOnClick()
        true
    }
}
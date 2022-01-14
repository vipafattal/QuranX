package com.abedfattal.quranx.ui.common.extensions

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.util.TypedValue
import androidx.annotation.AttrRes
import com.abedfattal.quranx.ui.common.R


inline fun <reified T : Any> Context.launchActivity() {
    val intent = newIntent<T>()
    startActivity(intent)
}

inline fun <reified T : Any> Context.newIntent(): Intent = Intent(this, T::class.java)

fun Context.valueOfAttribute(@AttrRes attribute: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attribute, typedValue, true)
    return typedValue.data
}


fun Context.goTo(url: String) {
    val uriUrl = Uri.parse(url)
    val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
    startActivity(launchBrowser)
}

fun Context.isDarkThemeOn() =
    when (resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> true
        Configuration.UI_MODE_NIGHT_NO -> false
        Configuration.UI_MODE_NIGHT_UNDEFINED -> false
        else -> false
    }


val Context.colorAccent: Int
    get() = valueOfAttribute(R.attr.colorAccent)

val Context.colorPrimary: Int
    get() = valueOfAttribute(R.attr.colorPrimary)

val Context.colorPrimaryDark: Int
    get() = valueOfAttribute(R.attr.colorPrimaryDark)

val Context.colorSecondary: Int
    get() = valueOfAttribute(R.attr.colorSecondary)

val Context.colorBackground: Int
    get() = valueOfAttribute(android.R.attr.colorBackground)

val Context.textColor: Int
    get() = valueOfAttribute(android.R.attr.textColor)
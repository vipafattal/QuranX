package com.abedfattal.quranx.core.utils

import java.util.*

/** @suppress */
val Locale.isArabic: Boolean
    get() = this == Locale("ar")
val Locale.isNotArabic: Boolean
    get() = !isArabic
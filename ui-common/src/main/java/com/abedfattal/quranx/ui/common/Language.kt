package com.abedfattal.quranx.ui.common

import java.util.*

/**
 * Created by Abed on
 */
private var numbers = arrayOf(
    '0' to '٠',
    '1' to '١',
    '2' to '٢',
    '3' to '٣',
    '4' to '٤',
    '5' to '٥',
    '6' to '٦',
    '7' to '٧',
    '8' to '٨',
    '9' to '٩'
)

fun Int.toLocalizedNumber(forceArabic: Boolean = false): String {

    var output = this.toString()
    if (Locale.getDefault().language == "ar" || forceArabic)
        for ((englishNumber, arabicNumber) in numbers)
            output = output.replace(englishNumber, arabicNumber)

    return output
}


fun String.toLocalizedNumber(forceArabic: Boolean = false): String {
    return toInt().toLocalizedNumber(forceArabic)
}

fun getLocalizedLanguage(code: String): String {
    return when (code) {
        "ar" -> "العربية"
        "en" -> "English"
        "fr" -> "Français"
        "ru" -> "Русский"
        "de" -> "Deutsch"
        "es" -> "Español"
        "tr" -> "Türkçe"
        "zh" -> "中文"
        "th" -> "ไทย"
        "ur" -> "اردو"
        "bn" -> "বাংলা"
        "bs" -> "Bosanski"
        "ug" -> "ئۇيغۇرچە"
        "fa" -> "فارسى"
        "tg" -> "Тоҷикӣ"
        "ml" -> "മലയാളം"
        "tl" -> "Tagalog"
        "id" -> "Indonesia"
        "pt" -> "Português"
        "ha" -> "Hausa"
        "sw" -> "Kiswahili"
        "cs" -> "Čeština"
        "dv" -> "ދިވެހި"
        "hi" -> "हिन्दी،हिंदी"
        "it" -> "Italiano"
        "ja" -> "日本語 (にほんご)"
        "ko" -> "한국어, 조선어"
        "ku" -> "Kurdî, كوردی"
        "nl" -> "Nederlands،Vlaams"
        "no" -> "Norsk"
        "pl" -> "Język polski،polszczyzna"
        "ro" -> "Limba română"
        "sd" -> "सिन्धी"
        "so" -> "Soomaaliga"
        "sq" -> "Shqip"
        "sv" -> "Svenska"
        "ta" -> "தமிழ்"
        "ty" -> "Reo Tahiti"
        "tt" -> "татар теле"
        "uz" -> "Oʻzbek, Ўзбек"
        "az" -> "Azərbaycan dili"
        else -> code
    }
}
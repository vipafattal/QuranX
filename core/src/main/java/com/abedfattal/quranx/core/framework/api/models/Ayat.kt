package com.abedfattal.quranx.core.framework.api.models

import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.core.model.Edition
import com.google.gson.annotations.SerializedName
/** @suppress */
object Ayat {

    data class QuranPageData(
        @SerializedName("data") val pageData: Page,
        val code: Int,
        val status: String
    )

    data class Page(
        val number: Int,
        val ayahs: List<Aya>,
        val edition: Edition,
    )


    data class QuranJuzData(
        @SerializedName("data") val juzData: Juz,
        val code: Int,
        val status: String
    )


    data class Juz(
        val number: Int,
        val ayahs: List<Aya>,
        val surah: Map<String, Quran.QuranSurah>,
        val edition: Edition
    )

    data class AyaData(
        @SerializedName("data") val aya: Aya,
        val code: Int,
        val status: String
    )
}
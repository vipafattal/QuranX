package com.abedfattal.quranx.core.framework.api.models

import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.core.model.Surah
import com.google.gson.annotations.SerializedName


/** @suppress */
object Quran {

    data class QuranBook(@SerializedName("data") val quran: QuranData, val code: Int, val status: String)
    data class QuranData(val surahs: List<QuranSurah>, val edition: Edition)
    data class QuranSurah(
        val number: Int,
        val name: String,
        val englishName: String,
        val englishNameTranslation: String,
        val revelationType: String,
        @SerializedName("ayahs")
        val ayat: List<Aya>
    ) {
        fun toSurah(editionId: String): Surah {
            return Surah(
                number = number,
                name = name,
                englishName = englishName,
                englishNameTranslation = englishNameTranslation,
                revelationType = revelationType,
                numberOfAyahs = ayat.size,
                surahEdition = editionId
            )
        }
    }
}
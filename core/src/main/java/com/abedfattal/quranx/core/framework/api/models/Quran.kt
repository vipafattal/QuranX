package com.abedfattal.quranx.core.framework.api.models

import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.core.model.Surah
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/** @suppress */
object Quran {

    @Serializable
    data class QuranBook(
        @SerialName("data")
        @SerializedName("data") val quran: QuranData, val code: Int, val status: String
    )

    @Serializable
    data class QuranData(val surahs: List<QuranSurah>, val edition: Edition)

    @Serializable
    data class QuranSurah(
        val number: Int,
        val name: String,
        val englishName: String,
        val englishNameTranslation: String,
        val revelationType: String,
        @SerialName("ayahs")
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
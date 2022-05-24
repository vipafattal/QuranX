package com.abedfattal.quranx.core.framework.api.models

import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.core.model.AyatWithEdition
import com.google.gson.annotations.SerializedName

/** @suppress */
object Search {
    data class QuranResults(val data: ResultsData, val code: Int, val status: String)
    data class ResultsData(val count: Int, @SerializedName("matches") val results: List<Result>) {
        // Todo document the following:
        //    var juz: Int = -1,
        //    var page: Int = -1,
        //    var hizbQuarter: Int = -1,
        //    since the api does not provide them
        @Suppress("RedundantSuspendModifier")
        suspend fun toQuranWithEdition(): List<AyatWithEdition> {
            val quranWithEditionList = mutableListOf<AyatWithEdition>()
            val ayat = mutableListOf<Aya>()

            var edition = ""
            var resultIndex = -1
            results.sortedByDescending { it.edition.identifier }.map {
                resultIndex++

                ayat += Aya(
                    number = it.numberInQuran,
                    text = it.text,
                    numberInSurah = it.numberInSurah,
                    surah_number = it.surah.number,
                    ayaEdition = it.edition.identifier
                )

                if (edition != it.edition.identifier || results.lastIndex == resultIndex) {
                    quranWithEditionList += AyatWithEdition(it.edition, ayat.toList())
                    ayat.clear()
                }
                edition = it.edition.identifier
            }
            return quranWithEditionList
        }
    }

    data class Result(
        @SerializedName("number")
        val numberInQuran: Int,
        val numberInSurah: Int,
        val text: String,
        val edition: com.abedfattal.quranx.core.model.Edition,
        val surah: Quran.QuranSurah,
    )
}
package com.abedfattal.quranx.wordsprocessor.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

/**
 * A model class for v2 extracting method @see [com.abedfattal.words_parser.WordByWordEnglish.getWordOfAyaV2]
 * which used as holder for text that is derivative from `quran-wordbyword-2` edition.
 * see [https://api.alquran.cloud/v1/ayah/2/quran-wordbyword-2]
 */
@Serializable
data class AyaWordV2 internal constructor(
    @SerialName("word_arabic") val arabicWord: String,
    @SerialName("word_translation") val wordTranslation: String,
    @SerialName("word_transliteration") val wordTransliteration: String,
    @SerialName("word_number_in_ayah") val wordNumberInAya: Int,
    @SerialName("word_number_in_quran") val wordNumberInQuran: Int,
    @SerialName("word_number_in_surah") val wordNumberInSurah: Int,
) {
    companion object {
        fun fromJsonList(jsonAya: String): List<AyaWordV2> {
            return Json{ignoreUnknownKeys = true}.decodeFromString(ListSerializer(serializer()), jsonAya)
        }
    }
}
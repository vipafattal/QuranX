package com.abedfattal.quranx.wordparser

import AyaWordV1
import com.abedfattal.quranx.wordparser.model.AyaWordV2

/**
 * [WordByWordEnglish] use to extract Arabic Quran words meaning in English.
 * It only supports the following text editions [quran-wordbyword, quran-wordbyword-2].
 * It's recommend to use [getWordOfAyaV2] method which is based on `quran-wordbyword-2` for better results and
 * also you get transliteration for out the box.
 */
object WordByWordEnglish {
    /**
     * It's the recommended method to extract words meaning and transliteration in English from [ayaText].
     * @param ayaText must be derivative from `quran-wordbyword-2` edition otherwise it won't work.
     * @return [List<AyaWordV2>]
     * for input sample see [https://api.alquran.cloud/v1/ayah/2/quran-wordbyword-2]
     */
    fun getWordOfAyaV2(ayaText: String): List<AyaWordV2> {
        require(ayaText.isNotEmpty())
        return AyaWordV2.fromJsonList(ayaText)
    }

    /**
     * Extract words meaning in English from [ayaText].
     * @param ayaText must be derivative from `quran-wordbyword` edition otherwise it won't work.
     * @return [List<AyaWordV2>]
     * for input sample see [https://api.alquran.cloud/v1/ayah/2/quran-wordbyword]
     */
    fun getWordOfAyaV1(ayaText: String): List<AyaWordV1> {
        require(ayaText.isNotEmpty())
        return AyaWordV1.getAyatWord(ayaText)
    }
}
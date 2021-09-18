package com.abedfattal.quranx.wordsprocessor


import com.abedfattal.quranx.wordsprocessor.WordByWordEnglish.getWordOfAyaV2
import com.abedfattal.quranx.wordsprocessor.model.AyaWordV1
import com.abedfattal.quranx.wordsprocessor.model.AyaWordV2

/**
 * [WordByWordEnglish] use to extract Arabic Quran words meaning in English.
 * It only supports the following text editions [quran-wordbyword, quran-wordbyword-2].
 * It's recommend to use [getWordOfAyaV2] method which is based on `quran-wordbyword-2` for better results and
 * also you get transliteration for out the box.
 *
 * @sample com.abedfattal.quranx.sample.wordsprocessor.WordsParserActivity
 */
object WordByWordEnglish {
    /**
     * It's the recommended method to extract words meaning and transliteration in English from [ayaText].
     *
     * @sample com.abedfattal.quranx.sample.wordsprocessor.VerseViewModel.getVerseWithTranslation
     *
     * @param ayaText must be derivative from `quran-wordbyword-2` edition otherwise it won't work.
     *
     * @return [AyaWordV2] list that contains word meaning, and translation plus the transliteration.
     */
    fun getWordOfAyaV2(ayaText: String): List<AyaWordV2> {
        require(ayaText.isNotEmpty())
        return AyaWordV2.fromJsonList(ayaText)
    }

    /**
     * Extract words meaning in English from [ayaText]
     *
     * @param ayaText must be derivative from `quran-wordbyword` edition otherwise it won't work.
     *
     * @return [AyaWordV1] list that contains word meaning, and translation.
     */
    fun getWordOfAyaV1(ayaText: String): List<AyaWordV1> {
        require(ayaText.isNotEmpty())
        return AyaWordV1.getAyatWord(ayaText)
    }
}
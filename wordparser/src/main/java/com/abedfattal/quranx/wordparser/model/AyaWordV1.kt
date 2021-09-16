package com.abedfattal.quranx.wordparser.model

/**
 * A model class for v1 extracting method @see [com.abedfattal.words_parser.WordByWordEnglish.getWordOfAyaV1]
 * which used as holder for text that is derivative from `quran-wordbyword` edition.
 * see [https://api.alquran.cloud/v1/ayah/2/quran-wordbyword]
 */
data class AyaWordV1 internal constructor(val arabic: String, val english: String) {
    companion object {
        fun getAyatWord(rawAyah: String): List<AyaWordV1> {

            val ayahSplits = rawAyah.replace("[\\[0-9|$]".toRegex(), "%")
                .replace("[0-9]".toRegex(), "")
                .split("[%]+".toRegex())

            val wordsTranslation = mutableListOf<AyaWordV1>()

            for (index in ayahSplits.indices) {
                if (index + 1 == ayahSplits.lastIndex) break
                if (index != 0 && index % 2 == 1) continue
                wordsTranslation += AyaWordV1(ayahSplits[index], ayahSplits[index + 1])
            }
            return wordsTranslation
        }


    }
}
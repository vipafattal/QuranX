package com.abedfattal.ui.supported.edition

import com.abedfattal.quranx.core.model.Edition

object SupportedUiEditions {

    val QURAN_UTHMANI = Edition.quranEdition(
        id = "quran-uthmani-quran-academy",
        name = "القرآن الكريم برسم العثماني",
        englishName = "Quran Uthmani",
    )

    @JvmField
    val QURAN_SIMPLE = Edition.quranEdition(
        "quran-simple-clean",
        name = "القرآن الكريم البسيط (بدون تشكيل)",
        englishName = "Quran Uthmani (Minimal punctuation)",
    )

    @JvmField
    val QURAN_TAJWEED = Edition.quranEdition(
        id = "quran-tajweed",
        name = "القرآن الكريم المجود (ملون)",
        englishName = "Quran Mujawad (Colored rules)",
    )


    @JvmField
    val EN_WORD_BY_WORD = Edition(
        identifier = "quran-wordbyword-2",
        language = "en",
        name = "Quran words meaning & transliteration",
        englishName = "Quran words meaning & transliteration",
        type = Edition.TYPE_VERSE_BY_VERSE,
        format = Edition.FORMAT_TEXT
    )

    @JvmField
    val ALL_EDITIONS = listOf(QURAN_UTHMANI, QURAN_TAJWEED, QURAN_SIMPLE,)

    @JvmField
    val ALL_EDITIONS_IDS = listOf(QURAN_UTHMANI.identifier, QURAN_TAJWEED.identifier, QURAN_SIMPLE.identifier)

    @JvmField
    val BLACKLISTED_TRANSLATION = listOf("quran-buck")
}
package com.abedfattal.quranx.core.model

/**
 * This [AyatInfoWithTafseer] model is used by database to query a list of verses ([Aya]) with following [Edition].
 */
data class AyatInfoWithTafseer(
    var surah: Surah?,
    val tafseerList: List<AyaWithInfo>?,
    val quranList: List<AyaWithInfo>?
)
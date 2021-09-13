package com.abedfattal.quranx.core.model

/**
 * This [AyatWithTafseer] model is used by database to query a list of verses ([Aya]) with following [Edition].
 */
data class AyatWithTafseer(
    var surah: Surah?,
    val tafseerList: List<Aya>?,
    val quranList: List<Aya>?
)
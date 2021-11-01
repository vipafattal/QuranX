package com.abedfattal.quranx.core.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * This [AyaWithInfo] model is used by database to query a verse ([Aya]) with [Edition] and get the [bookmark] state.
 *
 * @property aya represents the verse.
 * @property surah represents the verse surah.
 * @property edition represents the verse edition.
 * @property bookmark represents the verse bookmark status, which is null if the verse is not bookmarked.
 */
data class AyaWithInfo(
    @Embedded
    val aya: Aya,
    @Relation(
        parentColumn = "numberInSurah",
        entityColumn = "surahNumberInMushaf"
    )
    var surah: Surah?,
    @Relation(
        parentColumn = "ayaEdition",
        entityColumn = "id",
        entity = Edition::class
    )
    val edition: Edition,
    @Relation(
        parentColumn = "ayaNumberInMushaf",
        entityColumn = "bookmark_ayaNumber",
        entity = Bookmark::class
    )
    val bookmark: Bookmark?
)

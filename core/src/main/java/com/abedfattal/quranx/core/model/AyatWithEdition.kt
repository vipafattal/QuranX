package com.abedfattal.quranx.core.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * This [AyatWithEdition] model is used by database to query a list of verses ([Aya]) with following [Edition].
 */
data class AyatWithEdition(
    @Embedded
    val edition: Edition,
    @Relation(
        parentColumn = "id",
        entityColumn = "ayaEdition",
        entity = Aya::class
    )
    val ayat: List<Aya>,
)
package com.abedfattal.quranx.core.framework.data.sources

import com.abedfattal.quranx.core.framework.data.repositories.localbased.LocalBasedEditionsRepository
import com.abedfattal.quranx.core.framework.data.repositories.localbased.LocalBasedLanguagesRepository
import com.abedfattal.quranx.core.framework.data.repositories.localbased.LocalBasedQuranRepository
import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.core.model.Language

/**
 * Use [LocalBasedDataSource] to performs local queries as first priority on [Aya], [Edition], and [Language], by means if the queries don't local result it'll call remote methods to get data etc...
 *
 * @property quranRepository to performs local queries as first priority on [Aya].
 * @property editionsRepository to performs local queries as first priority on [Edition].
 * @property languagesRepository to performs local queries as first priority on [Language].
 */
class LocalBasedDataSource internal constructor(
   val quranRepository: LocalBasedQuranRepository,
   val editionsRepository: LocalBasedEditionsRepository,
   val languagesRepository: LocalBasedLanguagesRepository,
)
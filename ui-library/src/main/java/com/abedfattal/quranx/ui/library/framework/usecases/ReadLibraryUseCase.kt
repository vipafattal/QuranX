package com.abedfattal.quranx.ui.library.framework.usecases

import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.AyatInfoWithTafseer
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.ui.library.ui.settings.LibraryPreferences
import com.abedfattal.ui.supported.edition.SupportedUiEditions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

object ReadLibraryUseCase {
    private val localEditionRepo = DataSources.localDataSource.editionsRepository
    private val localQuranRepo = DataSources.localDataSource.quranRepository

    fun getSurah(
        edition: Edition,
        surahNumber: Int,
    ): Flow<AyatInfoWithTafseer> = flow {

        val isRequestingQuranEdition = edition.identifier == SupportedUiEditions.EN_WORD_BY_WORD.identifier || SupportedUiEditions.ALL_EDITIONS_IDS.contains(edition.identifier)

        val quranEditionExists = getAnyDownloadQuranEdition()

        //Check if Quran edition is exists.
        val ayatWithTafseer =
            if (!isRequestingQuranEdition && LibraryPreferences.isDisplayAyaWithTafseer() && quranEditionExists != null) {

                val userSelectedQuranEdition = LibraryPreferences.getTranslationQuranEdition()

                val quranEdition =
                    userSelectedQuranEdition?.identifier ?: quranEditionExists.identifier

                getTranslationWithQuran(
                    edition.identifier,
                    quranEdition,
                    surahNumber
                )

            } else getSurahAyat(edition.identifier, surahNumber)

        val surah = localQuranRepo.getSurah(edition.identifier, surahNumber)
        emitAll(ayatWithTafseer.map { it.copy(surah) })
    }

    private suspend fun getAnyDownloadQuranEdition(): Edition? {
        val downloadEditions: List<Edition> = localEditionRepo.getDownloadedEditions(
            format = Edition.FORMAT_TEXT,
            type = Edition.TYPE_QURAN
        )
        return downloadEditions.firstOrNull()
    }

    private fun getTranslationWithQuran(
        tafseerEdition: String,
        quranEdition: String,
        surahNumber: Int
    ): Flow<AyatInfoWithTafseer> {
        return localQuranRepo.listenToSurahChanges(
            tafseerEdition = tafseerEdition,
            quranEdition = quranEdition,
            surahNumber = surahNumber,
        )
    }

    private fun getSurahAyat(editionId: String, surahNumber: Int): Flow<AyatInfoWithTafseer> {
        return localQuranRepo.listenToSurahChanges(
            edition = editionId,
            surahNumber = surahNumber,
        )
    }
}
package com.abedfattal.quranx.ui.library.framework.usecases

import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.AyatInfoWithTafseer
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.ui.library.ui.settings.LibraryPreferences
import com.abedfattal.quranx.ui.library.utils.QURAN_SUPPORTED_EDITIONS_IDS
import kotlinx.coroutines.flow.*

object ReadLibraryUseCase {
    private val localEditionRepo = DataSources.localDataSource.editionsRepository
    private val localQuranRepo = DataSources.localDataSource.quranRepository

    fun getSurah(
        edition: String,
        surahNumber: Int,
    ): Flow<AyatInfoWithTafseer> = flow {

        val isRequestingQuranEdition = QURAN_SUPPORTED_EDITIONS_IDS.contains(edition)

        val quranEditionExists = getAnyDownloadQuranEdition()

        //Check if Quran edition is exists.
        val ayatWithTafseer =
            if (!isRequestingQuranEdition && LibraryPreferences.isDisplayAyaWithTafseer() && quranEditionExists != null) {

                val userSelectedQuranEdition = LibraryPreferences.getTranslationQuranEdition()

                val quranEdition =
                    userSelectedQuranEdition?.id ?: quranEditionExists.id

                getTranslationWithQuran(
                    edition,
                    quranEdition,
                    surahNumber
                )

            } else getSurahAyat(edition, surahNumber)

        val surah = localQuranRepo.getSurah(edition, surahNumber)
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
package com.abedfattal.quranx.ui.library.framework.usecases

import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.core.model.ProcessState
import com.abedfattal.quranx.ui.library.ReadLibrary
import com.abedfattal.ui.supported.edition.SupportedUiEditions
import kotlinx.coroutines.flow.flow

object ManageLibraryUseCase {
    private val editionsRepo =
        DataSources.localBasedDataSource.editionsRepository.getEditionsByFormat(
            Edition.FORMAT_TEXT,
            true
        )

    fun getTextEdition() = flow<List<Edition>> {
        editionsRepo.collect { remoteEditionsProcess ->
            if (remoteEditionsProcess is ProcessState.Success) {
                val manageBlacklistedBook =
                    ReadLibrary.libraryConfig.editions.manageBlacklistedBook

                val remoteEditions = remoteEditionsProcess.data!!.filter { edition ->

                    val isNotBlacklistedBook = !manageBlacklistedBook.contains(edition.identifier)
                    val isTafseerOrTranslation =
                        edition.language != "ar" || edition.type == Edition.TYPE_TAFSEER
                    val isSupportedQuranBook =
                        edition.type == Edition.TYPE_QURAN && SupportedUiEditions.ALL_EDITIONS_IDS.contains(
                            edition.identifier
                        )
                    isNotBlacklistedBook && (isTafseerOrTranslation || isSupportedQuranBook)
                }.toMutableList()

                remoteEditions.apply { add(SupportedUiEditions.EN_WORD_BY_WORD) }.sortBy { it.language }

                emit(remoteEditions)
            }
        }
    }
}
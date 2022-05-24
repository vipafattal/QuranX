package com.abedfattal.quranx.ui.library.ui.manage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.ui.library.ReadLibrary
import com.abedfattal.quranx.ui.library.framework.download.EditionDownloadJob
import com.abedfattal.quranx.ui.library.framework.download.EditionsDownloadService
import com.abedfattal.quranx.ui.library.framework.usecases.ManageLibraryUseCase
import com.abedfattal.quranx.ui.library.models.EditionDownloadState
import com.abedfattal.quranx.ui.library.ui.settings.LibraryPreferences
import com.abedfattal.quranx.ui.library.utils.EditionShortcut
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ManageLibraryViewModel : ViewModel() {

    private val allAvailableEditions: MutableLiveData<List<Pair<EditionDownloadState, EditionDownloadJob?>>> =
        MutableLiveData(
        )

    private val localBasedQuranDataRepo = DataSources.localBasedDataSource.quranRepository
    private val localEditionsRepo = DataSources.localDataSource.editionsRepository

    private var remoteEditions: List<Edition> = listOf()
    private var downloadedEditions: List<Edition> = listOf()
    private var downloadInProgressEditions: List<EditionDownloadJob> = listOf()

    private fun prepareData() {
        viewModelScope.launch(Dispatchers.IO) {
            ManageLibraryUseCase.getTextEdition().collect {
                remoteEditions = it
                dataHasChange()
            }

        }
        viewModelScope.launch(Dispatchers.IO) {
            EditionsDownloadService.downloadingProcess.collect {
                downloadInProgressEditions = it
                dataHasChange()
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            localEditionsRepo.listenDownloadedEditions().collect {
                downloadedEditions = it
                dataHasChange()
            }
        }

    }

    private fun dataHasChange() {
        if (remoteEditions.isNotEmpty())
            allAvailableEditions.postValue(remoteEditions
                .map { edition ->

                    val editionDownloadState = EditionDownloadState(
                        edition,
                        downloadedEditions.any { it.identifier == edition.identifier })

                    val downloadProgressJob =
                        downloadInProgressEditions.firstOrNull { it.edition.identifier == edition.identifier }

                    return@map (editionDownloadState to downloadProgressJob)

                }
            )
    }


    fun getEditions(): MutableLiveData<List<Pair<EditionDownloadState, EditionDownloadJob?>>> {
        prepareData()
        return allAvailableEditions
    }

    fun deleteMushaf(edition: Edition) {
        EditionShortcut(edition, ReadLibrary.app).disableShortcut()
        if (edition.identifier == LibraryPreferences.getTranslationQuranEdition()?.identifier)
            changeTranslationQuranOnDelete()

        viewModelScope.launch(Dispatchers.IO) {
            localBasedQuranDataRepo.deleteQuranBook(edition.identifier, true)
        }

    }

    private fun changeTranslationQuranOnDelete() {
        viewModelScope.launch(Dispatchers.IO) {
            val anyDownloadedQuran =
                localEditionsRepo.getDownloadedEditions(type = Edition.TYPE_QURAN).firstOrNull()

            LibraryPreferences.setTranslationQuranEdition(anyDownloadedQuran)
        }
    }

}



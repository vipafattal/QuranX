package com.abedfattal.quranx.ui.library.ui.manage

import androidx.lifecycle.*
import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.DownloadingProcess
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.core.model.ProcessState
import com.abedfattal.quranx.ui.common.models.Process
import com.abedfattal.quranx.ui.library.models.EditionDownloadState
import com.abedfattal.quranx.ui.library.ui.settings.LibraryPreferences
import com.abedfattal.quranx.ui.library.utils.QURAN_SUPPORTED_EDITIONS_IDS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class ManageLibraryViewModel : ViewModel() {

    private lateinit var allAvailableEditions: MutableLiveData<List<EditionDownloadState>>

    private val localBasedQuranDataRepo = DataSources.localBasedDataSource.quranRepository
    private val remoteEditionsRepo = DataSources.remoteDataSource.editionsRepository
    private val localEditionsRepo = DataSources.localDataSource.editionsRepository

    private fun prepareData() {
        if (!::allAvailableEditions.isInitialized)
            allAvailableEditions = MutableLiveData()
        if (allAvailableEditions.value == null || allAvailableEditions.value!!.isNotEmpty())
            viewModelScope.launch(Dispatchers.IO) {
                remoteEditionsRepo.getTextEditions().collect { remoteEditionsProcess ->
                    if (remoteEditionsProcess is ProcessState.Success) {
                        val remoteEditions = remoteEditionsProcess.data!!.filter {
                            it.language != "ar" || it.type == Edition.TYPE_TAFSEER || it.type == Edition.TYPE_QURAN && QURAN_SUPPORTED_EDITIONS_IDS.contains(
                                it.id
                            )
                        }
                        localEditionsRepo.listenDownloadedEditions().collect { downloadedEditions ->

                            allAvailableEditions.postValue(remoteEditions.asSequence()
                                .map { edition ->
                                    EditionDownloadState(
                                        edition,
                                        downloadedEditions.any { it.id == edition.id })
                                }.sortedBy { it.edition.language }.toList()
                            )
                        }
                    }
                }
            }
    }

    fun getEditions(): LiveData<List<EditionDownloadState>> {
        prepareData()
        return allAvailableEditions
    }

    fun downloadMushaf(edition: Edition): LiveData<DownloadingProcess<Unit>> {
        return localBasedQuranDataRepo.downloadQuranBook(edition.id).onEach { downloadingProcess ->
            if (downloadingProcess is DownloadingProcess.Success &&
                edition.type == Edition.TYPE_QURAN && LibraryPreferences.getTranslationQuranEdition() == null
            )
                LibraryPreferences.setTranslationQuranEdition(edition)
        }.asLiveData(Dispatchers.IO)
    }

    fun deleteMushaf(edition: Edition): LiveData<Int> {
        val process = MutableLiveData<Int>()

        if (edition.id == LibraryPreferences.getTranslationQuranEdition()?.id)
            changeTranslationQuranOnDelete()

        viewModelScope.launch {
            localBasedQuranDataRepo.deleteQuranBook(edition.id, false)
            delay(500)
            process.postValue(Process.SUCCESS)
        }

        return process
    }

    private fun changeTranslationQuranOnDelete() {
        viewModelScope.launch(Dispatchers.IO) {
            val anyDownloadedQuran = localEditionsRepo.getDownloadedEditions()
                .firstOrNull { it.type == Edition.TYPE_QURAN }

            LibraryPreferences.setTranslationQuranEdition(anyDownloadedQuran)
        }
    }

    fun cancelWork() {
        viewModelScope.coroutineContext.cancelChildren()
    }
}



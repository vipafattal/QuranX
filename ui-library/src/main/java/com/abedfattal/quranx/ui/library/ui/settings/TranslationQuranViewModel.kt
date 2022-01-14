package com.abedfattal.quranx.ui.library.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.ui.library.models.EditionDownloadState
import com.abedfattal.quranx.ui.library.utils.SUPPORTED_QURAN_EDITIONS
import kotlinx.coroutines.Dispatchers

class TranslationQuranViewModel : ViewModel() {

    fun getDownloadedQuranEdition(): LiveData<List<EditionDownloadState>> {
        return liveData<List<EditionDownloadState>>(Dispatchers.IO) {

            val downloadEdition =
                DataSources.localDataSource.editionsRepository.getDownloadedEditions()

            emit(
                SUPPORTED_QURAN_EDITIONS.map { supportedEdition ->
                    EditionDownloadState(
                        edition = supportedEdition,
                        isDownloaded = downloadEdition.firstOrNull { it.id == supportedEdition.id } != null)
                }
            )
        }
    }
}
package com.abedfattal.quranx.ui.library.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.ui.library.models.EditionDownloadState
import com.abedfattal.ui.supported.edition.SupportedUiEditions
import kotlinx.coroutines.Dispatchers

class TranslationQuranViewModel : ViewModel() {

    fun getDownloadedQuranEdition(): LiveData<List<EditionDownloadState>> {
        return liveData<List<EditionDownloadState>>(Dispatchers.IO) {

            val downloadEdition =
                DataSources.localDataSource.editionsRepository.getDownloadedEditions()

            emit(
                SupportedUiEditions.ALL_EDITIONS.map { supportedEdition ->
                    EditionDownloadState(
                        edition = supportedEdition,
                        isDownloaded = downloadEdition.firstOrNull { it.identifier == supportedEdition.identifier } != null)
                }
            )
        }
    }
}
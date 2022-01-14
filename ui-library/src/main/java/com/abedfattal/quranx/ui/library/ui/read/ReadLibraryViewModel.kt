package com.abedfattal.quranx.ui.library.ui.read

import androidx.lifecycle.*
import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.AyatInfoWithTafseer
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.core.model.Surah
import com.abedfattal.quranx.ui.library.framework.usecases.ReadLibraryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ReadLibraryViewModel : ViewModel() {

    private val localQuranRepo = DataSources.localDataSource.quranRepository
    private val localEditionRepo = DataSources.localDataSource.editionsRepository

    private lateinit var _downloadEdition: LiveData<List<Edition>>

    fun getSurahs(editionId: String): LiveData<List<Surah>> {
        val surahs = MutableLiveData<List<Surah>>()
        viewModelScope.launch(Dispatchers.IO) {
            surahs.postValue(localQuranRepo.getSurahsByEdition(editionId))
        }
        return surahs
    }

    fun getEditionAyat(edition: String, surahNumber: Int): LiveData<AyatInfoWithTafseer> {
        return ReadLibraryUseCase.getSurah(edition, surahNumber).asLiveData(Dispatchers.IO)
    }

    fun listDownloadedEdition(): LiveData<List<Edition>> {
        if (!::_downloadEdition.isInitialized)
            _downloadEdition =
                localEditionRepo.listenDownloadedEditions().asLiveData(Dispatchers.IO)

        return _downloadEdition
    }
}



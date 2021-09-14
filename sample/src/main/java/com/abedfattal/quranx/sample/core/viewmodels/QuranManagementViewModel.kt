package com.abedfattal.quranx.sample.core.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.ProcessState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuranManagementViewModel : ViewModel() {

    private val localDataSource = DataSources.localBasedDataSource
    private val repository = localDataSource.localBasedQuranRepository

    fun downloadQuran(editionId: String): LiveData<ProcessState<Unit>> {
        return repository.downloadQuranBook(editionId).asLiveData(Dispatchers.IO)
    }

    fun deleteQuranEdition(editionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteQuranBook(editionId,removeWithEditionInfo = false)
        }
    }
}
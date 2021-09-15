package com.abedfattal.quranx.sample.core.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.core.model.ProcessState
import kotlinx.coroutines.Dispatchers

class EditionViewModel : ViewModel() {

    private val dataSource = DataSources.localBasedDataSource
    private val repository = dataSource.editionsRepository

    fun getEditionsByType(language: String, type: String): LiveData<ProcessState<List<Edition>>> {
        return repository.getEditions(format = Edition.FORMAT_TEXT, language, type)
            .asLiveData(Dispatchers.IO)
    }

}
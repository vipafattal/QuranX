package com.abedfattal.quranx.sample.core.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.core.model.Language
import com.abedfattal.quranx.core.model.ProcessState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LanguageViewModel : ViewModel() {

    private val localDataSource = DataSources.localBasedDataSource
    private val repository = localDataSource.localBasedLanguagesRepository

    fun getSupportedLanguages(): LiveData<ProcessState<List<Language>>> {
        return repository.getSupportLanguages().asLiveData(Dispatchers.IO)
    }
}
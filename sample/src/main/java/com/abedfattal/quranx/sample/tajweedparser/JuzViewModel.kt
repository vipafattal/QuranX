package com.abedfattal.quranx.sample.tajweedparser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.core.model.ProcessState
import com.abedfattal.quranx.core.utils.onSuccess
import kotlinx.coroutines.Dispatchers

class JuzViewModel : ViewModel() {

    private val localDataSource = DataSources.localBasedDataSource
    private val repository = localDataSource.localBasedQuranRepository

    fun getJuzVerses(juzNumber:Int, editionId: String): LiveData<List<Aya>?> {
        return repository.getJuz(juzNumber,editionId).onSuccess.asLiveData(Dispatchers.IO)
    }

}
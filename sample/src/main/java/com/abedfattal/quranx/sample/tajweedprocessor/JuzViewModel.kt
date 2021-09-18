package com.abedfattal.quranx.sample.tajweedprocessor

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.core.utils.onSuccess
import kotlinx.coroutines.Dispatchers

class JuzViewModel : ViewModel() {

    private val dataSource = DataSources.localBasedDataSource
    private val repository = dataSource.quranRepository

    fun getJuzVerses(juzNumber:Int, editionId: String): LiveData<List<Aya>?> {
        return repository.getJuz(juzNumber,editionId).onSuccess.asLiveData(Dispatchers.IO)
    }

}
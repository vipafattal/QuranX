package com.abedfattal.quranx.core.framework.data.repositories.localbased

import com.abedfattal.quranx.core.framework.data.repositories.remote.IRemoteLanguagesRepository
import com.abedfattal.quranx.core.model.Language
import com.abedfattal.quranx.core.model.ProcessState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLanguageRemoteRepository : IRemoteLanguagesRepository {

    var response: ProcessState<List<Language>>? = null

    override fun getSupportedLanguage(): Flow<ProcessState<List<Language>>> = flow {
        emit(response!!)
    }
}
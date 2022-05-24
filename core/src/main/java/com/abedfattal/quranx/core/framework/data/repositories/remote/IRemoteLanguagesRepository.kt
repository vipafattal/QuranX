package com.abedfattal.quranx.core.framework.data.repositories.remote

import com.abedfattal.quranx.core.model.Language
import com.abedfattal.quranx.core.model.ProcessState
import kotlinx.coroutines.flow.Flow

interface IRemoteLanguagesRepository {
    /**
     * @return the whole supported [Language] by the API service
     */
    fun getSupportedLanguage(): Flow<ProcessState<List<Language>>>
}
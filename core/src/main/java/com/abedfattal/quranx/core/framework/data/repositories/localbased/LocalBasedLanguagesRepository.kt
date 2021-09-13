package com.abedfattal.quranx.core.framework.data.repositories.localbased

import com.abedfattal.quranx.core.framework.data.repositories.local.LocalLanguagesRepository
import com.abedfattal.quranx.core.framework.data.repositories.remote.RemoteLanguagesRepository
import com.abedfattal.quranx.core.framework.data.repositories.remote.RemoteQuranRepository
import com.abedfattal.quranx.core.model.Language
import com.abedfattal.quranx.core.model.ProcessState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

/**
 * Use [LocalBasedLanguagesRepository] to performs local queries as first priority on [Language], by means if the queries don't local result it'll call remote methods to get data etc...
 */
class LocalBasedLanguagesRepository internal constructor(
    private val remoteRepository: RemoteLanguagesRepository,
    private val languagesRepository: LocalLanguagesRepository,
) {

    /**
     * List all the supported languages in the database.
     * If only the process state of [RemoteQuranRepository.getQuranBook] is [ProcessState.Success] the Quran book will be saved in the local database.
     *
     * @return [Language] list of the all saved languages in database. If no languages in the database,
     * it will call the remote API service to provide corresponds supported [Language].
     */
    fun getSupportLanguages(): Flow<ProcessState<List<Language>>> = flow {
        val languages = languagesRepository.getAllSupportedLanguage()
        if (languages.isNotEmpty())
            emit(ProcessState.Success(languages))
        else
            emitAll(remoteRepository.getSupportedLanguage())
    }
}
package com.abedfattal.quranx.core.framework.data.repositories.localbased

import com.abedfattal.quranx.core.framework.data.repositories.local.LocalLanguagesRepository
import com.abedfattal.quranx.core.framework.data.repositories.remote.RemoteLanguagesRepository
import com.abedfattal.quranx.core.framework.data.repositories.remote.RemoteQuranRepository
import com.abedfattal.quranx.core.model.Language
import com.abedfattal.quranx.core.model.ProcessState
import com.abedfattal.quranx.core.utils.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

/**
 * Use [LocalBasedLanguagesRepository] to performs local queries as first priority on [Language], by means if the queries don't local result it'll call remote methods to get data etc...
 */
class LocalBasedLanguagesRepository internal constructor(
    private val local: LocalLanguagesRepository,
    private val remote: RemoteLanguagesRepository,
) :LocalBased(){

    /**
     * List all the supported languages in the database.
     * If only the process state of [RemoteQuranRepository.getQuranBook] is [ProcessState.Success] the Quran book will be saved in the local database.
     *
     * @return [Language] list of the all saved languages in database. If no languages in the database,
     * it will call the remote API service to provide corresponds supported [Language].
     */
    fun getSupportLanguages(): Flow<ProcessState<List<Language>>>  {
        return caller(
            local = { local.getAllSupportedLanguage() },
            remote = { remote.getSupportedLanguage() },
            onRemoteSuccess = { local.addLanguages(it) },
        )
    }
}
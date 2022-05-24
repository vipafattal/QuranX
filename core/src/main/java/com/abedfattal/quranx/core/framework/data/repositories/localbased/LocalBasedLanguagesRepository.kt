package com.abedfattal.quranx.core.framework.data.repositories.localbased

import com.abedfattal.quranx.core.framework.data.repositories.local.ILocalLanguagesRepository
import com.abedfattal.quranx.core.framework.data.repositories.remote.IRemoteLanguagesRepository
import com.abedfattal.quranx.core.framework.data.repositories.remote.RemoteQuranRepository
import com.abedfattal.quranx.core.model.Language
import com.abedfattal.quranx.core.model.ProcessState
import kotlinx.coroutines.flow.Flow

/**
 * Use [LocalBasedLanguagesRepository] to performs local queries as first priority on [Language], by means if the queries don't local result it'll call remote methods to get data etc...
 */
class LocalBasedLanguagesRepository internal constructor(
    private val local: ILocalLanguagesRepository,
    private val remote: IRemoteLanguagesRepository,
) : LocalBased() {

    /**
     * List all the supported languages in the database.
     * If only the process state of [RemoteQuranRepository.getQuranBook] is [ProcessState.Success] the Quran book will be saved in the local database.
     *
     * @return [Language] list of the all saved languages in database. If no languages in the database,
     * it will call the remote API service to provide corresponds supported [Language].
     */
    fun getSupportLanguages(prioritizeRemote: Boolean = false): Flow<ProcessState<List<Language>>> {
        return caller(
            prioritizeRemote,
            local = { local.getAllSupportedLanguage() },
            remote = { remote.getSupportedLanguage() },
            onRemoteSuccess = { local.addLanguages(it) },
        )
    }
}
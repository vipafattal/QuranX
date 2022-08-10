package com.abedfattal.quranx.core.framework.data.repositories.remote

import com.abedfattal.quranx.core.framework.api.QURAN_CLOUD_BASE_URL
import com.abedfattal.quranx.core.framework.api.QuranCloudAPI
import com.abedfattal.quranx.core.model.Language
import com.abedfattal.quranx.core.model.ProcessState
import com.abedfattal.quranx.core.utils.newRequest
import com.abedfattal.quranx.core.utils.transform
import kotlinx.coroutines.flow.Flow

/**
 * An API [RemoteLanguagesRepository] depended repository, mainly to get supported languages by API.
 *
 * @property api used to access the API service [QURAN_CLOUD_BASE_URL] which is based on [retrofit2.Retrofit].
 * Note that each function you call you will get a result of [Flow], which wraps the response with [ProcessState].
 */
class RemoteLanguagesRepository internal constructor(
    private val api: QuranCloudAPI
) : IRemoteLanguagesRepository {
    /**
     * @return the whole supported [Language] by the API service
     */
    override fun getSupportedLanguage(): Flow<ProcessState<List<Language>>> {
        return newRequest { api.getSupportedLanguage() }
            .transform {
                it.languages.map { code -> Language(code) }
            }
    }
}




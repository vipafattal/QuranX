package com.abedfattal.quranx.core.framework.data.repositories.remote

import com.abedfattal.quranx.core.framework.api.QURAN_CLOUD_BASE_URL
import com.abedfattal.quranx.core.framework.api.QuranCloudAPI
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.core.model.ProcessState
import com.abedfattal.quranx.core.utils.newRequest
import com.abedfattal.quranx.core.utils.processTransform
import kotlinx.coroutines.flow.Flow


/**
 * An API [RemoteEditionsRepository] depended repository, that performs queries to get available Quran editions see [Edition].
 *
 * @property api used to access the API service [QURAN_CLOUD_BASE_URL] which is based on [retrofit2.Retrofit].
 * Note that each function you call you will get a result of [Flow], which wraps the response with [ProcessState].
 *
 */
class RemoteEditionsRepository internal constructor(
    private val api: QuranCloudAPI
) {
    /**
     * List all [Edition] that can be either [Edition.FORMAT_TEXT] or [Edition.FORMAT_AUDIO].
     * @param format represents the format kind of the [Edition] you want to list.
     *
     * @return [Edition] list only that confirms with [format].
     */
    fun getEditionByFormat(format: String): Flow<ProcessState<List<Edition>>> {
        return when (format) {
            Edition.FORMAT_TEXT -> getTextEditions()
            Edition.FORMAT_AUDIO -> getAudioEditions()
            else -> throw IllegalArgumentException("Unknown edition format:($format)")
        }
    }

    /**
     *
     * @return [Edition] list of all [Edition.FORMAT_TEXT].
     *
     */
    fun getTextEditions(): Flow<ProcessState<List<Edition>>> {
        return newRequest {
            api.getTextEditions()
        }.processTransform { it.editions }
    }

    /**
     *
     * @return [Edition] list of all [Edition.FORMAT_AUDIO].
     *
     */
    fun getAudioEditions(): Flow<ProcessState<List<Edition>>> {
        return newRequest {
            api.getAudioEditions()
        }.processTransform { it.editions }
    }

    /**
     * List all [Edition]'s type that can be used by [getEditionByType].
     */
    fun getAllEditionsType(): Flow<ProcessState<List<String>>> {
        return newRequest { api.getEditionsTypes() }.processTransform { it.editionsType }
    }

    /**
     * List all [Edition] by certain type.
     * @param type represents the type of the [Edition] you want to list.
     *
     * @return [Edition] list only that confirms with [type].
     */
    fun getEditionByType(type: String): Flow<ProcessState<List<Edition>>> {
        return newRequest {
            api.getEditionsByType(type)
        }.processTransform { it.editions }
    }

    /**
     * List all [Edition] by certain [format], [language], and [type].
     * @param format can be either [Edition.FORMAT_TEXT] or [Edition.FORMAT_AUDIO].
     * @param language represents the language code.
     *
     */
    fun getEditions(
        format: String,
        language: String,
        type: String
    ): Flow<ProcessState<List<Edition>>> {
        return newRequest {
            api.getEditions(format = format, language = language, type = type)
        }.processTransform { it.editions }
    }

}
package com.abedfattal.quranx.core.framework.data.repositories.localbased

import com.abedfattal.quranx.core.framework.data.repositories.local.LocalEditionsRepository
import com.abedfattal.quranx.core.framework.data.repositories.remote.RemoteEditionsRepository
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.core.model.ProcessState
import kotlinx.coroutines.flow.Flow


/**
 * Use [LocalBasedEditionsRepository] to performs local queries as first priority on [Edition], by means if the queries don't local result it'll call remote methods to get data etc...
 */
class LocalBasedEditionsRepository internal constructor(
    private val remote: RemoteEditionsRepository,
    private val local: LocalEditionsRepository,
) : LocalBased() {

    /**
     * List all [Edition] by certain [Edition.type] from the database if only exists,
     * otherwise will call the API service to provide the corresponds [Edition] list.
     *
     * @param type represents the edition's type to get from database which can be any of [Edition.getAllEditionsTypes].
     *
     * @return a flow of a [ProcessState] that contains the [Edition] list,
     * and the [ProcessState] actually represents the remote process state rather then local process state.
     */
    fun getEditionsByType(type: String): Flow<ProcessState<List<Edition>>> {
        return caller(
            local = { local.getEditionsByType(type) },
            remote = { remote.getEditionsByType(type) },
            onRemoteSuccess = { local.addAllEdition(it) },
        )
    }

    /**
     * List all [Edition] by certain [Edition.format] from the database if only exists,
     * otherwise will call the API service to provide the corresponds [Edition] list.
     *
     * @param format represents the edition format which can be either [Edition.FORMAT_AUDIO] or [Edition.FORMAT_TEXT].
     *
     * @return a flow of a [ProcessState] that contains the [Edition] list,
     * and the [ProcessState] actually represents the remote process state rather then local process state.
     */
    fun getEditionsByFormat(format: String): Flow<ProcessState<List<Edition>>> {
        return caller(
            local = { local.getEditionsByFormat(format) },
            remote = { remote.getEditionsByFormat(format) },
            onRemoteSuccess = { local.addAllEdition(it) },
        )
    }

    /**
     * List all [Edition] from the database if only exists,
     * otherwise will call the API service to provide the corresponds [Edition] list.
     *
     * @param format represents the edition format which can be either [Edition.FORMAT_AUDIO] or [Edition.FORMAT_TEXT].
     * @param languageCode represents the edition's language to get from the database.
     * @param type represents the edition's type to get from database which can be any of [Edition.getAllEditionsTypes].
     *
     * @return a flow of a [ProcessState] that contains the [Edition] list,
     * and the [ProcessState] actually represents the remote process state rather then local process state.
     */
    fun getEditions(
        format: String,
        languageCode: String,
        type: String
    ): Flow<ProcessState<List<Edition>>> {
        return caller(
            local = { local.getEditions(format, languageCode, type) },
            remote = { remote.getEditions(format, languageCode, type) },
            onRemoteSuccess = { local.addAllEdition(it) },
        )
    }


}
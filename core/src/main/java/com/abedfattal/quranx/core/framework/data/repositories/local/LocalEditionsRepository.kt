package com.abedfattal.quranx.core.framework.data.repositories.local

import com.abedfattal.quranx.core.framework.data.repositories.localbased.LocalBasedQuranRepository
import com.abedfattal.quranx.core.framework.db.daos.EditionsDao
import com.abedfattal.quranx.core.model.DownloadState
import com.abedfattal.quranx.core.model.Edition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * Use [LocalEditionsRepository] to save and apply local queries on [Edition] table.
 * To save [Edition] you should call the one of the methods: [addEdition] or [addAllEdition].
 * then you can performs local queries on Table, otherwise you'll get a nullable or empty list,
 * (obviously) since the requested [Edition] does not exist in the database.
 *
 * @property editionsDao represents the data access object for [Edition] table.
 */
class LocalEditionsRepository internal constructor(private val editionsDao: EditionsDao) {

    /**
     * Listen to all [Edition] table changes, like when new [Edition] is added or removed from the table.
     *
     * @return [Edition] list that is ordered ascending by [Edition.language].
     */
    fun listenToEditionChanges(): Flow<List<Edition>> {
        return editionsDao.listenToEditions().distinctUntilChanged()
    }


    /**
     * Listen to the [DownloadState] table to list [Edition] that only is [DownloadState.STATE_DOWNLOADED],
     * this is helpful when downloading a full Quran book using [LocalBasedQuranRepository.downloadQuranBook].
     *
     * @param format optional parameter to filter by the edition format which can be either [Edition.FORMAT_AUDIO] or [Edition.FORMAT_TEXT].
     * @param type optional parameter to filter by the edition type which can be any of [Edition.getAllEditionsTypes].
     *
     * @return [Edition] list that is downloaded and ordered ascending by [Edition.language].
     * When [format] and [type] both are not provided will return all downloaded editions.
     */
    fun listenDownloadedEditions(
        format: String? = null,
        type: String? = null,
    ): Flow<List<Edition>> {

        return if (format == null || type == null) {
            if (format == null && type == null)
                editionsDao.listenToDownloadedEditions().distinctUntilChanged()
            else if (format != null)
                editionsDao.listenToDownloadedEditionsByFormat(format).distinctUntilChanged()
            else
                editionsDao.listenToDownloadedEditionsByType(type!!).distinctUntilChanged()
        } else
            editionsDao.listenToDownloadedEditions(format, type).distinctUntilChanged()
    }

    /**
     * Get a list of [Edition] that only it's [DownloadState.state] is [DownloadState.STATE_DOWNLOADED],
     * this is helpful when downloading a full Quran book using [LocalBasedQuranRepository.downloadQuranBook].
     *
     * @param format optional parameter to filter by the edition format which can be either [Edition.FORMAT_AUDIO] or [Edition.FORMAT_TEXT].
     * @param type optional parameter to filter by the edition type which can be any of [Edition.getAllEditionsTypes].
     *
     * @return [Edition] list that is downloaded and ordered ascending by [Edition.language].
     * When [format] and [type] both are not provided will return all downloaded editions.
     */
    suspend fun getDownloadedEditions(format: String? = null, type: String? = null): List<Edition> {
        return if (format == null || type == null) {
            if (format == null && type == null)
                editionsDao.getDownloadedEditions()
            else if (format != null)
                editionsDao.getDownloadedEditionsByFormat(format)
            else
                editionsDao.getDownloadedEditionsType(type!!)
        } else
            editionsDao.getDownloadedEditions(format, type)
    }


    /**
     * Get specific [Edition] by id. To list all [Edition] by ids see [getAllEditions].
     *
     * @param editionId which is the corresponds for [Edition.id].
     *
     * @return [Edition] that if only exists in database.
     */
    suspend fun getEdition(editionId: String): Edition? {
        return editionsDao.getEdition(editionId)
    }

    /**
     * List all [Edition] by ids. To get a specific [Edition] see [getEdition].
     *
     * @param editionIds represent for all the [Edition.id] to list from database.
     *
     * @return [Edition] list that is ordered ascending by [Edition.language].
     */
    suspend fun getAllEditions(vararg editionIds: String): List<Edition> {
        return editionsDao.getAllEditions(editionIds = editionIds)
    }

    /**
     * List all [Edition].
     *
     * @param format represents the edition format which can be either [Edition.FORMAT_AUDIO] or [Edition.FORMAT_TEXT].
     * @param languageCode represents the edition's language to get from the database.
     * @param type represents the edition's type to get from database which can be any of [Edition.getAllEditionsTypes].
     *
     * @return [Edition] list that is ordered ascending by [Edition.language].
     */
    suspend fun getEditions(format: String, languageCode: String, type: String): List<Edition> {
        return editionsDao.getEditions(format, languageCode, type)
    }

    /**
     * List all [Edition] by certain [Edition.type].
     *
     * @param type represents the edition's type to get from database which can be any of [Edition.getAllEditionsTypes].
     *
     * @return [Edition] list that is ordered ascending by [Edition.language].
     */
    suspend fun getEditionsByType(type: String): List<Edition> {
        return editionsDao.getEditionsByType(type)
    }

    /**
     * List all [Edition] by certain [Edition.format].
     *
     * @param format represents the edition format which can be either [Edition.FORMAT_AUDIO] or [Edition.FORMAT_TEXT].
     *
     * @return [Edition] list that is ordered ascending by [Edition.language].
     */
    suspend fun getEditionsByFormat(format: String): List<Edition> {
        return editionsDao.getEditionsByFormat(format)
    }

    /**
     * Add a list of [Edition] to the database table. To add a single edition see [Edition].
     */
    suspend fun addAllEdition(editions: List<Edition>) {
        return editionsDao.addAllEdition(editions)
    }

    /**
     * Add a single [Edition] to the database table. To add a list of editions see [addAllEdition].
     */
    suspend fun addEdition(edition: Edition) {
        return editionsDao.addEdition(edition)
    }

    /**
     * Removes a single [Edition] from the database table. To remove all editions see [deleteAllEditions].
     */
    suspend fun deleteEdition(editionId: String) {
        editionsDao.deleteEdition(editionId)
    }

    /**
     * Removes all [Edition] from the database table. To remove a single edition see [Edition].
     */
    suspend fun deleteAllEditions() {
        editionsDao.deleteAllEditions()
    }
}
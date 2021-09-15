package com.abedfattal.quranx.core.framework.data.repositories.localbased

import com.abedfattal.quranx.core.framework.data.repositories.local.LocalEditionsRepository
import com.abedfattal.quranx.core.framework.data.repositories.local.LocalQuranRepository
import com.abedfattal.quranx.core.framework.data.repositories.remote.RemoteQuranRepository
import com.abedfattal.quranx.core.model.Surah
import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.core.model.ProcessState
import com.abedfattal.quranx.core.utils.processTransform
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

/**
 * Use [LocalBasedQuranRepository] to performs local queries as first priority on [Aya], by means if the queries don't local result it'll call remote methods to get data etc...
 * however the only [downloadQuranBook] method, apparently, calls the remote API service first, then call to the local service to save Quran book into the database
 */
class LocalBasedQuranRepository internal constructor(
    private val remoteRepository: RemoteQuranRepository,
    private val quranLocalRepository: LocalQuranRepository,
    private val editionsLocalRepository: LocalEditionsRepository,
) {

    /**
     * Use [downloadQuranBook] to download the whole Quern book edition,
     * by means download all verses ([Aya]) and surahs ([Surah]) in one request, then save data into the database.
     *
     * @sample com.abedfattal.quranx.sample.core.viewmodels.QuranManagementViewModel
     *
     * If only the process state of [RemoteQuranRepository.getQuranBook] is [ProcessState.Success] the Quran book will be saved in the local database.
     *
     * @param id represents the edition id of the Quran book to download.
     *
     * @return a flow of a [ProcessState] that actually represents the remote process state rather then local process state.
     */
    fun downloadQuranBook(id: String): Flow<ProcessState<Unit>> =
        remoteRepository.getQuranBook(id).onEach { process ->
            if (process is ProcessState.Success && process.data != null) {
                val quran = process.data
                quranLocalRepository.addQuranBook(quran)
                editionsLocalRepository.addEdition(quran.edition)
            }
        }.processTransform { }

    /**
     * Use [getAya] to get a verse from the database if only exists, otherwise will call the API service to provide the corresponds [Aya].
     *
     * @param numberInMushaf represents the [Aya.number] in Quran.
     * @param editionId represents the edition id of the Quran book.
     *
     * @return a flow of a [ProcessState] that contains the [Aya],
     * and the [ProcessState] actually represents the remote process state rather then local process state.
     */
    fun getAya(numberInMushaf: Int, editionId: String): Flow<ProcessState<Aya>> = flow {
        val aya = quranLocalRepository.getAya(numberInMushaf, editionId)
        if (aya != null)
            emit(ProcessState.Success(aya))
        else
            emitAll(remoteRepository.getAya(numberInMushaf, editionId))
    }

    /**
     * Use [getPage] to get all verses in single Quran page from the database if only exists, otherwise will call the API service to provide the corresponds [Aya] list.
     *
     * @param number represents the [Aya.page] in Quran.
     * @param editionId represents the edition id of the Quran book.
     *
     * @return a flow of a [ProcessState] that contains the [Aya] list of the page,
     * and the [ProcessState] actually represents the remote process state rather then local process state.
     */
    fun getPage(number: Int, editionId: String): Flow<ProcessState<List<Aya>>> = flow {
        val pageAyat = quranLocalRepository.getPage(number, editionId)
        if (pageAyat.isNotEmpty())
            emit(ProcessState.Success(pageAyat.map { it.aya }))
        else
            emitAll(remoteRepository.getPage(number, editionId))
    }

    /**
     * Use [getJuz] to get all verses in single Quran juz from the database if only exists, otherwise will call the API service to provide the corresponds [Aya] list.
     *
     *  @sample com.abedfattal.quranx.sample.tajweedparser.JuzViewModel
     *
     * @param number represents the [Aya.juz] in Quran.
     * @param editionId represents the edition id of the Quran book.
     *
     * @return a flow of a [ProcessState] that contains the [Aya] list of the juz,
     * and the [ProcessState] actually represents the remote process state rather then local process state.
     */
    fun getJuz(number: Int, editionId: String): Flow<ProcessState<List<Aya>>> = flow {

        val juzAyat = quranLocalRepository.getJuz(number, editionId)
        if (juzAyat.isNotEmpty())
            emit(ProcessState.Success(juzAyat.map { it.aya }))
        else
            emitAll(remoteRepository.getPage(number, editionId))
    }

    /**
     * Removes a all Quran verses ([Aya]) and ([Surah]) for the following Quran edition.
     *
     * @param editionId represents the edition id of the Quran book to delete.
     * @param removeWithEditionInfo determines whither to remove the [Edition] also.
     */
    suspend fun deleteQuranBook(editionId: String, removeWithEditionInfo: Boolean) {
        quranLocalRepository.deleteQuranBook(editionId)
        if (removeWithEditionInfo)
            editionsLocalRepository.deleteEdition(editionId)
    }

}
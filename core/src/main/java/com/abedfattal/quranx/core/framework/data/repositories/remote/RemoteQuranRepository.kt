package com.abedfattal.quranx.core.framework.data.repositories.remote

import com.abedfattal.quranx.core.framework.api.QURAN_CLOUD_BASE_URL
import com.abedfattal.quranx.core.framework.api.QuranCloudAPI
import com.abedfattal.quranx.core.framework.api.models.Quran
import com.abedfattal.quranx.core.framework.data.repositories.localbased.LocalBasedQuranRepository
import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.core.model.AyatWithEdition
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.core.model.ProcessState
import com.abedfattal.quranx.core.utils.newRequest
import com.abedfattal.quranx.core.utils.processTransform
import kotlinx.coroutines.flow.Flow


/**
 * An API [RemoteQuranRepository] depended repository, that performs queries to get verses Quran see [Aya], [AyatWithEdition].
 *
 * @property api used to access the API service [QURAN_CLOUD_BASE_URL] which is based on [retrofit2.Retrofit].
 * Note that each function you call you will get a result of [Flow], which wraps the response with [ProcessState].
 *
 */
class RemoteQuranRepository internal constructor(
    private val api: QuranCloudAPI
) {

    /**
     * Use [getQuranBook] to list all Quran verses in a single remote request.
     *
     * @see [LocalBasedQuranRepository.downloadQuranBook]
     *
     * @param editionId represents the Quran [Edition.identifier].
     *
     * @return the whole book Quran book by [editionId].
     */
    fun getQuranBook(
        editionId: String,
    ): Flow<ProcessState<Quran.QuranData>> {
        return newRequest { api.getQuranBook(editionId) }.processTransform { it.quran }
    }


    /**
     * Use [getAya] to get single verse.
     *
     * @sample com.abedfattal.quranx.sample.wordsprocessor.VerseViewModel

     *
     * @param numberInMushaf represents the verse number in Quran.
     * @param editionId represents the Quran [Edition.identifier].
     *
     * @return [Aya] a single verse.
     */
    fun getAya(numberInMushaf: Int, editionId: String): Flow<ProcessState<Aya>> {
        return newRequest { api.getAya(numberInMushaf, editionId) }.processTransform { it.aya.copy(ayaEdition = editionId) }
    }


    /**
     * Use [getPage] to all verses in single page.
     *
     * @param number represents the page number in Quran.
     * @param editionId represents the Quran [Edition.identifier].
     *
     * @return [Aya] list for the whole Quran page.
     */
    fun getPage(number: Int, editionId: String): Flow<ProcessState<List<Aya>>> {
        return newRequest { api.getPage(number, editionId) }.processTransform {data->
            data.pageData.ayahs.map { it.copy(ayaEdition = editionId) }
        }
    }

    /**
     * Use [getJuz] to list all verses in single juz.
     *
     * @param number represents the juz number in Quran.
     * @param editionId represents the Quran [Edition.identifier].
     *
     * @return [Aya] list for the whole Quran juz.
     */
    fun getJuz(number: Int, editionId: String): Flow<ProcessState<List<Aya>>> {
        return newRequest { api.getJuz(number, editionId) }.processTransform {data->
            data.juzData.ayahs.map { it.copy(ayaEdition = editionId) }
        }
    }

    /**
     * Search in a specific Quran text edition.
     *
     * @param query represents the words that you want search for.
     * @param editionId represents the Quran [Edition.identifier] that you want to search in.
     *
     * @return [AyatWithEdition] list result that match with [query].
     */
    fun searchQuranByEdition(
        query: String,
        editionId: String
    ): Flow<ProcessState<List<AyatWithEdition>>> {
        return newRequest {
            api.searchQuranByEdition(
                query,
                editionId
            )
        }.processTransform {
            it.data.toQuranWithEdition()
        }
    }

    /**
     * Search the whole Quran text in specific language.
     *
     * @param query represents the words that you want search for.
     * @param languageCode represents the Quran [Edition.language] that you want to search in.
     *
     * @return [AyatWithEdition] list result that match with [query].
     */
    fun searchAllQuran(
        query: String,
        languageCode: String
    ): Flow<ProcessState<List<AyatWithEdition>>> {
        return newRequest {
            api.searchAllQuran(
                query,
                languageCode
            )
        }.processTransform {
            it.data.toQuranWithEdition()
        }
    }
}
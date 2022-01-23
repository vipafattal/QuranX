package com.abedfattal.quranx.core.framework.data.repositories.local

import com.abedfattal.quranx.core.framework.api.models.Quran
import com.abedfattal.quranx.core.framework.data.repositories.localbased.LocalBasedQuranRepository
import com.abedfattal.quranx.core.framework.data.repositories.remote.RemoteQuranRepository
import com.abedfattal.quranx.core.framework.db.daos.QuranDao
import com.abedfattal.quranx.core.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.transform


/**
 * Use [LocalQuranRepository] to save and apply local queries on [Aya] table .
 * To save [Aya] you should call the appropriate methods, such as [LocalBasedQuranRepository.downloadQuranBook], [addSurah], or [addAyat], etc...
 * then you can performs local queries on Table, otherwise you'll get a nullable or empty list,
 * (obviously) since the requested [Aya] does not exist in the database.
 *
 * @property quranDao represents the data access object for [Aya] table.
 */
class LocalQuranRepository internal constructor(
    private val quranDao: QuranDao
) {

    /**
     * Get all saved verses in Surah by [surahNumber] that confirms with specific [Edition.id].
     */
    suspend fun getAyatBySurah(editionId: String, surahNumber: Int): List<AyaWithInfo> {
        return quranDao.getSurahAyatByEdition(editionId, surahNumber)
    }

    /**
     * Get a specific saved Surah verses with it's verses tafseer (Arabic explanation) or translation.
     * If you want to get more editions for the same surah see [getSurahsEditions].
     *
     * @param tafseerEdition it's the tafseer or the translation edition id you want to query with corresponds Quran edition.
     * @param quranEdition the Quran edition id to query with tafseer edition.
     * @param surahNumber the Surah number in Quran to query.
     *
     * @return [tafseerEdition] verses ([AyatWithTafseer.tafseerList]).
     */
    suspend fun getSurahWithTafseer(
        tafseerEdition: String,
        quranEdition: String,
        surahNumber: Int
    ): AyatWithTafseer {
        require(quranEdition != tafseerEdition)

        val data: List<AyatWithEdition> = quranDao.getSurahAllEditions(
            surahNumber,
            editions = arrayOf(quranEdition, tafseerEdition)
        )

        return AyatWithTafseer(
            null,
            tafseerList = data.firstOrNull { it.edition.id == tafseerEdition },
            quranList = data.firstOrNull { it.edition.id == quranEdition },
        )
    }

    /**
     * List all saved verses with the corresponds editions.
     *
     * @param editions represents the edition ids to query verses.
     *
     * @return [AyatWithEdition] list for all requested edition ids ([editions]).
     */
    suspend fun getAyatAllEditions(vararg editions: String): List<AyatWithEdition> {
        return quranDao.getAyatAllEditions(editions = editions)
    }

    /**
     * List all saved verses with the corresponds single edition.
     *
     * @param edition represents the edition id to query juz.
     *
     * @return [AyaWithInfo] list that exist in specific edition.
     */
    suspend fun getAyatEditions(edition: String): List<AyaWithInfo> {
        return quranDao.getAyatEdition(edition)
    }

    /**
     * Get saved Juz with the corresponds editions.
     * If you want to an only one juz with specific edition see [getJuz].
     *
     * @param juz the Quran juz number.
     * @param editions represents the edition ids to query juz.
     *
     * @return [AyatWithEdition] list for all requested edition ids ([editions]).
     */
    suspend fun getJuzEditions(juz: Int, vararg editions: String): List<AyatWithEdition> {
        return quranDao.getJuzAllEditions(
            juz,
            editions = editions
        )
    }

    /**
     * Listen for bookmarks table changes, like when new [Bookmark] is added or removed from the table, see [updateBookmarkStatus].
     *
     * @return [AyaWithInfo] list contains only the bookmarked verses and ordered ascending by [Aya.number].
     */
    fun listenToSurahChanges(
        tafseerEdition: String,
        quranEdition: String,
        surahNumber: Int
    ): Flow<AyatInfoWithTafseer> {

        require(quranEdition != tafseerEdition)

        return quranDao.listenToSurahAyatByEdition(surahNumber, tafseerEdition, quranEdition).transform { ayatWithTafseer ->
            val dataSize = ayatWithTafseer.size
            val firstList = ayatWithTafseer.subList(0, dataSize / 2)
            var quranList: List<AyaWithInfo> = emptyList()
            var tafseerList: List<AyaWithInfo> = emptyList()
            if (firstList.getOrNull(0) != null) {
                if (firstList[0].aya.ayaEdition == quranEdition) {
                    quranList = ayatWithTafseer.subList(0, dataSize / 2)
                    tafseerList = ayatWithTafseer.subList(dataSize / 2, dataSize)
                } else {
                    tafseerList = ayatWithTafseer.subList(0, dataSize / 2)
                    quranList = ayatWithTafseer.subList(dataSize / 2, dataSize)
                }
            }
            emit(AyatInfoWithTafseer(null,tafseerList, quranList))
        }.distinctUntilChanged()
    }
    /**
     * Listen for bookmarks table changes, like when new [Bookmark] is added or removed from the table, see [updateBookmarkStatus].
     *
     * @return [AyaWithInfo] list contains only the bookmarked verses and ordered ascending by [Aya.number].
     */
    fun listenToSurahChanges(
        edition: String,
        surahNumber: Int
    ): Flow<AyatInfoWithTafseer> {

        return quranDao.listenToSurahAyatByEdition(surahNumber, edition).transform { ayatWithTafseer ->
            emit(AyatInfoWithTafseer(null,null, ayatWithTafseer))
        }.distinctUntilChanged()
    }

    /**
     * Get saved Surah with the corresponds editions.
     * If you want to an only one surah with specific edition see [getSurah].
     *
     * @param surahNumber the Surah number in Quran to query.
     * @param editions represents the edition ids to query surah.
     *
     * @return [AyatWithEdition] list for all requested edition ids ([editions]).
     */
    suspend fun getSurahsEditions(
        surahNumber: Int,
        vararg editions: String
    ): List<AyatWithEdition> {
        return quranDao.getSurahAllEditions(
            surahNumber,
            editions = editions
        )
    }


    /**
     * Get saved page with the corresponds editions.
     * If you want to an only one page with specific edition see [getPage].
     *
     * @param page represents the page number in Quran.
     * @param editions represents the edition ids to query pages.
     *
     * @return [AyatWithEdition] list for all requested edition ids ([editions]).
     */
    suspend fun getPageEditions(page: Int, vararg editions: String): List<AyatWithEdition> {
        return quranDao.getPageAllEditions(
            page,
            editions = editions
        )
    }

    /**
     * Get saved verse with the corresponds editions.
     * If you want to an only one verse with specific edition see [getAya].
     *
     * @param ayaNumberInMushaf represents the aya number in Quran.
     * @param editions represents the edition ids to query ayat.
     *
     * @return [AyatWithEdition] list for all requested edition ids ([editions]).
     */
    suspend fun getAyaEditions(
        ayaNumberInMushaf: Int,
        vararg editions: String
    ): List<AyatWithEdition> {
        return quranDao.getAyaAllEditions(
            ayaNumberInMushaf,
            editions = editions
        )
    }

    /**
     * Get all surahs.
     *
     * @param editionId represents the Surah edition.
     *
     * @return [Surah] list in that exist in specific edition.
     */
    suspend fun getSurahsByEdition(editionId: String): List<Surah> {
        return quranDao.getSurahsByEdition(editionId)
    }


    /**
     * Get specific juz verses with info such as bookmarkState such as [AyaWithInfo.bookmark].

     * @param juz represents the Quran juz number.
     * @param editionId represents the juz edition.
     *
     * @return [AyaWithInfo] list that exist in specific edition.
     */
    suspend fun getJuz(juz: Int, editionId: String): List<AyaWithInfo> {
        return quranDao.getAyatOfJuz(juz, editionId)
    }

    /**
     * Get specific surah.
     *
     * @param surahNumber represents the surah number in Quran.
     * @param editionId represents the surah edition.
     *
     * @return [Surah] single that exist in specific edition,
     * if no surah exist in database the query will return null.
     */
    suspend fun getSurah(editionId: String, surahNumber: Int): Surah? {
        return quranDao.getSurahByEdition(editionId, surahNumber)
    }

    /**
     * Get specific page verses with info such as bookmarkState such as [AyaWithInfo.bookmark].
     *
     * @param page represents the page number in Quran.
     * @param editionId represents the juz edition.
     *
     * @return [AyaWithInfo] list that exist in specific edition.
     */
    suspend fun getPage(page: Int, editionId: String): List<AyaWithInfo> {
        return quranDao.getAyatOfPage(page, editionId)
    }

    /**
     * Get specific verse.
     *
     * @param numberInMushaf represents the aya number in Quran. if you want to get [Aya] by [Aya.numberInSurah] see [getAyaByNumberInMushaf].
     * @param editionId represents the verse edition.
     *
     * @return [Aya] single that exist in specific edition,
     * if no verse exist in database the query will return null.
     */
    suspend fun getAya(numberInMushaf: Int, editionId: String): AyaWithInfo? {
        return quranDao.getAyaByEdition(numberInMushaf, editionId)
    }

    /**
     * Get specific verse.
     *
     * @param numberInMushaf represents the aya number in Quran. if you want to get [Aya] by [Aya.number] in Quran see [getAya].
     * @param editionId represents the verse edition.
     *
     * @return [AyaWithInfo] single that exist in specific edition,
     * if no verse exist in database the query will return null.
     */
    suspend fun getAyaByNumberInMushaf(numberInMushaf: Int, editionId: String): AyaWithInfo? {
        return quranDao.getAyaByNumberInQuran(numberInMushaf, editionId)

    }

    /**
     * Get the following verses with info that lays between range of verses number.
     *
     * @param range represents the verse number domain in Quran to look for verses,
     * where as [IntRange.start], [IntRange.last] respectively are the starting and ending verse number in Quran.
     * @param editionId represents the verse edition.
     *
     * @return [AyaWithInfo] list that exist in specific edition.
     */
    suspend fun getAyatByRange(range: IntRange, editionId: String): List<AyaWithInfo> {
        return quranDao.getAyatByNumberInMushafRange(range.first, range.last, editionId)
    }

    /**
     * Search the whole Quran saved editions for a specific query, If you want to look in specific edition see [searchQuranByEdition].
     *
     * @param query represents the query to look for.
     * @param languageCode represents the results language code.
     *
     * @return [AyaWithInfo] list that exist that matches with query.
     */
    suspend fun searchQuran(query: String, languageCode: String): List<AyatWithEdition> {
        return quranDao.searchAllQuran("%$query%", languageCode)
    }

    /**
     * Search the in specific Quran edition, for a specific query, If you want to look in all Quran editions see [searchQuran].
     *
     * @param query represents the query to look for.
     * @param editionId represents the Quran edition that you want to search in.

     * @return [AyaWithInfo] list that exist that matches with query.
     */
    suspend fun searchQuranByEdition(query: String, editionId: String): List<AyaWithInfo> {
        return quranDao.searchQuranByEdition("%$query%", editionId)
    }

    fun searchQuranByEditionWithChanges(query: String, editionId: String): Flow<List<AyaWithInfo>> {
        return quranDao.searchQuranByEditionWithChanges("%$query%", editionId)
    }

    /**
     * Save Quran edition.
     * When you want to download a full Quran edition ([Surah], and all verses [Aya]), you should use [LocalBasedQuranRepository.downloadQuranBook].
     *
     * @param quran the Quran data that you want to save. It's constructed by the API service see [RemoteQuranRepository.getQuranBook].
     */
    suspend fun addQuranBook(quran: Quran.QuranData) {
        quran.surahs.forEach { quranCloudSurah ->
            val surahAyat = quranCloudSurah.ayat.onEach { aya ->
                aya.surah_number = quranCloudSurah.number
                aya.ayaEdition = quran.edition.id
            }
            addAyat(surahAyat)
            addSurah(quranCloudSurah.toSurah(quran.edition.id))
        }
    }

    /**
     * Save a [Surah] into database. To save a list of [Surah] see [addAllSurahs].
     */
    suspend fun addSurah(surah: Surah) {
        quranDao.addSurah(surah)
    }

    /**
     * Save a list of [Surah] into database. To save a single [Surah] see [addSurah].
     */
    suspend fun addAllSurahs(surahs: List<Surah>) {
        quranDao.addSurahs(surahs)
    }

    /**
     * Save an [Aya] into database. To save a list of [Aya] see [addAyat]
     */
    suspend fun addAya(aya: Aya) {
        return quranDao.addAya(aya)
    }

    /**
     * Save a list of [Aya] into database. To save a single [Aya] see [addAya].
     */
    suspend fun addAyat(ayat: List<Aya>) {
        return quranDao.addAyat(ayat)
    }

    /**
     * To delete a full Quran edition book see [LocalBasedQuranRepository.deleteQuranBook].
     */
    suspend fun deleteQuranBook(editionId: String) {
        quranDao.deleteAyat(editionId)
    }
}
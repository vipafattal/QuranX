package com.abedfattal.quranx.core.framework.data.repositories.local

import com.abedfattal.quranx.core.framework.db.daos.BookmarksDao
import com.abedfattal.quranx.core.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.transform


/**
 * Most Quran apps needs a bookmark feature, like saving on each verse the user left of.
 * So this class will help to save and query the bookmarked verse ([Aya]).
 *
 * @property bookmarksDao represents the data access object for [Bookmark] table.
 */
class LocalBookmarksRepository internal constructor(private val bookmarksDao: BookmarksDao) {

    /**
     * Listen for bookmarks table changes, like when new [Bookmark] is added or removed from the table, see [updateBookmarkStatus].
     *
     * @return [AyaWithInfo] list contains only the bookmarked verses and ordered ascending by [Aya.number].
     */
    fun listenToBookmarksChanges(): Flow<List<AyaWithInfo>> {
        return bookmarksDao.listenToBookmarks().distinctUntilChanged()
    }

    /**
     * Listen for bookmarks table changes, like when new [Bookmark] is added or removed from the table, see [updateBookmarkStatus].
     *
     * @return [Edition] info list contains only the bookmarked edition ordered ascending by [Edition.type].
     */
    fun listenToBookmarksEditionChanges(): Flow<List<Edition>> {
        return bookmarksDao.listenToEditionBookmarks().distinctUntilChanged()
    }

    /**
     * List all bookmarked edition, To Listen for bookmark edition changes see [listenToBookmarksEditionChanges].
     *
     * @return [Edition] info list contains only the bookmarked edition ordered ascending by [Edition.type].
     */
    suspend fun getBookmarkedEdition(): List<Edition> {
        return bookmarksDao.getBookmarkedEditions()
    }

    /**
     * Listen for bookmarks table changes in specific edition, like when new [Bookmark] add or removed from the table, see [updateBookmarkStatus].
     *
     * @return [AyaWithInfo] list contains only the bookmarked verses and ordered ascending by [Aya.number].
     */
    fun listenToEditionBookmarks(editionId: String): Flow<List<AyaWithInfo>> {
        return bookmarksDao.listenToEditionBookmarks(editionId).distinctUntilChanged()
    }

    /**
     * List all bookmarked verses. To Listen for bookmark changes see [listenToBookmarksChanges].
     *
     * @return [AyaWithInfo] list contains only the bookmarked verses and ordered ascending by [Aya.number].
     */
    suspend fun getAllBookmarkedAyat(): List<AyaWithInfo> {
        return bookmarksDao.getAllBookmarkedAyat()
    }

    /**
     * List all bookmarked verses in specific edition. To list bookmarks changes see [getAllBookmarkedAyat] (non-specified-edition).
     *
     * @param editionId which represents the edition of the bookmarked verses to look in.
     *
     * @return [AyaWithInfo] list contains only the bookmarked verses and ordered ascending by [Aya.number].
     */
    suspend fun getBookmarkedAyatInEdition(editionId: String): List<AyaWithInfo> {
        return bookmarksDao.getBookmarkedAyatInEdition(editionId)
    }


    /**
     * list all bookmarked verse in all editions.
     *
     * @param ayaNumberInMushaf which represents the verse number.
     *
     * @return [AyaWithInfo] list that contains only the bookmarked verse in all editions.
     */
    suspend fun getAyaBookmarkInAllEdition(ayaNumberInMushaf: Int): List<AyaWithInfo> {
        return bookmarksDao.getAyaBookmarkInAllEdition(ayaNumberInMushaf)
    }

    /**
     * Get bookmarked verse status in specific edition. To get bookmarked verses status see [getAyaBookmarkInAllEdition] (non-specified-edition).
     *
     * @param ayaNumberInMushaf which represents the verse number.
     * @param editionId which represents the edition of the bookmarked verses to look in.
     *
     * @return [AyaWithInfo] only if it's bookmarked, otherwise the result is null.
     */
    suspend fun getAyaBookmarkStatus(ayaNumberInMushaf: Int, editionId: String): AyaWithInfo? {
        return bookmarksDao.getAyaBookmarkStatus(ayaNumberInMushaf, editionId)
    }

    /**
     * Updates the verse bookmark status.
     *
     * @param ayaNumber which represents the verse number in Quran to bookmark.
     * @param editionId which represents the verse edition to bookmark.
     * @param isAdd which determines to add the following bookmark info or remove from the table.
     */
    suspend fun updateBookmarkStatus(
        ayaNumber: Int,
        editionId: String,
        isAdd: Boolean
    ) {
        if (isAdd) bookmarksDao.addBookmark(Bookmark(editionId, ayaNumber))
        else bookmarksDao.removeBookmark(ayaNumber, editionId)
    }
}
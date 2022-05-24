package com.abedfattal.quranx.core.framework.data.repositories.local

import com.abedfattal.quranx.core.framework.db.daos.BookmarksDao
import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.core.model.AyaWithInfo
import com.abedfattal.quranx.core.model.Bookmark
import com.abedfattal.quranx.core.model.Edition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*


/**
 * Most Quran apps needs a bookmark feature, like saving on each verse the user left of.
 * So this class will help to save and query the bookmarked verse ([Aya]).
 *
 * @property bookmarksDao represents the data access object for [Bookmark] table.
 */
class LocalBookmarksRepository internal constructor(private val bookmarksDao: BookmarksDao) :
    ILocalBookmarksRepository {


    override fun listenToEditionChanges(id:String):Flow<List<Bookmark>> {
        return bookmarksDao.listenToEditionBookmarks(id)
    }

    /**
     * Listen for bookmarks table changes, like when new [Bookmark] is added or removed from the table.
     *
     * @return [AyaWithInfo] list contains only the bookmarked verses and ordered ascending by [Aya.number].
     */
    override fun listenToAyatBookmarksChanges(): Flow<List<AyaWithInfo>> {
        return bookmarksDao.listenToBookmarks().distinctUntilChanged()
    }
    /**
     * Listen for bookmarks table changes, like when new [Bookmark] is added or removed from the table.
     *
     * @return [AyaWithInfo] list contains only the bookmarked verses and ordered ascending by [Aya.number].
     */
    override fun listenToAyatBookmarksChanges(editionId: String): Flow<List<AyaWithInfo>> {
        return bookmarksDao.listenToAyaEditionBookmarks(editionId).distinctUntilChanged()
    }

    /**
     * Listen for bookmarks table changes, like when new [Bookmark] is added or removed from the table.
     *
     * @return [Edition] info list contains only the bookmarked edition ordered ascending by [Edition.type].
     */
    override fun listenToBookmarksEditionChanges(): Flow<List<Edition>> {
        return bookmarksDao.listenToAyaEditionBookmarks().distinctUntilChanged()
    }

    /**
     * List all bookmarked edition, To Listen for bookmark edition changes see [listenToBookmarksEditionChanges].
     *
     * @return [Edition] info list contains only the bookmarked edition ordered ascending by [Edition.type].
     */
    override suspend fun getBookmarkedEdition(): List<Edition> {
        return bookmarksDao.getBookmarkedEditions()
    }

    /**
     * Listen for bookmarks table changes in specific edition, like when new [Bookmark] add or removed from the table.
     *
     * @return [AyaWithInfo] list contains only the bookmarked verses and ordered ascending by [Aya.number].
     */
    override fun listenToEditionBookmarks(editionId: String): Flow<List<AyaWithInfo>> {
        return bookmarksDao.listenToAyaEditionBookmarks(editionId).distinctUntilChanged()
    }

    /**
     * List all bookmarked verses. To Listen for bookmark changes see [listenToAyatBookmarksChanges].
     *
     * @return [AyaWithInfo] list contains only the bookmarked verses and ordered ascending by [Aya.number].
     */
    override suspend fun getAllBookmarkedAyat(): List<AyaWithInfo> {
        return bookmarksDao.getAllBookmarkedAyat()
    }

    /**
     * List all bookmarked verses in specific edition. To list bookmarks changes see [getAllBookmarkedAyat] (non-specified-edition).
     *
     * @param editionId which represents the edition of the bookmarked verses to look in.
     *
     * @return [AyaWithInfo] list contains only the bookmarked verses and ordered ascending by [Aya.number].
     */
    override suspend fun getBookmarkedAyatInEdition(editionId: String): List<AyaWithInfo> {
        return bookmarksDao.getBookmarkedAyatInEdition(editionId)
    }


    override suspend fun getBookmarksByType(type:String): List<Bookmark> {
        return bookmarksDao.getBookmarksByType(type)
    }

    /**
     * list all bookmarked verse in all editions.
     *
     * @param ayaNumberInMushaf which represents the verse number.
     *
     * @return [AyaWithInfo] list that contains only the bookmarked verse in all editions.
     */
    override suspend fun getAyaBookmarkInAllEdition(ayaNumberInMushaf: Int): List<AyaWithInfo> {
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
    override suspend fun getAyaBookmarkStatus(ayaNumberInMushaf: Int, editionId: String): AyaWithInfo? {
        return bookmarksDao.getAyaBookmarkStatus(ayaNumberInMushaf, editionId)
    }


    override suspend fun getBookmark(number: Int, ayaEdition: String): Bookmark?
    {
        return bookmarksDao.getBookmark(number,ayaEdition)
    }

    /**
     * Updates the verse bookmark status.
     *
     * @param ayaNumber which represents the verse number in Quran to bookmark.
     * @param editionId which represents the verse edition to bookmark.
     * @param isAdd which determines to add the following bookmark info or remove from the table.
     */
    override suspend fun addBookmark(
        ayaNumber: Int,
        editionId: String,
        editionType: String,
        userId: String
    ) {
        bookmarksDao.addBookmark(
            Bookmark(
                id = UUID.randomUUID().toString(),
                editionId = editionId,
                ayaNumber = ayaNumber,
                type = editionType,
                date = Date(),
                lastUpdate = Date(),
                userId = userId
            )
        )
    }

    override suspend fun addBookmark(bookmark: Bookmark) {
        bookmarksDao.addBookmark(bookmark)
    }
    override suspend fun addBookmark(bookmark: List<Bookmark>) {
        bookmarksDao.addBookmarks(bookmark)
    }

    override suspend fun updateBookmark(bookmark: Bookmark) {
        bookmarksDao.updateBookmark(bookmark)
    }

    override suspend fun updateDirtyState(bookmark: Bookmark, newDirtyState: Boolean) {
        bookmarksDao.updateBookmark(bookmark.copy(isDirty = newDirtyState))
    }

    override suspend fun getDirtyBookmarked(): List<Bookmark> {
        return bookmarksDao.getDirtyBookmarks()
    }
    override suspend fun removeBookmarksPermanently(id: String){
        return bookmarksDao.removeBookmark(id)
    }

    override suspend fun restBookmarks() {
        bookmarksDao.removeAllBookmarked()
    }



}
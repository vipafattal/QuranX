package com.abedfattal.quranx.core.framework.data.repositories.local

import com.abedfattal.quranx.core.model.AyaWithInfo
import com.abedfattal.quranx.core.model.Bookmark
import com.abedfattal.quranx.core.model.Edition
import kotlinx.coroutines.flow.Flow

interface ILocalBookmarksRepository {
    fun listenToEditionChanges(id: String): Flow<List<Bookmark>>

    /**
     * Listen for bookmarks table changes, like when new [Bookmark] is added or removed from the table.
     *
     * @return [AyaWithInfo] list contains only the bookmarked verses and ordered ascending by [Aya.number].
     */
    fun listenToAyatBookmarksChanges(): Flow<List<AyaWithInfo>>

    /**
     * Listen for bookmarks table changes, like when new [Bookmark] is added or removed from the table.
     *
     * @return [AyaWithInfo] list contains only the bookmarked verses and ordered ascending by [Aya.number].
     */
    fun listenToAyatBookmarksChanges(editionId: String): Flow<List<AyaWithInfo>>

    /**
     * Listen for bookmarks table changes, like when new [Bookmark] is added or removed from the table.
     *
     * @return [Edition] info list contains only the bookmarked edition ordered ascending by [Edition.type].
     */
    fun listenToBookmarksEditionChanges(): Flow<List<Edition>>

    /**
     * List all bookmarked edition, To Listen for bookmark edition changes see [listenToBookmarksEditionChanges].
     *
     * @return [Edition] info list contains only the bookmarked edition ordered ascending by [Edition.type].
     */
    suspend fun getBookmarkedEdition(): List<Edition>

    /**
     * Listen for bookmarks table changes in specific edition, like when new [Bookmark] add or removed from the table.
     *
     * @return [AyaWithInfo] list contains only the bookmarked verses and ordered ascending by [Aya.number].
     */
    fun listenToEditionBookmarks(editionId: String): Flow<List<AyaWithInfo>>

    /**
     * List all bookmarked verses. To Listen for bookmark changes see [listenToAyatBookmarksChanges].
     *
     * @return [AyaWithInfo] list contains only the bookmarked verses and ordered ascending by [Aya.number].
     */
    suspend fun getAllBookmarkedAyat(): List<AyaWithInfo>

    /**
     * List all bookmarked verses in specific edition. To list bookmarks changes see [getAllBookmarkedAyat] (non-specified-edition).
     *
     * @param editionId which represents the edition of the bookmarked verses to look in.
     *
     * @return [AyaWithInfo] list contains only the bookmarked verses and ordered ascending by [Aya.number].
     */
    suspend fun getBookmarkedAyatInEdition(editionId: String): List<AyaWithInfo>

    suspend fun getBookmarksByType(type: String): List<Bookmark>

    /**
     * list all bookmarked verse in all editions.
     *
     * @param ayaNumberInMushaf which represents the verse number.
     *
     * @return [AyaWithInfo] list that contains only the bookmarked verse in all editions.
     */
    suspend fun getAyaBookmarkInAllEdition(ayaNumberInMushaf: Int): List<AyaWithInfo>

    /**
     * Get bookmarked verse status in specific edition. To get bookmarked verses status see [getAyaBookmarkInAllEdition] (non-specified-edition).
     *
     * @param ayaNumberInMushaf which represents the verse number.
     * @param editionId which represents the edition of the bookmarked verses to look in.
     *
     * @return [AyaWithInfo] only if it's bookmarked, otherwise the result is null.
     */
    suspend fun getAyaBookmarkStatus(ayaNumberInMushaf: Int, editionId: String): AyaWithInfo?

    suspend fun getBookmark(number: Int, ayaEdition: String): Bookmark?

    /**
     * Updates the verse bookmark status.
     *
     * @param ayaNumber which represents the verse number in Quran to bookmark.
     * @param editionId which represents the verse edition to bookmark.
     * @param isAdd which determines to add the following bookmark info or remove from the table.
     */
    suspend fun addBookmark(
        ayaNumber: Int,
        editionId: String,
        editionType: String,
        userId: String
    )

    suspend fun addBookmark(bookmark: Bookmark)

    suspend fun addBookmark(bookmark: List<Bookmark>)

    suspend fun updateBookmark(bookmark: Bookmark)

    suspend fun updateDirtyState(bookmark: Bookmark, newDirtyState: Boolean)

    suspend fun getDirtyBookmarked(): List<Bookmark>

    suspend fun removeBookmarksPermanently(id: String)
    suspend fun restBookmarks()
}
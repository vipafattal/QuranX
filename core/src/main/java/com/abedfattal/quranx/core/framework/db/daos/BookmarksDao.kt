package com.abedfattal.quranx.core.framework.db.daos

import androidx.room.*
import com.abedfattal.quranx.core.framework.db.AYAT_TABLE
import com.abedfattal.quranx.core.framework.db.BOOKMARKS_TABLE
import com.abedfattal.quranx.core.framework.db.EDITIONS_TABLE
import com.abedfattal.quranx.core.model.AyaWithInfo
import com.abedfattal.quranx.core.model.Bookmark
import com.abedfattal.quranx.core.model.Edition
import kotlinx.coroutines.flow.Flow

/** @suppress */
@Dao
interface BookmarksDao {

    @Query("select * from $EDITIONS_TABLE join $BOOKMARKS_TABLE on id = bookmark_editionId order by type ASC")
    fun listenToAyaEditionBookmarks(): Flow<List<Edition>>

    @Query("select * from $BOOKMARKS_TABLE where bookmark_type = :type")
    suspend fun getBookmarksByType(type: String): List<Bookmark>

    @Query("select * from $EDITIONS_TABLE join $BOOKMARKS_TABLE on id = bookmark_editionId order by type ASC")
    suspend fun getBookmarkedEditions(): List<Edition>

    @Query("select * from $BOOKMARKS_TABLE where bookmark_editionId = :id order by bookmark_date desc")
    fun listenToEditionBookmarks(id: String): Flow<List<Bookmark>>


    @Query("select * from $BOOKMARKS_TABLE join $AYAT_TABLE on bookmark_editionId = ayaEdition and bookmark_ayaNumber = ayaNumberInMushaf where bookmark_is_deleted = 0 order by bookmark_date desc")
    fun listenToBookmarks(): Flow<List<AyaWithInfo>>

    @Query("select * from $AYAT_TABLE join $BOOKMARKS_TABLE on bookmark_editionId = ayaEdition where bookmark_editionId = :editionId order by bookmark_date desc")
    fun listenToAyaEditionBookmarks(editionId: String): Flow<List<AyaWithInfo>>

    @Transaction
    @Query("select * from $AYAT_TABLE join $BOOKMARKS_TABLE on bookmark_editionId = ayaEdition where bookmark_ayaNumber == ayaNumberInMushaf order by bookmark_date desc")
    suspend fun getAllBookmarkedAyat(): List<AyaWithInfo>

    @Transaction
    @Query("select * from $AYAT_TABLE join $BOOKMARKS_TABLE on bookmark_editionId = ayaEdition where bookmark_ayaNumber == ayaNumberInMushaf and bookmark_editionId == :editionId order by bookmark_date desc")
    suspend fun getBookmarkedAyatInEdition(editionId: String): List<AyaWithInfo>

    @Transaction
    @Query("select * from $AYAT_TABLE join $BOOKMARKS_TABLE on bookmark_editionId = ayaEdition where bookmark_ayaNumber == :ayaNumberInMushaf order by bookmark_date desc")
    suspend fun getAyaBookmarkInAllEdition(ayaNumberInMushaf: Int): List<AyaWithInfo>

    @Transaction
    @Query("select * from $AYAT_TABLE join $BOOKMARKS_TABLE on bookmark_editionId = ayaEdition where bookmark_editionId ==:editionId and bookmark_ayaNumber == :ayaNumberInMushaf")
    suspend fun getAyaBookmarkStatus(ayaNumberInMushaf: Int, editionId: String): AyaWithInfo?

    @Transaction
    @Query("select * from $BOOKMARKS_TABLE where bookmark_editionId ==:editionId and bookmark_ayaNumber == :ayaNumberInMushaf")
    suspend fun getBookmark(ayaNumberInMushaf: Int, editionId: String): Bookmark?

    @Query("select * from $BOOKMARKS_TABLE order by bookmark_date desc")
    suspend fun getAllBookmarks(): List<Bookmark>

    @Query("select * from $BOOKMARKS_TABLE where bookmark_is_dirty like 1 order by bookmark_date desc")
    suspend fun getDirtyBookmarks(): List<Bookmark>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBookmark(bookmark: Bookmark)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBookmarks(bookmarks: List<Bookmark>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBookmark(bookmark: Bookmark)

    @Query("delete from $BOOKMARKS_TABLE where bookmark_editionId == :editionId and bookmark_ayaNumber == :ayaNumber")
    suspend fun removeBookmark(
        ayaNumber: Int, editionId: String
    )

    @Query("delete from $BOOKMARKS_TABLE where bookmark_id == :id")
    suspend fun removeBookmark(id: String)

    @Query("delete from $BOOKMARKS_TABLE")
    suspend fun removeAllBookmarked()

}
package com.abedfattal.quranx.core.framework.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.abedfattal.quranx.core.framework.db.AYAT_TABLE
import com.abedfattal.quranx.core.framework.db.BOOKMARKS_TABLE
import com.abedfattal.quranx.core.model.AyaWithInfo
import com.abedfattal.quranx.core.model.Bookmark
import kotlinx.coroutines.flow.Flow

/** @suppress */
@Dao
interface BookmarksDao {

    @Query("select * from $AYAT_TABLE join $BOOKMARKS_TABLE on bookmark_editionId = ayaEdition order by ayaNumberInMushaf ASC")
    fun listenToBookmarks(): Flow<List<AyaWithInfo>>

    @Query("select * from $AYAT_TABLE join $BOOKMARKS_TABLE on bookmark_editionId = ayaEdition where bookmark_editionId = :editionId order by ayaNumberInMushaf ASC")
    fun listenToEditionBookmarks(editionId: String): Flow<List<AyaWithInfo>>

    @Transaction
    @Query("select * from $AYAT_TABLE join $BOOKMARKS_TABLE on bookmark_editionId = ayaEdition order by ayaNumberInMushaf ASC")
    suspend fun getAllBookmarkedAyat(): List<AyaWithInfo>

    @Transaction
    @Query("select * from $AYAT_TABLE join $BOOKMARKS_TABLE on bookmark_editionId = ayaEdition where bookmark_editionId == :editionId order by ayaNumberInMushaf ASC")
    suspend fun getBookmarkedAyatInEdition(editionId: String): List<AyaWithInfo>

    @Transaction
    @Query("select * from $AYAT_TABLE join $BOOKMARKS_TABLE on bookmark_editionId = ayaEdition where bookmark_ayaNumber == :ayaNumberInMushaf order by ayaNumberInMushaf ASC")
    suspend fun getAyaBookmarkInAllEdition(ayaNumberInMushaf: Int): List<AyaWithInfo>

    @Transaction
    @Query("select * from $AYAT_TABLE join $BOOKMARKS_TABLE on bookmark_editionId = ayaEdition where bookmark_editionId ==:editionId and bookmark_ayaNumber == :ayaNumberInMushaf")
    suspend fun getAyaBookmarkStatus(ayaNumberInMushaf: Int,editionId: String): AyaWithInfo?


    @Insert
    suspend fun addBookmark(bookmark: Bookmark)

    @Query("delete from $BOOKMARKS_TABLE where bookmark_editionId == :editionId and bookmark_ayaNumber == :ayaNumber")
    suspend fun removeBookmark(
        ayaNumber: Int, editionId: String
    )
}
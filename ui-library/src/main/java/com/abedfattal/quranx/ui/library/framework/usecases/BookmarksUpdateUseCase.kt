package com.abedfattal.quranx.ui.library.framework.usecases

import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.AyaWithInfo
import com.abedfattal.quranx.core.model.Bookmark
import java.util.*

object BookmarksUpdateUseCase {

    private val bookmarksRepository = DataSources.localDataSource.bookmarksRepository

    suspend fun updateBookmark(
        ayaWithInfo: AyaWithInfo,
        removePermanently: Boolean
    ) {
        if (ayaWithInfo.isBookmarked)
            removeBookmark(ayaWithInfo.bookmark!!, removePermanently)
        else
            addBookmark(ayaWithInfo)
    }


    private suspend fun addBookmark(ayaWithInfo: AyaWithInfo) {
        if (ayaWithInfo.isBookmarked) {
            updateBookmark(
                ayaWithInfo.bookmark!!.copy(
                    isDeleted = false,
                    lastUpdate = Date()
                )
            )
        } else
            bookmarksRepository.addBookmark(
                ayaWithInfo.aya.number,
                ayaWithInfo.edition.identifier,
                ayaWithInfo.edition.type,
                ""
            )
    }

    suspend fun updateBookmark(bookmark: Bookmark) {
        bookmarksRepository.updateBookmark(
            bookmark.copy(isDirty = true)
        )
    }

    suspend fun removeBookmarks(
        bookmarks: List<Bookmark>,
        removePermanently: Boolean,
    ) {
        bookmarks.forEach { removeBookmark(it, removePermanently) }
    }

    private suspend fun removeBookmark(
        bookmark: Bookmark,
        removePermanently: Boolean,
    ) {
        if (removePermanently) removeBookmarkPermanent(bookmark.id)
        else
            bookmarksRepository.updateBookmark(
                bookmark.copy(
                    isDeleted = true,
                    isDirty = true,
                    lastUpdate = Date()
                )
            )
    }

    private suspend fun removeBookmarkPermanent(bookmarkId: String) {
        bookmarksRepository.removeBookmarksPermanently(bookmarkId)
    }
}